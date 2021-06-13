package com.vahitkeskin.kotlinshoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.model.Categories
import kotlinx.android.synthetic.main.search_fragment_left_fab.view.*

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
        val categoryItemList = categoryItem[position]

        holder.itemView.setOnLongClickListener {
            Toast.makeText(holder.itemView.context,categoryItemList.categoryName,Toast.LENGTH_LONG).show()
            true
        }

        when (categoryItemList.categoryName) {
            "Araba" -> holder.itemView.search_fab.setImageResource(R.drawable.ic_baseline_directions_car_24)
            "BeyazEsya" -> holder.itemView.search_fab.setImageResource(R.drawable.ic_baseline_kitchen_24)
            "Bilgisayar" -> holder.itemView.search_fab.setImageResource(R.drawable.ic_baseline_computer_24)
            "KucukEvAletleri" -> holder.itemView.search_fab.setImageResource(R.drawable.ic_baseline_category_24_white)
            "Telefon" -> holder.itemView.search_fab.setImageResource(R.drawable.ic_baseline_phone_android_24)
            "Televizyon" -> holder.itemView.search_fab.setImageResource(R.drawable.ic_baseline_tv_24)
            else -> holder.itemView.search_fab.setImageResource(R.drawable.ic_shopping_white)
        }

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