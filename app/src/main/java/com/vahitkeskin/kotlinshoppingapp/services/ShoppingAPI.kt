package com.vahitkeskin.kotlinshoppingapp.services

import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.util.UtilStrings.GET_URL
import io.reactivex.Single
import retrofit2.http.GET

interface ShoppingAPI {
    @GET(GET_URL)
    fun getShoppingAPI(): Single<List<Shopping>>
}