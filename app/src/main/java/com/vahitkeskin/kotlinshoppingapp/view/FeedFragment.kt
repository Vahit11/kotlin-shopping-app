package com.vahitkeskin.kotlinshoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.adapter.CategoryAdapter
import com.vahitkeskin.kotlinshoppingapp.adapter.ShoppingAdapter
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(), CategoryAdapter.CategoryListener {

    private lateinit var viewModel: FeedViewModel
    private var shoppingAdapter = ShoppingAdapter(arrayListOf())
    private var categoryAdapter = CategoryAdapter(arrayListOf(), this)
    private var mShopping: ArrayList<Shopping> = ArrayList()

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
        viewModel.getCategoryAPI()

        //Shopping
        shoppingList.layoutManager = LinearLayoutManager(context)
        shoppingList.adapter = shoppingAdapter


        //Category
        rvCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvCategory.adapter = categoryAdapter

        swipeRefreshLayout.setOnRefreshListener {
            shoppingList.visibility = View.GONE
            shoppingError.visibility = View.GONE
            shoppingLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveDataCategory()
        observeLiveData()
    }

    private fun observeLiveDataCategory() {
        viewModel.categories.observe(viewLifecycleOwner, { categories ->
            categories.let {
                categoryAdapter.categoryList(categories)
            }
        })
    }

    private fun observeLiveData() {
        viewModel.shopping.observe(viewLifecycleOwner, { shopping ->
            shopping?.let {
                shoppingList.visibility = View.VISIBLE
                shoppingAdapter.updateShoppingList(shopping)
            }

        })
        notSuccess()

    }

    override fun onCategoryClickListener(position: Int, categoryName: String) {
        viewModel.shopping.observe(viewLifecycleOwner, { shopping ->
            shopping?.let {
                mShopping.clear()
                for (shoppingItem in shopping) {
                    if (shoppingItem.category.equals(categoryName, ignoreCase = true)) {
                        mShopping.add(shoppingItem)
                    }
                }
                shoppingAdapter = ShoppingAdapter(mShopping)
                shoppingList.adapter = shoppingAdapter
                shoppingAdapter.notifyDataSetChanged()
            }

        })
        notSuccess()
    }

    private fun notSuccess() {
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