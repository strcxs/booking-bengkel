package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;

public class BengkelService {
	Scanner input = new Scanner(System.in);
	//Silahkan tambahkan fitur-fitur utama aplikasi disini
	
	//Login
	
	//Info Customer
	public void infoCustomer(Customer customer){
		String member = "Non Member";
		if (customer instanceof MemberCustomer) {
			MemberCustomer memberCustomer = (MemberCustomer) customer;
			member = "Member";
			System.out.printf(" %-20s %-10s\n","Customer Id",memberCustomer.getCustomerId());
			System.out.printf(" %-20s %-10s\n","Nama",memberCustomer.getName());
			System.out.printf(" %-20s %-10s\n","Customer Status",member);
			System.out.printf(" %-20s %-10s\n","Alamat",memberCustomer.getAddress());
			System.out.printf(" %-20s %-10s\n","Saldo Koin",memberCustomer.getSaldoCoin());
			System.out.println("List Kendaraan");
			
			PrintService.printVechicle(memberCustomer.getVehicles());
		}else{
			member = "Non Member";
			System.out.printf(" %-20s %-10s\n","Customer Id",customer.getCustomerId());
			System.out.printf(" %-20s %-10s\n","Nama",customer.getName());
			System.out.printf(" %-20s %-10s\n","Customer Status",member);
			System.out.printf(" %-20s %-10s\n","Alamat",customer.getAddress());
			System.out.println("List Kendaraan");
			
			PrintService.printVechicle(customer.getVehicles());
		}
	}
	//Booking atau Reservation
	public void bookingBengkel(List<Vehicle> vehicles ,List<ItemService> itemServices, List<BookingOrder> bookingOrders){
		boolean cekVehicle = false;
		boolean cekService = false;
		boolean valid = false;
		String vehicleType = null;
		List<ItemService> itemType = new ArrayList<>();
		do {
			System.out.println("Masukan Vehicle Id");
			String vehicleId = input.nextLine().toUpperCase();
			for (Vehicle vehicle : vehicles) {
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
		for (ItemService itemService : itemServices) {
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
				}
			}
			if (serviceId.equals("0")) {
				break;
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
		System.out.println("silahkan Pilih metode pembayaran (Saldo Coin atau Cash)");
		String paymentType = input.nextLine();
		System.out.println();
		System.out.println("Booking Berhasil !");
	}
	//Top Up Saldo Coin Untuk Member Customer
	
	//Logout
	
}
