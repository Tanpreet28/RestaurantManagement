package models;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;

public class Restaurant {
    int id;
    String name;
    String location;
    String phone;
    String email;
    int costForTwo;
    List<RestaurantTable> restaurantTableList;
    List<RestaurantSchedule> restaurantSlotList;

    public Restaurant(int id, String name, String location, String phone, String email, int costForTwo) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.costForTwo = costForTwo;
        this.restaurantTableList = new ArrayList<>();
        this.restaurantSlotList = new ArrayList<>();
    }

    public List<RestaurantTable> getRestaurantTableList() {
        return restaurantTableList;
    }

    public String getName() {
        return name;
    }

    public int getCostForTwo() {
        return costForTwo;
    }

    public String getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }

    public boolean isTableAvailable(LocalDate LocalDate, LocalTime startTime, LocalTime endTime) {
        for(RestaurantTable restaurantTable : restaurantTableList) {
            if(restaurantTable.isTableAvailable(LocalDate, startTime, endTime)) {
                return true;
            }
        }
        return false;
    }

    public void addSchedule(RestaurantSchedule schedule) {
        restaurantSlotList.add(schedule);
    }

    public void addRestaurantTable(RestaurantTable restaurantTable) {
        restaurantTableList.add(restaurantTable);
    }

}