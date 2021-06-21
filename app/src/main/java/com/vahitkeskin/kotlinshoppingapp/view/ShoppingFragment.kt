package com.vahitkeskin.kotlinshoppingapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.adapter.SelectedProductAdapter
import com.vahitkeskin.kotlinshoppingapp.databinding.FragmentShoppingBinding
import com.vahitkeskin.kotlinshoppingapp.model.Products
import com.vahitkeskin.kotlinshoppingapp.viewmodel.ShoppingViewModel
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow


class ShoppingFragment : Fragment() {

    private lateinit var viewModel: ShoppingViewModel
    private var shoppingUuid = 0
    private lateinit var binding: FragmentShoppingBinding
    private var basketItem = 1
    private var basketItemStock = 0
    private var selectedProductAdapter: SelectedProductAdapter? = null
    private var movieList: MutableList<Products> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @SuppressLint("InflateParams")
    private fun init() {

        arguments?.let {
            shoppingUuid = ShoppingFragmentArgs.fromBundle(it).selectedProductId
        }

        viewModel = ViewModelProviders.of(this).get(ShoppingViewModel::class.java)
        viewModel.getDataFromRoom(shoppingUuid)

        val randomPoint = (40 until 50).random() / 10.toFloat()
        binding.rbStarPoint.rating = randomPoint
        binding.tvStarPointSize.text = randomPoint.toString()

        binding.btnShoppingIncrease.setOnClickListener {
            if (basketItem >= basketItemStock) {
                largeOfProductLargeStock()
            } else {
                basketItem++
                binding.etShoppingItem.setText("$basketItem")
            }
        }

        binding.btnShoppingDecrease.setOnClickListener {
            if (basketItem > 0) {
                basketItem--
                binding.etShoppingItem.setText("$basketItem")
            }
        }

        binding.btnShoppingAddToBasket.setOnClickListener {
            productSavedDB()
        }

        binding.title.setFactory {
            val inflater: LayoutInflater = LayoutInflater.from(context)
            val txt = inflater.inflate(R.layout.layout_title, null) as TextView
            txt
        }

        val myIn: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_top)
        val myOut: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom)
        binding.title.inAnimation = myIn
        binding.title.outAnimation = myOut

        selectedProductAdapter = SelectedProductAdapter(movieList, requireContext())
        binding.coverFlow.adapter = selectedProductAdapter
        binding.coverFlow.setOnScrollPositionListener(object :
            FeatureCoverFlow.OnScrollPositionListener {
            override fun onScrolledToPosition(position: Int) {
                binding.title.setText(movieList[position].name)
            }

            override fun onScrolling() {
                println("Not yet implemented")
            }

        })
        selectedProductImages()
        observeLiteData()
    }

    private fun selectedProductImages() {
        for (index in 1..10) {
            movieList.add(
                Products(
                    "Product-$index",
                    "https://raw.githubusercontent.com/Vahit11/kotlin-shopping-app/master/Shopping/image$index.jpg"
                )
            )
        }
    }

    private fun observeLiteData() {
        viewModel.shoppingLiveData.observe(viewLifecycleOwner, { shopping ->
            shopping?.let {
                binding.selectedShopping = shopping
                basketItemStock = it.stock.toString().toInt()
            }
        })
    }

    private fun largeOfProductLargeStock() {
        Snackbar.make(binding.llShopping,"There are only $basketItemStock items in stock",Snackbar.LENGTH_LONG).show()
    }

    private fun productSavedDB() {
        val numberOfProducts = binding.etShoppingItem.text.toString()
        if (numberOfProducts.isEmpty() || numberOfProducts.toInt() <= 0) {
            binding.etShoppingItem.setText("1")
            Toast.makeText(context, "Is not null. Please!", Toast.LENGTH_LONG).show()
        } else {
            if (numberOfProducts.toInt() > basketItemStock) {
                largeOfProductLargeStock()
            } else {
                Toast.makeText(context, "Item size new: $basketItem", Toast.LENGTH_LONG).show()
                //Saved ROOM

            }
        }
    }
}