package com.example.recipeapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "meals")
data class MealsItem(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("idMeal")
    val idMeal: String,

    @SerializedName("strMeal")
    val strMeal: String,

    @SerializedName("strMealThumb")
    val strMealThumb: String,

    @SerializedName("strInstructions")
    val strInstructions: String? = "",

    @SerializedName("strSource")
    val strSource: String?,

    @SerializedName("strIngredient1") val strIngredient1: String? = "",
    @SerializedName("strIngredient2") val strIngredient2: String? = "",
    @SerializedName("strIngredient3") val strIngredient3: String? = "",
    @SerializedName("strIngredient4") val strIngredient4: String? = "",
    @SerializedName("strIngredient5") val strIngredient5: String? = "",
    @SerializedName("strIngredient6") val strIngredient6: String? = "",
    @SerializedName("strIngredient7") val strIngredient7: String? = "",
    @SerializedName("strIngredient8") val strIngredient8: String? = "",
    @SerializedName("strIngredient9") val strIngredient9: String? = "",
    @SerializedName("strIngredient10") val strIngredient10: String? = "",

    @SerializedName("strMeasure1") val strMeasure1: String? = "",
    @SerializedName("strMeasure2") val strMeasure2: String? = "",
    @SerializedName("strMeasure3") val strMeasure3: String? = "",
    @SerializedName("strMeasure4") val strMeasure4: String? = "",
    @SerializedName("strMeasure5") val strMeasure5: String? = "",
    @SerializedName("strMeasure6") val strMeasure6: String? = "",
    @SerializedName("strMeasure7") val strMeasure7: String? = "",
    @SerializedName("strMeasure8") val strMeasure8: String? = "",
    @SerializedName("strMeasure9") val strMeasure9: String? = "",
    @SerializedName("strMeasure10") val strMeasure10: String? = "",

    var categoryName: String = ""
)