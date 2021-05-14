package com.vahitkeskin.kotlinshoppingapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.services.ShoppingDatabase
import kotlinx.coroutines.launch

class ShoppingViewModel(application: Application) : BaseViewModel(application) {
    val shoppingLiveData = MutableLiveData<Shopping>()

    fun getDataFromRoom(uuid: Int) {
        launch {
            val dao = ShoppingDatabase(getApplication()).shoppingDao()
            val shopping = dao.getShopping(uuid)
            shoppingLiveData.value = shopping
        }
    }
}