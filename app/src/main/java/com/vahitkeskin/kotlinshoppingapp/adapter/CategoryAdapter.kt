package com.vahitkeskin.kotlinshoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.model.Categories
import kotlinx.android.synthetic.main.item_category.view.*
import java.lang.Exception

class CategoryAdapter(
    private val categoryArray: ArrayList<Categories>, private val categoryListener: CategoryListener
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(), CategoryClickListener {

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = DataBindingUtil.inflate<ItemCategoryBinding>(inflater, R.layout.item_category, parent, false)
        val view = inflater.inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryList = categoryArray[position]
        holder.itemView.tvCategoryName.text = categoryList.categoryName
        holder.itemView.setOnClickListener {
            categoryList.categoryName?.let { name ->
                categoryListener.onCategoryClickListener(position, categoryList.categoryName)
            }
        }
        Picasso.get().load(categoryList.categoryImage)
            .fit()
            .centerCrop()
            .into(holder.itemView.ivCategoryImage, object : Callback {
                override fun onSuccess() {
                    holder.itemView.pbCategoryImage.isVisible = false
                }

                override fun onError(e: Exception?) {
                    holder.itemView.pbCategoryImage.isVisible = true
                }

            })
    }

    override fun getItemCount(): Int {
        return categoryArray.size
    }

    fun categoryList(newCategoryList: List<Categories>) {
        categoryArray.clear()
        categoryArray.addAll(newCategoryList)
        notifyDataSetChanged()
    }

    override fun onCategoryClicked(view: View) {}


    interface CategoryListener {
        fun onCategoryClickListener(position: Int,categoryName: String)
    }

}