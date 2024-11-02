package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTable {
    private final int id;
    private final String name;
    private final int seatingCapacity;
    private final List<Reservation> reservationList;

    public RestaurantTable(int id, String name, int seatingCapacity) {
        this.id = id;
        this.name = name;
        this.seatingCapacity = seatingCapacity;
        this.reservationList = new ArrayList<>();
    }

    public synchronized boolean addReservation(Reservation reservation) {
        if (isTableAvailable(reservation.localDate, reservation.startTime, reservation.endTime)) {
            reservationList.add(reservation);
            return true; // Booking successful
        }
        return false; // Booking failed
    }

    public synchronized boolean isTableAvailable(LocalDate localDate, LocalTime startTime, LocalTime endTime) {
        for (Reservation reservation : reservationList) {
            if (!reservation.localDate.equals(localDate)) {
                continue;
            }
            if (isTimeOverlapping(reservation, startTime, endTime)) {
                return false;
            }
        }
        return true; // Table is available
    }

    private boolean isTimeOverlapping(Reservation reservation, LocalTime startTime, LocalTime endTime) {
        LocalTime a = reservation.startTime;
        LocalTime b = reservation.endTime;
        return startTime.isBefore(b) && endTime.isAfter(a); // Check for overlap
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }
}
