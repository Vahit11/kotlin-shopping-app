package com.vahitkeskin.kotlinshoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.adapter.ShoppingAdapter
import com.vahitkeskin.kotlinshoppingapp.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private var shoppingAdapter = ShoppingAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        shoppingList.layoutManager = LinearLayoutManager(context)
        shoppingList.adapter = shoppingAdapter

        swipeRefreshLayout.setOnRefreshListener {
            shoppingList.visibility = View.GONE
            shoppingError.visibility = View.GONE
            shoppingLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.shopping.observe(viewLifecycleOwner, { shopping ->
            shopping?.let {
                shoppingList.visibility = View.VISIBLE
                shoppingAdapter.updateShoppingList(shopping)
            }

        })

        viewModel.shoppingError.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it) {
                    shoppingError.visibility = View.VISIBLE
                } else {
                    shoppingError.visibility = View.GONE
                }
            }
        })

        viewModel.shoppingLoading.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it) {
                    shoppingLoading.visibility = View.VISIBLE
                    shoppingList.visibility = View.GONE
                    shoppingError.visibility = View.GONE
                } else {
                    shoppingLoading.visibility = View.GONE
                }
            }
        })
    }
}