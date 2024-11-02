package models;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Reservation {
    String id;
    int userId;
    LocalDate localDate;
    LocalTime startTime;
    LocalTime endTime;

    public Reservation(String id, int userId, LocalDate localDate, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.userId = userId;
        this.localDate = localDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static class RestaurantTable {
        int id;
        String name;
        int seatingCapacity;
        List<Reservation> reservationList;

        public RestaurantTable(int id, String name, int seatingCapacity, boolean isAvailable, List<Reservation> reservationList) {
            this.id = id;
            this.name = name;
            this.seatingCapacity = seatingCapacity;
            this.reservationList = reservationList != null ? new ArrayList<>(reservationList) : new ArrayList<>();
        }

        public boolean addReservation(Reservation reservation) {
                if (isTableAvailable(reservation.localDate, reservation.startTime, reservation.endTime)) {
                    reservationList.add(reservation);
                    return true; // Booking successful
            }
            return false;
        }

        public boolean isTableAvailable(LocalDate localDate, LocalTime startTime, LocalTime endTime) {
            for (Reservation reservation : reservationList) {
                if (!reservation.localDate.equals(localDate)) {
                    continue;
                }
                if (isTimeOverlapping(reservation, startTime, endTime)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isTimeOverlapping(Reservation reservation, LocalTime startTime, LocalTime endTime) {
            LocalTime a = reservation.startTime;
            LocalTime b = reservation.endTime;

            boolean res = startTime.isBefore(b) && endTime.isAfter(a);
            return res;
        }
    }
}