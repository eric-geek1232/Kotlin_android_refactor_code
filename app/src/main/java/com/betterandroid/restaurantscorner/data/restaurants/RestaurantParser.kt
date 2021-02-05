package com.betterandroid.restaurantscorner.data.restaurants

import com.betterandroid.restaurantscorner.Restaurant
import com.betterandroid.restaurantscorner.RestaurantListResponse
import com.betterandroid.restaurantscorner.SimpleLocation

class RestaurantParser {
    fun parseRestaurants(response: RestaurantListResponse): List<Restaurant> {
        return response.restaurants
            ?.filter { restaurantResponse ->
                restaurantResponse.name != null
                        && restaurantResponse.imageUrl != null
            }?.map { restaurantResponse ->
                val location = SimpleLocation(
                    restaurantResponse.locationLatitude,
                    restaurantResponse.locationLongitude
                )
                return@map Restaurant(
                    id = restaurantResponse.id,
                    name = restaurantResponse.name!!,
                    imageUrl = restaurantResponse.imageUrl!!,
                    location = location,
                    closingHour = restaurantResponse.closingHour,
                    type = restaurantResponse.type
                )
            }.orEmpty()
    }
}