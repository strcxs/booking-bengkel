package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;
import com.bengkel.booking.repositories.BookingOrderRepo;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class BengkelService {
	Scanner input = new Scanner(System.in);
	private static List<BookingOrder> bookingOrders = BookingOrderRepo.getAllBookingOrders();
	//Silahkan tambahkan fitur-fitur utama aplikasi disini
	
	//Login
	
	//Info Customer
	public void infoCustomer(Customer customer){
		boolean loop = true;
		while (loop) {
			String member = "Non Member";
			if (customer instanceof MemberCustomer) {
				MemberCustomer memberCustomer = (MemberCustomer) customer;
				member = "Member";
				System.out.printf(" %-20s %-10s\n","Customer Id",memberCustomer.getCustomerId());
				System.out.printf(" %-20s %-10s\n","Nama",memberCustomer.getName());
				System.out.printf(" %-20s %-10s\n","Customer Status",member);
				System.out.printf(" %-20s %-10s\n","Alamat",memberCustomer.getAddress());
				System.out.printf(" %-20s %-10s\n","Saldo Koin",memberCustomer.getSaldoCoin());
				System.out.println();
				System.out.println("List Kendaraan");
				
				PrintService.printVechicle(memberCustomer.getVehicles());
			}else{
				member = "Non Member";
				System.out.printf(" %-20s %-10s\n","Customer Id",customer.getCustomerId());
				System.out.printf(" %-20s %-10s\n","Nama",customer.getName());
				System.out.printf(" %-20s %-10s\n","Customer Status",member);
				System.out.printf(" %-20s %-10s\n","Alamat",customer.getAddress());
				System.out.println();
				System.out.println("List Kendaraan");
				
				PrintService.printVechicle(customer.getVehicles());
			}
			System.out.println("0 untuk keluar");
			if (Integer.valueOf(input.nextLine())==0) {
				loop = false;
			}else{
				loop = true;
			}
		}
	}
	//Booking atau Reservation
	public void bookingBengkel(Customer customer){
		List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
		
		boolean cekVehicle = false;
		boolean cekService = false;
		boolean valid = false;
		String vehicleType = null;
		List<ItemService> itemType = new ArrayList<>();
		List<ItemService> addItem = new ArrayList<>();
		do {
			System.out.println("Masukan Vehicle Id");
			String vehicleId = input.nextLine().toUpperCase();
			for (Vehicle vehicle : customer.getVehicles()) {
				if (vehicle.getVehiclesId().toUpperCase().equals(vehicleId)) {
					cekVehicle = true;
					vehicleType = vehicle.getVehicleType(); 
				}
			}if (!cekVehicle) {
				System.out.println("Kendaraan Tidak ditemukan");
			}
		} while (!cekVehicle);
		System.out.println();
		System.out.println("List Service yang tersedia :");
		for (ItemService itemService : listAllItemService) {
			if (vehicleType.toLowerCase().equals(itemService.getVehicleType().toLowerCase())) {
				itemType.add(itemService);
			}
		}
		PrintService.PrintItemService(itemType);
		System.out.println();
		do {
			cekService = false;
			System.out.println("Silahkan Masukan Service Id");
			String serviceId = input.nextLine().toUpperCase();
			for (ItemService itemService : itemType) {
				if (itemService.getServiceId().toUpperCase().equals(serviceId)) {
					cekService = true;
					addItem.add(itemService);
				}
			}
			if (serviceId.equals("0")) {
				return;
			}
			if (!cekService) {
				System.out.println("Service Tidak ditemukan");
			}
		} while (!cekService);
		do {
			valid = false;
			System.out.println("Apakah anda ingin menambahkan Service yang lainnya? (Y/T)");
			String confirm = input.nextLine().toUpperCase();
			if (confirm.equals("Y")) {
				do {
					cekService = false;
					System.out.println("Silahkan Masukan Service Id");
					String serviceId = input.nextLine().toUpperCase();
					for (ItemService itemService : itemType) {
						if (itemService.getServiceId().toUpperCase().equals(serviceId)) {
							cekService = true;
							addItem.add(itemService);
							valid = true;
						}
					}
					if (!cekService) {
						System.out.println("Service Tidak ditemukan");
					}
				} while (!cekService);
			}else if(confirm.equals("T")){
				valid = false;
			}else{
				System.out.println("input tidak valid");
				valid = true;
			}
		} while (valid);
		
		Random random = new Random();
		int randomID = random.nextInt(90)+10;
		String bookingID = "B"+randomID;
		Double totalServicePrice=0.0;

		for (ItemService itemService : addItem) {
			totalServicePrice += itemService.getPrice();
		}
		String paymentType="Cash";
		if (customer instanceof MemberCustomer) {
			MemberCustomer memberCustomer = (MemberCustomer) customer;

			System.out.println("silahkan Pilih metode pembayaran (Saldo Coin atau Cash)");
			paymentType = input.nextLine().toString();
			if (paymentType.equals("saldo")||paymentType.equals("coin")||paymentType.equals("saldo coin")) {
				Double payment = totalServicePrice-(totalServicePrice*0.1); 
				Double result = memberCustomer.getSaldoCoin()-payment;
				if (result>=0) {
					memberCustomer.setSaldoCoin(result);
					BookingOrder bookingOrder = new BookingOrder(bookingID, customer, addItem, paymentType, totalServicePrice, payment);
					bookingOrders.add(bookingOrder);
					System.out.println();
					System.out.println("Booking Berhasil !");
				}
				else{
					System.out.println("Saldo Coin tidak cukup");
				}
			}
		}else{
			BookingOrder bookingOrder = new BookingOrder(bookingID, customer, addItem, paymentType, totalServicePrice, totalServicePrice);
			bookingOrders.add(bookingOrder);
			System.out.println();
			System.out.println("Booking Berhasil !");
		}
	}
	//Top Up Saldo Coin Untuk Member Customer
	public void topUpSaldo(Customer customer){
		if (customer instanceof MemberCustomer) {
			MemberCustomer memberCustomer = (MemberCustomer) customer;
			boolean loop = true;
			while (loop) {
				System.out.println("Masukan Besaran Top up :");
				Double topUp = Double.valueOf(input.nextLine());
				if (topUp<0) {
					System.out.println("input tidak valid");
				}else{
					memberCustomer.setSaldoCoin(memberCustomer.getSaldoCoin()+topUp);
					System.out.printf("Berhasil, Saldo saat ini %d",memberCustomer.getSaldoCoin());
				}
			}
		}else{
			System.out.println("Anda bukan Member");
		}
	}

	//infoBooking
	public void infoBookingOrder(){
		boolean loop = true;
		while (loop) {
			System.out.println("Booking Order Menu");
			
			PrintService.printBookingMenu(bookingOrders);
			System.out.printf("> ");
			if (Integer.valueOf(input.nextLine())==0) {
				loop = false;
			}else{
				loop = true;
			}
		}
	}
	//Logout
	
}
