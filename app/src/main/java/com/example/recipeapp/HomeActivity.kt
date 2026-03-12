package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
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