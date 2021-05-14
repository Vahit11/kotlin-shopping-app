package com.vahitkeskin.kotlinshoppingapp.services

import com.vahitkeskin.kotlinshoppingapp.model.Categories
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.util.UtilStrings.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ShoppingAPIService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ShoppingAPI::class.java)

    fun getShoppingData() : Single<List<Shopping>>{
        return api.getShoppingAPI()
    }

    fun getCategoryData() : Single<List<Categories>>{
        return api.getCategoryAPI()
    }

}