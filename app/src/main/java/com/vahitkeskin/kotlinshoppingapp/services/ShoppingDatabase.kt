package com.vahitkeskin.kotlinshoppingapp.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.util.UtilStrings.DB_NAME

@Database(entities = [Shopping::class],version = 1)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingDao() : ShoppingDao

    //Singleton

    //Her yerden ulasim
    companion object {
        //Volatile herhanbiri degisken tanimlandinda farklı threadlarde de görünür
        @Volatile private var instance : ShoppingDatabase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            //also -> bunu yap ustune birde sunuda yap
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, ShoppingDatabase::class.java,DB_NAME
        ).build()
    }

}