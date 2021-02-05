package com.betterandroid.restaurantscorner.api.restaurants

import com.betterandroid.restaurantscorner.RestaurantListResponse
import com.betterandroid.restaurantscorner.mocks.MockCreator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RestaurantsRestClient {
    private val disposable = CompositeDisposable()

    fun getRestaurants(completionHandler: (response: RestaurantListResponse) -> Unit) {
        disposable.add(
            getRestaurants()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    completionHandler.invoke(response)
                }, {})
        )

    }

    fun getRestaurants() =
        MockCreator.getRestaurantsResponseMock().delay(1000, TimeUnit.MILLISECONDS)

    fun stopStream() {
        disposable.dispose()
    }
}