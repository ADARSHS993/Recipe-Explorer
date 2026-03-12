package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.recipeapp.Retrofitclient.RetrofitClientInstance
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.entity.CategoryItems
import com.example.recipeapp.entity.CategoryResponse
import com.example.recipeapp.entity.Meal
import com.example.recipeapp.entity.MealsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplaceScreen : BaseActivity() {

    private lateinit var loader: ProgressBar

    private var totalCategories = 0
    private var loadedCategories = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        loader = findViewById(R.id.loader)

        // Clear DB first, then start API
        GlobalScope.launch(Dispatchers.IO) {
            RecipeDatabase.getDatabse(this@SplaceScreen)
                .recipeDao()
                .clearDb()

            runOnUiThread {
                getCategories()
            }
        }
    }

    // ---------------- FETCH CATEGORIES ----------------

    private fun getCategories() {

        val service = RetrofitClientInstance.instance

        service.getCategoryList().enqueue(object : Callback<CategoryResponse> {

            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {

                    val nonVegList = listOf("Beef", "Lamb")

                    val filteredCategories =
                        response.body()!!.categories.filter {
                            !nonVegList.contains(it.strcategory)
                        }

                    insertCategoriesIntoRoomDb(filteredCategories)

                    totalCategories = filteredCategories.size

                    if (totalCategories == 0) {
                        goToHome()
                        return
                    }

                    for (cat in filteredCategories) {
                        getMealsByCategory(cat.strcategory)
                    }

                } else {
                    goToHome()
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Toast.makeText(this@SplaceScreen, "Category API Failed", Toast.LENGTH_LONG).show()
                goToHome()
            }
        })
    }

    // ---------------- FETCH MEALS ----------------

    private fun getMealsByCategory(categoryName: String) {

        val service = RetrofitClientInstance.instance

        service.getMealList(categoryName).enqueue(object : Callback<Meal> {

            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {

                if (response.isSuccessful && response.body() != null) {

                    insertMealItemsIntoRoomDb(categoryName, response.body())

                    loadedCategories++

                    if (loadedCategories == totalCategories) {
                        loader.visibility = View.GONE
                        startActivity(Intent(this@SplaceScreen, HomeActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {

                loadedCategories++

                if (loadedCategories == totalCategories) {
                    goToHome()
                }

                Toast.makeText(this@SplaceScreen, "Meal API Failed", Toast.LENGTH_LONG).show()
            }
        })
    }

    // ---------------- INSERT CATEGORY ----------------

    private fun insertCategoriesIntoRoomDb(categories: List<CategoryItems>) {

        GlobalScope.launch(Dispatchers.IO) {

            val dao = RecipeDatabase.getDatabse(this@SplaceScreen).recipeDao()

            for (item in categories) {
                dao.insertcategory(item)
            }
        }
    }

    // ---------------- INSERT MEALS ----------------

    private fun insertMealItemsIntoRoomDb(categoryName: String, meal: Meal?) {

        GlobalScope.launch(Dispatchers.IO) {

            val dao = RecipeDatabase.getDatabse(this@SplaceScreen).recipeDao()

            meal?.mealsItem?.forEach { arr ->

                val mealItemModel = MealsItem(
                    idMeal = arr.idMeal,
                    strMeal = arr.strMeal,
                    strMealThumb = arr.strMealThumb,
                    strSource = arr.strSource,
                    categoryName = categoryName
                )

                dao.insertmeal(mealItemModel)
            }
        }
    }

    // ---------------- NAVIGATION ----------------

    private fun goToHome() {
        loader.visibility = View.GONE
        startActivity(Intent(this@SplaceScreen, HomeActivity::class.java))
        finish()
    }
}