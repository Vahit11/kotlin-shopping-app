package com.vahitkeskin.kotlinshoppingapp.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.model.Categories
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    private val categoryArray: ArrayList<Categories>, private val categoryListener: CategoryListener
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(), CategoryClickListener {

    private val categoryBorderColors: Array<String> = arrayOf(
        "#403e91",
        "#8ee81f",
        "#bd7ad5",
        "#973b5a",
        "#1cd678",
        "#bf2e4d",
        "#0a8afd",
        "#9ce1c8",
        "#f09d94",
        "#fe85fa",
        "#8586a3",
        "#84dcb8"
    )

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val relativeLayout: RelativeLayout = view.findViewById(R.id.rlCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryList = categoryArray[position]
        holder.itemView.tvCategoryName.text = categoryList.categoryName
        holder.itemView.setOnClickListener {
            categoryList.categoryName?.let { name ->
                categoryListener.onCategoryClickListener(position, name)
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

        val gd = GradientDrawable()
        gd.cornerRadius = 10f
        gd.setStroke(
            15,
            Color.parseColor(categoryBorderColors[position % 6])
        )
        holder.relativeLayout.setBackgroundDrawable(gd)

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
        fun onCategoryClickListener(position: Int, categoryName: String)
    }

}