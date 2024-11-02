package models;

import enums.Weekday;

import java.time.LocalTime;

public class RestaurantSchedule {
    int id;
    int restaurantId;
    Weekday weekday;
    LocalTime startTime;
    LocalTime endTime;

    public RestaurantSchedule(int id, int restaurantId, Weekday weekday, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public int getId() { return id; }
    public int getRestaurantId() { return restaurantId; }
    public Weekday getDayOfWeek() { return weekday; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
}