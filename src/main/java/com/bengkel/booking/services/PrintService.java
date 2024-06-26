package com.bengkel.booking.services;

import java.security.Provider.Service;
import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.Vehicle;

public class PrintService {
	
	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}
	public static void PrintItemService(List<ItemService> listItemService) {
		String formatTable = "| %-2s | %-15s | %-15s | %-19s | %-15s |%n";
		String line = "+----+-----------------+-----------------+---------------------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Sevice Id", "Nama Service", "Tipe Kendaraan", "Harga");
	    System.out.format(line);
	    int number = 1;
	    for (ItemService itemService : listItemService) {
	    	System.out.format(formatTable, number, itemService.getServiceId(), itemService.getServiceName(), itemService.getVehicleType(), itemService.getPrice());
	    	number++;
	    }
	    System.out.printf(line);
	    System.out.format("| %-2s | %-73s |\n", "0", "Kembali Ke Home Menu");
	    System.out.printf(line);
	}
	public static String printServiceList(List<ItemService> itemServices){
		String result = "";
        // Bisa disesuaikan kembali
        for (ItemService itemService : itemServices) {
            result += itemService.getServiceName() + ", ";
        }
        return result;
	}

	public static void printBookingMenu(List<BookingOrder> bookingOrders){
		String formatTable = "| %-2s | %-15s | %-15s | %-19s | %-15s | %-15s | %-28s |%n";
		String line = "+----+-----------------+-----------------+---------------------+----------------+-------------------------------------------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Booking Id", "Nama Customer", "Payment Method", "Total Service","Total Payment","List Service");
	    System.out.format(line);
	    int number = 1;
	    for (BookingOrder bookingOrder : bookingOrders) {
	    	System.out.format(formatTable, number, bookingOrder.getBookingId(), bookingOrder.getCustomer().getName(), bookingOrder.getPaymentMethod(), bookingOrder.getTotalServicePrice(),bookingOrder.getTotalPayment(),printServiceList(bookingOrder.getServices()));
	    	number++;
	    }
	    System.out.printf(line);
	    System.out.format("| %-2s | %-122s |\n", "0", "Kembali Ke Home Menu");
	    System.out.printf(line);
	}
}
