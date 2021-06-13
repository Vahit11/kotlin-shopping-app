package com.vahitkeskin.kotlinshoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vahitkeskin.kotlinshoppingapp.adapter.SearchCategoryAdapter
import com.vahitkeskin.kotlinshoppingapp.adapter.SearchProductsAdapter
import com.vahitkeskin.kotlinshoppingapp.databinding.FragmentSearchBinding
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.viewmodel.FeedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), SearchCategoryAdapter.SearchProductListener {

    private lateinit var viewModel: FeedViewModel
    private var searchCategoryAdapter = SearchCategoryAdapter(arrayListOf(), this)
    private var searchProductsAdapter = SearchProductsAdapter(arrayListOf())
    private var mShopping: ArrayList<Shopping> = ArrayList()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        viewModel.getCategoryAPI()

        //FAB
        binding.rvSearchFragmentCategoryFab.layoutManager = LinearLayoutManager(activity?.baseContext)
        binding.rvSearchFragmentCategoryFab.adapter = searchCategoryAdapter
        observeLiveData()

        //Products
        binding.rvSearchFragmentProducts.layoutManager = LinearLayoutManager(activity?.baseContext)
        binding.rvSearchFragmentProducts.adapter = searchProductsAdapter
        observeLiveDataProducts()

        var job: Job? = null
        binding.etSearchFragmentSearch.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                binding.ibSearchEditTextClear.isVisible = true
                delay(1000)
                it.toString().let {
                    if (it.isNotEmpty()) {
                        observeLiveDataSearch(it)
                    }
                }
            }
        }

        binding.ibSearchEditTextClear.setOnClickListener {
            binding.etSearchFragmentSearch.text.clear()
            observeLiveDataProducts()
        }
    }

    private fun observeLiveDataProducts() {
        binding.ibSearchEditTextClear.isVisible = false
        viewModel.shopping.observe(viewLifecycleOwner, { shoppingList ->
            shoppingList?.let { list ->
                searchProductsAdapter.categoriesNewList(list)
            }
        })
    }

    private fun observeLiveData() {
        viewModel.categories.observe(viewLifecycleOwner, { categories ->
            categories?.let {
                searchCategoryAdapter.fabCategoryList(it)
            }
        })
    }

    override fun onSearchProductListener(position: Int, searchProductName: String) {
        observeLiveDataSearch(searchProductName)
    }

    private fun observeLiveDataSearch(search: String) {
        viewModel.shopping.observe(viewLifecycleOwner, { shoppingList ->
            shoppingList?.let {
                mShopping.clear()
                for (list in it) {
                    if (list.category == search) {
                        mShopping.add(list)
                    }
                }
                binding.tvSearchItemResult.isVisible = true
                binding.tvSearchItemResult.text = " \"$search\" ${mShopping.size} Result Found"
                searchProductsAdapter = SearchProductsAdapter(mShopping)
                binding.rvSearchFragmentProducts.adapter = searchProductsAdapter
                searchProductsAdapter.notifyDataSetChanged()
            }
        })
    }
}