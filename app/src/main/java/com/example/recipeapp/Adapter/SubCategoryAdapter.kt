package com.example.recipeapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.entity.MealsItem

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {
    var listener: SubCategoryAdapter.OnItemClickListener? = null
    var subRecipeArr = ArrayList<MealsItem>()
    class RecipeViewHolder(view : View): RecyclerView.ViewHolder(view){


        val tvdishname : TextView = view.findViewById(R.id.tv_dish_name)
        val disImage : ImageView = view.findViewById(R.id.img_item)
    }

    fun setdata(dataList: List<MealsItem>) {
        subRecipeArr = ArrayList(dataList)
        notifyDataSetChanged()
    }

    fun setClickListner(listner1 : SubCategoryAdapter.OnItemClickListener){
        listener = listner1
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SubCategoryAdapter.RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sub_categegory,parent,false))
    }

    override fun onBindViewHolder(
        holder: SubCategoryAdapter.RecipeViewHolder,
        position: Int,
    ) {
        val recipe = subRecipeArr[position]

        holder.tvdishname.text = recipe.strMeal
        Glide.with(holder.itemView.context).load(recipe.strMealThumb).into(holder.disImage)
        holder.itemView.rootView.setOnClickListener {
            listener?.onClicked(subRecipeArr[position].idMeal)
        }
    }

    override fun getItemCount(): Int {
        return subRecipeArr.size
    }

    interface OnItemClickListener{
        fun onClicked(id: String)
    }
}