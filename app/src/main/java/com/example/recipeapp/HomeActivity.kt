package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Adapter.MainCategoryAdapter
import com.example.recipeapp.Adapter.SubCategoryAdapter
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.entity.CategoryItems
import com.example.recipeapp.entity.MealsItem
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {

    private lateinit var mainCategoryRecyclerView: RecyclerView
    private lateinit var subCategoryRecyclerView: RecyclerView
    private lateinit var tvCategory: TextView
    private lateinit var searchMeal: AutoCompleteTextView

    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItem>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mainCategoryRecyclerView = findViewById(R.id.mainRecyclerview)
        subCategoryRecyclerView = findViewById(R.id.subRecyclerview)
        tvCategory = findViewById(R.id.tvcategory)
        searchMeal = findViewById(R.id.searchMeal)

        setupAutoComplete()

        mainCategoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        subCategoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        mainCategoryRecyclerView.adapter = mainCategoryAdapter
        subCategoryRecyclerView.adapter = subCategoryAdapter

        mainCategoryAdapter.setClickListner(onClicked)
        subCategoryAdapter.setClickListner(onClickedSubItem)

        getCategoryDataFromDb()
    }
    //filter meals based on search query
    private fun filterMeals(query: String?) {

        val searchText = query?.trim() ?: ""

        if (searchText.isEmpty()) {
            subCategoryAdapter.setdata(arrSubCategory)
            return
        }

        launch {

            val result = RecipeDatabase
                .getDatabse(this@HomeActivity)
                .recipeDao()
                .searchMeals("%$searchText%") // 🔥 important

            subCategoryAdapter.setdata(ArrayList(result))
        }
    }

    private fun setupAutoComplete() {

        launch {

            val mealList = RecipeDatabase
                .getDatabse(this@HomeActivity)
                .recipeDao()
                .getAllMealNames()  // ⚠️ you need to create this DAO method

            val adapter = ArrayAdapter(
                this@HomeActivity,
                android.R.layout.simple_dropdown_item_1line,
                mealList
            )

            searchMeal.setAdapter(adapter)
            searchMeal.threshold = 1

            // When user selects item
            searchMeal.setOnItemClickListener { parent, _, position, _ ->
                val selectedMeal = parent.getItemAtPosition(position).toString()
                filterMeals(selectedMeal)
            }
        }
    }
    // ------------------- CATEGORY CLICK -------------------

    private val onClicked = object : MainCategoryAdapter.OnItemClickListener {
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }
    }

    // ------------------- MEAL CLICK -------------------

    private val onClickedSubItem = object : SubCategoryAdapter.OnItemClickListener {
        override fun onClicked(id: String) {

            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)

        }
    }

    // ------------------- LOAD CATEGORY -------------------

    private fun getCategoryDataFromDb() {
        launch {
            val catList = RecipeDatabase.getDatabse(this@HomeActivity).recipeDao().getallCategory()
            arrMainCategory = ArrayList(catList)  // Use .reversed() for efficiency

            mainCategoryAdapter.setData(catList)

            if (arrMainCategory.isNotEmpty()) {
                getMealDataFromDb(arrMainCategory[0].strcategory)
            } else {
                // Fallback UI
                tvCategory.text = "No categories available"
                // Or retry network
            }
        }
    }

    // ------------------- LOAD MEALS -------------------

    private fun getMealDataFromDb(categoryName: String) {

        tvCategory.text = "$categoryName Category"

        launch {

            val mealList = RecipeDatabase
                .getDatabse(this@HomeActivity)
                .recipeDao()
                .getSpecificMEalList(categoryName)

            arrSubCategory = ArrayList(mealList)

            subCategoryAdapter.setdata(arrSubCategory)

        }


    }
}