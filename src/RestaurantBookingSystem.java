import enums.*;
import models.*;
import service.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RestaurantBookingSystem {
    public static void main(String[] args) {
        // Initialize services
        RestaurantService restaurantService = new RestaurantService();
        BookingService bookingService = new BookingService(restaurantService);

        // Add restaurants
        Restaurant restaurant1 = restaurantService.addRestaurant("Pasta Place", "123 Pasta Lane", "123-456-7890", "contact@pastaplace.com", 50);
        Restaurant restaurant2 = restaurantService.addRestaurant("Burger Joint", "456 Burger Blvd", "987-654-3210", "info@burgerjoint.com", 30);

        // Add tables to the first restaurant
        restaurant1.addRestaurantTable(new RestaurantTable(1, "Table 1", 4));
//        restaurant1.addRestaurantTable(new models.Reservation.RestaurantTable(2, "Table 2", 2, true));

        // Add schedule for the restaurant
        restaurantService.addSchedule(restaurant1.getId(), new RestaurantSchedule(1, restaurant1.getId(), Weekday.MONDAY, LocalTime.of(10, 0), LocalTime.of(22, 0)));

        // Attempt to book a table
        LocalDate bookingDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            final int userId = i; // Each thread gets a unique userId
            executorService.submit(() -> {
                try {
                    Booking booking = bookingService.bookTable(userId, restaurant1.getId(), bookingDate, startTime, endTime);
                    System.out.println("Booking successful! Booking ID: " + booking.getId() + "using thread:" + Thread.currentThread().getName() + " for restaurant id " + restaurant1.getId());
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            });
        }

    }
}