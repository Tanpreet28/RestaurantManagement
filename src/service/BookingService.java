package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import models.*;
import enums.*;

public class BookingService {
    Map<String, Booking> bookings = new HashMap<>();
    RestaurantService restaurantService;

    public BookingService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public Booking bookTable(int userId, int restaurantId, LocalDate localDate, LocalTime startTime, LocalTime endTime) {
        List<RestaurantTable> tables = findAvailableTables(restaurantId, localDate, startTime, endTime);

        if(tables.isEmpty()) {
            throw new RuntimeException("No available tables found for restaurant " + restaurantId);
        }

        RestaurantTable selectedTable = tables.get(0);
        Booking newBooking = createBooking(userId, restaurantId, startTime, endTime, selectedTable);

        synchronized (selectedTable) {
            if (!selectedTable.isTableAvailable(localDate, startTime, endTime)) {
                throw new RuntimeException("No available tables found for restaurant " + restaurantId);
            }
            System.out.println("Thread " + Thread.currentThread().getName() + " attempting to book..." + selectedTable.getName());
            if (selectedTable.addReservation(new Reservation(newBooking.getId(), userId, localDate, startTime, endTime))) {
                bookings.put(newBooking.getId(), newBooking);
                return newBooking;
            }
            throw new RuntimeException("No available tables found for restaurant " + restaurantId); // Handle the failure case
        }
    }

    public void confirmBooking(String id) {
        Booking booking = bookings.get(id);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }
        booking.setBookingStatus(BookingStatus.CONFIRMED);
    }

    void cancelBooking(String id) {
        Booking booking = bookings.get(id);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
    }

    private synchronized List<RestaurantTable> findAvailableTables(int restaurantId, LocalDate LocalDate, LocalTime startTime, LocalTime endTime) {
        return restaurantService.findAvailableTables(restaurantId, LocalDate, startTime, endTime);
    }

    private Booking createBooking(int userId, int restaurantId, LocalTime startTime, LocalTime endTime, RestaurantTable restaurantTable) {
        return new Booking.BookingBuilder(userId, restaurantId, startTime, endTime)
                .id(UUID.randomUUID().toString())
                .bookingLocalDate(new Date())
                .restaurantTableId(restaurantTable.getId())
                .bookingStatus(BookingStatus.RESERVED)
                .build();
    }

}
