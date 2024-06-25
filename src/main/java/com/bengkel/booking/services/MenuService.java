package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static List<BookingOrder> bookingOrders = new ArrayList<>();
	private static Scanner input = new Scanner(System.in);

	public static void run() {
		boolean isLooping = true;
		int trylogin = 1;
		do {
			String[] listMenu = {"Login", "Exit"};
			PrintService.printMenu(listMenu, "Aplikasi Booking Bengkel");
			int option = Integer.valueOf(input.nextLine());
			if (option==0) {
				isLooping = false;
			}else if (option==1) {
				login();
				if (trylogin >= 3) {
					isLooping=false;
				}
			} 
			trylogin++;
		} while (isLooping);
		
	}
	
	public static void login() {
		System.out.println("LOGIN");
		System.out.println("Masukkan Customer Id:");
		String custID = input.nextLine().toLowerCase();
		System.out.println("Masukkan Password:");
		String password = input.nextLine();
		
		for (Customer customer : listAllCustomers) {
			if (customer.getCustomerId().toLowerCase().equals(custID)) {
				if (customer.getPassword().equals(password)) {
					mainMenu(customer);
				}else{
					System.out.println("Password yang anda Masukkan Salah!");
				}
			} else{
				System.out.println("Customer Id Tidak Ditemukan atau Salah!");
			}
		}
	}
	
	public static void mainMenu(Customer customer) {
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;
		BengkelService bengkelService = new BengkelService();

		do {
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			System.out.println(menuChoice);
			
			switch (menuChoice) {
			case 1:
				//panggil fitur Informasi Customer
				bengkelService.infoCustomer(customer);
				break;
			case 2:
				//panggil fitur Booking Bengkel
				bengkelService.bookingBengkel(customer.getVehicles(), listAllItemService,bookingOrders);
				break;
			case 3:
				//panggil fitur Top Up Saldo Coin
				break;
			case 4:
				//panggil fitur Informasi Booking Order
				break;
			default:
				System.out.println("Logout");
				isLooping = false;
				break;
			}
		} while (isLooping);
		
		
	}
	
	//Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
