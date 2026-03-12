package com.example.recipeapp.entity.converter

import androidx.room.TypeConverter
import com.example.recipeapp.entity.CategoryItems
import com.example.recipeapp.entity.MealsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealListConverter {

    private val gson = Gson()

    // ✅ Convert List<CategoryItems> → String
    @TypeConverter
    fun fromCategoryList(category: List<MealsItem>?): String? {
        return gson.toJson(category)
    }

    // ✅ Convert String → List<CategoryItems>
    @TypeConverter
    fun toCategoryList(categoryString: String?): List<MealsItem>? {

        if (categoryString == null) return null

        val type = object : TypeToken<List<MealsItem>>() {}.type
        return gson.fromJson(categoryString, type)
    }
}
