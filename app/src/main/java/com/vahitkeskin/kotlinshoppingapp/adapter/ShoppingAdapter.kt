package com.vahitkeskin.kotlinshoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.databinding.ItemShoppingBinding
import com.vahitkeskin.kotlinshoppingapp.model.Shopping
import com.vahitkeskin.kotlinshoppingapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_shopping.view.*

class ShoppingAdapter(private val shoppingList: ArrayList<Shopping>) :
    RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>(), ShoppingClickListener {

    class ShoppingViewHolder(val view: ItemShoppingBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemShoppingBinding>(
            inflater,
            R.layout.item_shopping,
            parent,
            false
        )
        return ShoppingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        holder.view.shopping = shoppingList[position]
        holder.view.listener = this
    }

    fun updateShoppingList(newShoppingList: List<Shopping>) {
        shoppingList.clear()
        shoppingList.addAll(newShoppingList)
        notifyDataSetChanged()
    }

    override fun onShoppingClicked(v: View) {
        val uuid = v.shoppingUuidText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToShoppingFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}