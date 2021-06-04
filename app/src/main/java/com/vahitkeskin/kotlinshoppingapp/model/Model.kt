package com.vahitkeskin.kotlinshoppingapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Shopping(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int? = null,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,
    @ColumnInfo(name = "category")
    @SerializedName("category")
    val category: String?,
    @ColumnInfo(name = "price")
    @SerializedName("price")
    val price: String?,
    @ColumnInfo(name = "stock")
    @SerializedName("stock")
    val stock: String?,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: String?,
)