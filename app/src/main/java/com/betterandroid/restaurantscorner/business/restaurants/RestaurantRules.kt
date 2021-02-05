package com.betterandroid.restaurantscorner.business.restaurants

import android.location.Location
import com.betterandroid.restaurantscorner.Restaurant
import com.betterandroid.restaurantscorner.mocks.MockCreator

class RestaurantRules {
    fun filterRestaurants(restaurants: List<Restaurant>): List<Restaurant> {
        return restaurants
            .filter { restaurant -> restaurant.closingHour < 6 }
            .onEach { restaurant ->
                val userLatitude = MockCreator.getUserLatitude()
                val userLongitude = MockCreator.getUserLongitude()
                val distance = FloatArray(2)
                Location.distanceBetween(
                    userLatitude, userLongitude,
                    restaurant.location.latitude,
                    restaurant.location.longitude,
                    distance
                )

                val distanceResult = distance[0] / 100
                restaurant.distance = distanceResult.toInt()
            }.sortedBy { restaurant -> restaurant.distance }
    }
}