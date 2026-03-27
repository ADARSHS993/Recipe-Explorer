package com.example.recipeapp.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.entity.CategoryItems
import com.example.recipeapp.entity.MealsItem

@Dao
interface RecipeDao {

    @Query("SELECT * FROM categoryitems ORDER BY id DESC")
    suspend fun getallCategory(): List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertcategory(categoryItems: CategoryItems)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertmeal(mealItems: MealsItem)

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()

    @Query("SELECT * FROM meals WHERE categoryName = :categoryName ORDER BY idMeal DESC")
    suspend fun getSpecificMEalList(categoryName: String): List<MealsItem>

    @Query("SELECT * FROM meals WHERE strMeal LIKE :query")
    suspend fun searchMeals(query: String): List<MealsItem>

    @Query("SELECT strMeal FROM meals")
    suspend fun getAllMealNames(): List<String>
}