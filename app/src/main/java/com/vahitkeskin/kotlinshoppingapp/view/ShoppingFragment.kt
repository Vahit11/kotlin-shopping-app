package com.vahitkeskin.kotlinshoppingapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.databinding.FragmentShoppingBinding
import com.vahitkeskin.kotlinshoppingapp.viewmodel.ShoppingViewModel
import kotlinx.android.synthetic.main.fragment_shopping.*
import kotlinx.coroutines.delay

class ShoppingFragment : Fragment() {

    private lateinit var viewModel: ShoppingViewModel
    private var shoppingUuid = 0
    private lateinit var dataBinding: FragmentShoppingBinding
    private var basketItem = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shopping, container, false)

        val randomPoint = (0 until 50).random()/ 10.toFloat()
        dataBinding.rbStarPoint.rating = randomPoint
        dataBinding.tvStarPointSize.text = randomPoint.toString()

        dataBinding.btnShoppingIncrease.setOnClickListener {
            basketItem++
            dataBinding.etShoppingItem.setText("$basketItem")
        }

        dataBinding.btnShoppingDecrease.setOnClickListener {
            if (basketItem > 0) {
                basketItem--
                dataBinding.etShoppingItem.setText("$basketItem")
            }
        }

        dataBinding.btnShoppingAddToBasket.setOnClickListener {
            basketItem = 1
            dataBinding.etShoppingItem.setText("$basketItem")
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            shoppingUuid = ShoppingFragmentArgs.fromBundle(it).shoppingUuid
            println("shoppingUuid: $shoppingUuid")
        }

        viewModel = ViewModelProviders.of(this).get(ShoppingViewModel::class.java)
        viewModel.getDataFromRoom(shoppingUuid)

        observeLiteData()
    }

    private fun observeLiteData() {
        viewModel.shoppingLiveData.observe(viewLifecycleOwner, { shopping ->
            shopping?.let {
                dataBinding.selectedShopping = shopping
            }
        })
    }
}