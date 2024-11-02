package service;

import models.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class RestaurantService {
    private Map<Integer, Restaurant> restaurants;

    public RestaurantService() {
        this.restaurants = new HashMap<>();
    }

    public Restaurant addRestaurant(String name, String location, String phone, String email, int costForTwo) {
        if(name == null || costForTwo < 0) {
            throw new IllegalArgumentException("Invalid restaurant data");
        }
        int restaurantId = ThreadLocalRandom.current().nextInt(100, 10000); // Generate a random restaurant ID
        Restaurant restaurant = new Restaurant(restaurantId, name, location, phone, email, costForTwo);
        this.restaurants.put(restaurantId, restaurant);
        return restaurant;
    }

    public List<RestaurantTable> findAvailableTables(int restaurantId, LocalDate localDate, LocalTime startTime, LocalTime endTime) {
        Restaurant restaurant = this.restaurants.get(restaurantId);
        if (restaurant == null) {
            throw new RuntimeException("Restaurant not found");
        }
        return restaurant.getRestaurantTableList().stream()
                .filter(r -> r.isTableAvailable(localDate, startTime, endTime))
                .collect(Collectors.toList());
    }

    public void addSchedule(int rid, RestaurantSchedule schedule) {
        Restaurant restaurant = this.restaurants.get(rid);
        restaurant.addSchedule(schedule);
    }

    public List<Restaurant> searchRestaurants(String name, String location, Double maxCostForTwo) {
        return restaurants.values().stream()
                .filter(restaurant -> matchesName(restaurant, name))
                .filter(restaurant -> matchesLocation(restaurant, location))
                .filter(restaurant -> matchesCost(restaurant, maxCostForTwo))
                .collect(Collectors.toList());
    }

    private boolean matchesName(Restaurant restaurant, String name) {
        return (name == null || name.isEmpty() || restaurant.getName().toLowerCase().contains(name.toLowerCase()));
    }

    private boolean matchesLocation(Restaurant restaurant, String location) {
        return (location == null || location.isEmpty() || restaurant.getLocation().toLowerCase().contains(location.toLowerCase()));
    }

    private boolean matchesCost(Restaurant restaurant, Double maxCostForTwo) {
        return (maxCostForTwo == null || restaurant.getCostForTwo() <= maxCostForTwo);
    }
}

