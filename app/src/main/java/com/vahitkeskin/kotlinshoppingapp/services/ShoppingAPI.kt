package com.vahitkeskin.kotlinshoppingapp.services

import com.vahitkeskin.kotlinshoppingapp.model.Categories
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.util.UtilStrings.CATEGORY_URL
import com.vahitkeskin.kotlinshoppingapp.util.UtilStrings.SHOPPING_URL
import io.reactivex.Single
import retrofit2.http.GET

interface ShoppingAPI {
    @GET(SHOPPING_URL)
    fun getShoppingAPI(): Single<List<Shopping>>

    @GET(CATEGORY_URL)
    fun getCategoryAPI(): Single<List<Categories>>
}