package com.vahitkeskin.kotlinshoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.adapter.SearchCategoryAdapter
import com.vahitkeskin.kotlinshoppingapp.viewmodel.BaseViewModel
import com.vahitkeskin.kotlinshoppingapp.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private var searchCategoryAdapter = SearchCategoryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.getCategoryAPI()

        //FAB
        rv_search_fragment_category_fab.layoutManager = LinearLayoutManager(activity?.baseContext)
        rv_search_fragment_category_fab.adapter = searchCategoryAdapter
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.categories.observe(viewLifecycleOwner, {categories ->
            categories?.let {
                searchCategoryAdapter.fabCategoryList(it)
            }
        })
    }
}