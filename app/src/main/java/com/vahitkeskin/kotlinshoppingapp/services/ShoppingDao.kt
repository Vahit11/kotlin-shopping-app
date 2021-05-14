package com.vahitkeskin.kotlinshoppingapp.services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vahitkeskin.kotlinshoppingapp.model.Shopping

@Dao
interface ShoppingDao {

    @Insert
    suspend fun insertAll(vararg shopping: Shopping): List<Long>

    @Query("SELECT * FROM shopping")
    suspend fun getAllShopping(): List<Shopping>

    @Query("SELECT * FROM shopping WHERE uuid = :shoppingId")
    suspend fun getShopping(shoppingId: Int): Shopping

    @Query("DELETE FROM shopping")
    suspend fun deleteAllShopping()

}