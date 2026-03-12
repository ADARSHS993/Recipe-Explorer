package com.example.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.Dao.RecipeDao
import com.example.recipeapp.entity.CategoryItems
import com.example.recipeapp.entity.MealsItem   // ✅ IMPORT THIS

@Database(
    entities = [
        CategoryItems::class,
        MealsItem::class        // ✅ ADD THIS
    ],
    version = 2,               // ⚠️ Increase version
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    companion object {

        private var INSTANCE: RecipeDatabase? = null

        fun getDatabse(context: Context): RecipeDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_db"
                )
                    .fallbackToDestructiveMigration()   // ✅ IMPORTANT
                    .build()
            }

            return INSTANCE!!
        }
    }
}