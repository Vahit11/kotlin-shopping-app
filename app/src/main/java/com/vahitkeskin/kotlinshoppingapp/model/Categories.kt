package com.vahitkeskin.kotlinshoppingapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Categories(
    @ColumnInfo(name = "categoryname")
    @SerializedName("categoryname")
    val categoryName: String?,
    @ColumnInfo(name = "categoryimage")
    @SerializedName("categoryimage")
    val categoryImage: String?
)