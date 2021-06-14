package com.vahitkeskin.kotlinshoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.databinding.SearchFragmentProductsBinding
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.view.SearchFragmentDirections
import kotlinx.android.synthetic.main.search_fragment_products.view.*

class SearchProductsAdapter(
    private val searchProducts: ArrayList<Shopping>
) : RecyclerView.Adapter<SearchProductsAdapter.SearchProductsViewHolder>(), ShoppingClickListener {

    class SearchProductsViewHolder(val view: SearchFragmentProductsBinding) :
        RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchProductsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<SearchFragmentProductsBinding>(
            inflater,
            R.layout.search_fragment_products,
            parent,
            false
        )
        return SearchProductsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: SearchProductsViewHolder,
        position: Int
    ) {
        holder.view.search = searchProducts[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return searchProducts.size
    }

    fun categoriesNewList(newListCategory: List<Shopping>) {
        searchProducts.clear()
        searchProducts.addAll(newListCategory)
        notifyDataSetChanged()
    }

    override fun onShoppingClicked(v: View) {
        val id = v.searchCategoryItem.text.toString().toInt()
        val action = SearchFragmentDirections.actionSearchFragmentToShoppingFragment2(id)
        Navigation.findNavController(v).navigate(action)
    }
}