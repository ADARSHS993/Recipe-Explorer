package com.example.recipeapp.`interface`

import com.example.recipeapp.entity.CategoryResponse
import com.example.recipeapp.entity.Meal
import com.example.recipeapp.entity.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query   // ✅ FIXED

interface GetDataService {

    @GET("categories.php")
    fun getCategoryList(): Call<CategoryResponse>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getSpecified(@Query("i") id: String): Call<MealResponse>
}