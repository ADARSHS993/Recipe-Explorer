package com.example.recipeapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.recipeapp.entity.converter.MealListConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("meals")
    var mealsItem: List<MealsItem>
)