package com.example.recipeapp.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MealResponse(

    @Expose
    @SerializedName("meals")
    val mealsEntity: List<MealsItem>
)