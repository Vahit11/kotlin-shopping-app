package com.vahitkeskin.kotlinshoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.databinding.ItemCategoryBinding
import com.vahitkeskin.kotlinshoppingapp.model.Categories

class CategoryAdapter(private val categoryArray: ArrayList<Categories>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(), CategoryClickListener {

    class CategoryViewHolder(val view: ItemCategoryBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemCategoryBinding>(
            inflater,
            R.layout.item_category,
            parent,
            false
        )
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        println("Image : ${categoryArray[position].categoryImage}")
        println("Name  : ${categoryArray[position].categoryName}")
        holder.view.category = categoryArray[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return categoryArray.size
    }

    fun categoryList(newCategoryList: List<Categories>) {
        categoryArray.clear()
        categoryArray.addAll(newCategoryList)
        notifyDataSetChanged()
    }

    override fun onCategoryClicked(view: View) {

    }

}