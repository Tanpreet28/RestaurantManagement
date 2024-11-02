package models;

import enums.BookingStatus;

import java.time.LocalTime;
import java.util.Date;

public class Booking {
    private String id;
    private int userId;
    private int restaurantId;
    private Date bookingLocalDate;
    private int restaurantTableId;
    private LocalTime startTime;
    private LocalTime endTime;
    private BookingStatus bookingStatus;

    public Booking(BookingBuilder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.restaurantId = builder.restaurantId;
        this.bookingLocalDate = builder.bookingLocalDate;
        this.restaurantTableId = builder.restaurantTableId;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.bookingStatus = builder.bookingStatus;
    }

    public String getId() {
        return id;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public static class BookingBuilder {
        private String id;
        private int userId;
        private int restaurantId;
        private Date bookingLocalDate;
        private int restaurantTableId;
        private LocalTime startTime;
        private LocalTime endTime;
        private BookingStatus bookingStatus;

        public BookingBuilder(int userId, int restaurantId, LocalTime startTime, LocalTime endTime) {
            this.userId = userId;
            this.restaurantId = restaurantId;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public BookingBuilder id(String id) {
            this.id = id;
            return this;
        }

        public BookingBuilder bookingLocalDate(Date bookingLocalDate) {
            this.bookingLocalDate = bookingLocalDate;
            return this;
        }

        public BookingBuilder bookingStatus(BookingStatus bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public BookingBuilder restaurantTableId(int restaurantTableId) {
            this.restaurantTableId = restaurantTableId;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}
