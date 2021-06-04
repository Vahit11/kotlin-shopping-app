package com.vahitkeskin.kotlinshoppingapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.vahitkeskin.kotlinshoppingapp.model.Categories
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.services.ShoppingAPIService
import com.vahitkeskin.kotlinshoppingapp.services.ShoppingDatabase
import com.vahitkeskin.kotlinshoppingapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val shoppingAPIService = ShoppingAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L //10 Dakka

    val shopping = MutableLiveData<List<Shopping>>()
    val categories = MutableLiveData<List<Categories>>()
    val shoppingError = MutableLiveData<Boolean>()
    val shoppingLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            // Internetten cek
            getDataFromAPI()
        }
    }

    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        shoppingLoading.value = true
        launch {
            val shopping = ShoppingDatabase(getApplication()).shoppingDao().getAllShopping()
            showShopping(shopping)
            Toast.makeText(getApplication(), "Shopping From SQLite", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI() {
        shoppingLoading.value = true

        disposable.add(
            shoppingAPIService.getShoppingData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Shopping>>() {
                    override fun onSuccess(t: List<Shopping>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Shopping From API", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        shoppingLoading.value = false
                        shoppingError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    fun getCategoryAPI() {
        disposable.addAll(
            shoppingAPIService.getCategoryData()
                .subscribeOn(Schedulers.newThread())
                .observeOn((AndroidSchedulers.mainThread()))
                .subscribeWith(object : DisposableSingleObserver<List<Categories>>() {
                    override fun onSuccess(t: List<Categories>) {
                        categories.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showShopping(shoppingList: List<Shopping>) {
        shopping.value = shoppingList
        shoppingError.value = false
        shoppingLoading.value = false
    }

    private fun storeInSQLite(list: List<Shopping>) {
        launch {
            val dao = ShoppingDatabase(getApplication()).shoppingDao()
            dao.deleteAllShopping()
            dao.insertAll(*list.toTypedArray()) // -> list individual
            showShopping(list)
        }

        customPreferences.saveTime(System.nanoTime())
    }

    //Hafizayi cok daha verimli yapar
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}