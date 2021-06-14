package com.vahitkeskin.kotlinshoppingapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.adapter.MovieAdapter
import com.vahitkeskin.kotlinshoppingapp.databinding.FragmentShoppingBinding
import com.vahitkeskin.kotlinshoppingapp.model.Products
import com.vahitkeskin.kotlinshoppingapp.viewmodel.ShoppingViewModel
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow


class ShoppingFragment : Fragment() {

    private lateinit var viewModel: ShoppingViewModel
    private var shoppingUuid = 0
    private lateinit var binding: FragmentShoppingBinding
    private var basketItem = 1

    //SwipeImageItem
    private var movieAdapter: MovieAdapter? = null
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

    private fun init() {

        arguments?.let {
            shoppingUuid = ShoppingFragmentArgs.fromBundle(it).selectedProductId
        }

        viewModel = ViewModelProviders.of(this).get(ShoppingViewModel::class.java)
        viewModel.getDataFromRoom(shoppingUuid)

        initData()
        observeLiteData()

        val randomPoint = (0 until 50).random() / 10.toFloat()
        binding.rbStarPoint.rating = randomPoint
        binding.tvStarPointSize.text = randomPoint.toString()

        binding.btnShoppingIncrease.setOnClickListener {
            basketItem++
            binding.etShoppingItem.setText("$basketItem")
        }

        binding.btnShoppingDecrease.setOnClickListener {
            if (basketItem > 0) {
                basketItem--
                binding.etShoppingItem.setText("$basketItem")
            }
        }

        binding.btnShoppingAddToBasket.setOnClickListener {
            basketItem = 1
            binding.etShoppingItem.setText("$basketItem")
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

        context?.let {
            movieAdapter = MovieAdapter(movieList, it)
            binding.coverFlow.adapter = movieAdapter
            binding.coverFlow.setOnScrollPositionListener(object :
                FeatureCoverFlow.OnScrollPositionListener {
                override fun onScrolledToPosition(position: Int) {
                    binding.title.setText(movieList[position].name)
                }

                override fun onScrolling() {
                    println("Not yet implemented")
                }

            })
        }
    }

    private fun initData() {
        for (index in 1..10) {
            movieList.add(
                Products(
                    "Product$index",
                    "http://www.vahitkeskin.com/Shopping/image$index.jpg"
                )
            )
        }
    }

    private fun observeLiteData() {
        viewModel.shoppingLiveData.observe(viewLifecycleOwner, { shopping ->
            shopping?.let {
                binding.selectedShopping = shopping
            }
        })
    }
}