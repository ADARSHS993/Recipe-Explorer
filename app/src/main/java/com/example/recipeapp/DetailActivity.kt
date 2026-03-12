package com.example.recipeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.example.recipeapp.Retrofitclient.RetrofitClientInstance
import com.example.recipeapp.entity.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : BaseActivity() {

    var id: String = ""
    var sourceLink = ""

    private lateinit var scrollview: NestedScrollView
    private lateinit var tvCategory: TextView
    private lateinit var tvInstructions: TextView
    private lateinit var tvIntegrats: TextView
    private lateinit var imgMeal: ImageView

    private lateinit var imgBackbtn: ImageButton
    private lateinit var imgfavorite: ImageButton
    private lateinit var youtubeBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Views
        scrollview = findViewById(R.id.detailScroll)
        tvCategory = findViewById(R.id.tvCategoryName)
        tvIntegrats = findViewById(R.id.tvIngredients)
        tvInstructions = findViewById(R.id.tvInstructions)
        imgMeal = findViewById(R.id.imageView)

        imgBackbtn = findViewById(R.id.back_arrow)
        imgfavorite = findViewById(R.id.favourite)
        youtubeBtn = findViewById(R.id.youtubebtn)

        // Get meal id from intent
        id = intent.getStringExtra("id") ?: ""

        // Load meal detail
        getSpecificItem(id)

        // Back button
        imgBackbtn.setOnClickListener {
            finish()
        }

        // Open recipe source
        youtubeBtn.setOnClickListener {

            if (sourceLink.isNotEmpty()) {

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(sourceLink)
                startActivity(intent)

            } else {

                Toast.makeText(
                    this,
                    "Source not available",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun getSpecificItem(id: String) {

        val service = RetrofitClientInstance.instance

        service.getSpecified(id)
            .enqueue(object : Callback<MealResponse> {

                override fun onResponse(
                    call: Call<MealResponse>,
                    response: Response<MealResponse>
                ) {

                    if (response.body() != null) {

                        val meal = response.body()!!.mealsEntity[0]

                        // Load Image
                        Glide.with(this@DetailActivity)
                            .load(meal.strMealThumb)
                            .into(imgMeal)

                        // Meal Name
                        tvCategory.text = meal.strMeal

                        // Ingredients
                        val ingredient = """
                        ${meal.strIngredient1} ${meal.strMeasure1}
                        ${meal.strIngredient2} ${meal.strMeasure2}
                        ${meal.strIngredient3} ${meal.strMeasure3}
                        ${meal.strIngredient4} ${meal.strMeasure4}
                        ${meal.strIngredient5} ${meal.strMeasure5}
                        ${meal.strIngredient6} ${meal.strMeasure6}
                        ${meal.strIngredient7} ${meal.strMeasure7}
                        ${meal.strIngredient8} ${meal.strMeasure8}
                        ${meal.strIngredient9} ${meal.strMeasure9}
                        ${meal.strIngredient10} ${meal.strMeasure10}
                        """.trimIndent()

                        tvIntegrats.text = ingredient

                        // Instructions
                        tvInstructions.text = meal.strInstructions

                        // Source link
                        if (meal.strSource != null) {
                            sourceLink = meal.strSource!!
                        }else{
                            youtubeBtn.visibility = View.GONE
                        }
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {

                    Toast.makeText(
                        this@DetailActivity,
                        "Specific Meal API Failed: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}