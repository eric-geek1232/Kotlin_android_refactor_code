package com.betterandroid.restaurantscorner.features.restaurants

import com.betterandroid.restaurantscorner.Restaurant
import com.betterandroid.restaurantscorner.domain.models.restaurants.RestaurantDisplayItem
import com.betterandroid.restaurantscorner.domain.models.restaurants.RestaurantType

class RestaurantViewModel {
    fun prepareRestaurantViews(restaurants: List<Restaurant>): List<RestaurantDisplayItem>{
        return restaurants.map { restaurant ->
            return@map RestaurantDisplayItem(
                id = restaurant.id,
                displayName = "Restaurant ${restaurant.name}",
                displayDistance = "at ${restaurant.distance} KM distance",
                imageUrl = restaurant.imageUrl,
                type = when (restaurant.type) {
                    "TAKE_AWAY" -> RestaurantType.TAKE_AWAY
                    "EAT_IN" -> RestaurantType.EAT_IN
                    else -> RestaurantType.DRIVE_THROUGH
                }
            )
        }
    }
}