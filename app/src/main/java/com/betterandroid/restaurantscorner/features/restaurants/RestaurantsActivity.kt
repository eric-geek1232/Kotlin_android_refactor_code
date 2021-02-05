package com.betterandroid.restaurantscorner.features.restaurants

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.betterandroid.restaurantscorner.*
import com.betterandroid.restaurantscorner.api.restaurants.RestaurantsRestClient
import com.betterandroid.restaurantscorner.data.restaurants.RestaurantParser
import com.betterandroid.restaurantscorner.business.restaurants.RestaurantRules
import kotlinx.android.synthetic.main.activity_restaurants.*
import java.util.*

class RestaurantsActivity : AppCompatActivity() {

    private val restaurantsRestClient = RestaurantsRestClient()
    private var restaurantsAdapter: RestaurantsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants)
        restaurantsAdapter = RestaurantsAdapter()
        recyclerViewRestaurants.apply {
            layoutManager = LinearLayoutManager(
                context!!,
                LinearLayoutManager.VERTICAL,
                false
            )
            this.adapter = restaurantsAdapter
        }
        showRestaurants()
    }

    override fun onDestroy() {
        super.onDestroy()
        restaurantsRestClient.stopStream()
    }

    private fun showRestaurants() {
        restaurantsRestClient.getRestaurants { response ->
            // parsing, filtering and displaying
            val restaurantsParser = RestaurantParser()
            val restaurantRules = RestaurantRules()

            val parsedRestaurants = restaurantsParser.parseRestaurants(response)
            val filteredRestaurants = restaurantRules.filterRestaurants(parsedRestaurants)
            displayRestaurants(filteredRestaurants)
        }
    }

    private fun displayRestaurants(restaurants: List<Restaurant>) {
        val viewModel = RestaurantViewModel()
        val displayRestaurants = viewModel.prepareRestaurantViews(restaurants)

        restaurantsAdapter!!.restaurants = displayRestaurants
        restaurantsAdapter!!.clickListener =
            object : RestaurantsAdapter.RestaurantClickListener {
                override fun onRestaurantClicked(restaurantId: Int) {
                    Toast.makeText(
                        this@RestaurantsActivity,
                        "Pressed a restaurant!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}