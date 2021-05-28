package com.vahitkeskin.kotlinshoppingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vahitkeskin.kotlinshoppingapp.R
import com.vahitkeskin.kotlinshoppingapp.model.Movie
import java.lang.Exception

class MovieAdapter(
    private val movieList: List<Movie>,
    private val mContext: Context
) : BaseAdapter() {
    override fun getCount(): Int {
        return movieList.size
    }

    override fun getItem(position: Int): Any {
        return movieList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var rowView = convertView
        if (rowView == null) {
            rowView = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false)
            val name: TextView = rowView.findViewById(R.id.label)
            val image: ImageView = rowView.findViewById(R.id.image)
            val progressBar: ProgressBar = rowView.findViewById(R.id.pbLayoutItem)

            Picasso.get().load(movieList[position].imageURL).into(image,object: Callback {
                override fun onSuccess() {
                    progressBar.isVisible = false
                }

                override fun onError(e: Exception?) {
                    progressBar.isVisible = true
                }

            })

            name.text = movieList[position].name
        }
        return rowView
    }
}