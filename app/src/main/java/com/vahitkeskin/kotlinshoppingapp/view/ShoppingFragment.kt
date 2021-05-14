package com.vahitkeskin.kotlinshoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.databinding.FragmentShoppingBinding
import com.vahitkeskin.kotlinshoppingapp.viewmodel.ShoppingViewModel

class ShoppingFragment : Fragment() {

    private lateinit var viewModel: ShoppingViewModel
    private var shoppingUuid = 0
    private lateinit var dataBinding: FragmentShoppingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping, container, false)
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