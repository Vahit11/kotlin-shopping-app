package com.vahitkeskin.kotlinshoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import kotlinx.android.synthetic.main.search_fragment_products.view.*

class SearchProductsAdapter(
    private val searchProducts: ArrayList<Shopping>
): RecyclerView.Adapter<SearchProductsAdapter.SearchProductsViewHolder>() {

    class SearchProductsViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_fragment_products,parent,false)
        return SearchProductsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SearchProductsViewHolder,
        position: Int
    ) {
        val list = searchProducts[position]
        holder.itemView.tvSearchFragmentProductName.text = list.name
        holder.itemView.tvSearchFragmentProductPrice.text = list.price
        holder.itemView.tvSearchFragmentProductStock.text = list.stock
        Picasso.get().load(list.image)
            .centerCrop()
            .fit()
            .into(holder.itemView.ivSearchFragmentProduct)
    }

    override fun getItemCount(): Int {
        return searchProducts.size
    }

    fun categoriesNewList(newListCategory: List<Shopping>) {
        searchProducts.clear()
        searchProducts.addAll(newListCategory)
        notifyDataSetChanged()
    }

}