package com.vahitkeskin.kotlinshoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.model.Categories

class SearchCategoryAdapter(
    private val categoryItem: ArrayList<Categories>
) : RecyclerView.Adapter<SearchCategoryAdapter.SearchCategoryViewHolder>() {

    class SearchCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_fragment_left_fab, parent, false)
        return SearchCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchCategoryViewHolder, position: Int) {
        println("Category left item name: ${categoryItem[position].categoryName}")
    }

    override fun getItemCount(): Int {
        return categoryItem.size
    }

    fun fabCategoryList(newFabCategoryList: List<Categories>) {
        categoryItem.clear()
        categoryItem.addAll(newFabCategoryList)
        notifyDataSetChanged()
    }

}