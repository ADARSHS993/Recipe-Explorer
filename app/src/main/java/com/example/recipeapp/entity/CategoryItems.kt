package com.example.recipeapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "categoryitems")
data class CategoryItems(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @SerializedName("idCategory")
    val idcategory: String,

    @SerializedName("strCategory")
    val strcategory: String,

    @SerializedName("strCategoryThumb")
    val strcategorythumb: String,

    @SerializedName("strCategoryDescription")
    val strcategorydescription: String
)

