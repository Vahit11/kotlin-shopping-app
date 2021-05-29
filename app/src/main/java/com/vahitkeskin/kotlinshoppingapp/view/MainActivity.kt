package com.vahitkeskin.kotlinshoppingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("Hello MainActivity")

        val navController = findNavController(this, R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}