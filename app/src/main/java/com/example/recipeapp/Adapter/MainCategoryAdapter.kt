package com.example.recipeapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.entity.CategoryItems
class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {
    var listener : OnItemClickListener? = null
    private var ArrMainCategory: ArrayList<CategoryItems> = ArrayList()
    class RecipeViewHolder(view : View): RecyclerView.ViewHolder(view){

        val dishtvname : TextView = view.findViewById(R.id.tv_main_dish_name)
        val disImage : ImageView = view.findViewById(R.id.img_dish)
    }

    fun setData(arrData: List<CategoryItems>) {
        ArrMainCategory = ArrayList(arrData)
       notifyDataSetChanged()
    }

    fun setClickListner(listner1 : OnItemClickListener){
        listener = listner1
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MainCategoryAdapter.RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main_categegory,parent,false))
    }

    override fun onBindViewHolder(
        holder: RecipeViewHolder,
        position: Int,
    ) {
        val recipe = ArrMainCategory[position]
        holder.dishtvname.text = recipe.strcategory

        Glide.with(holder.itemView.context).load(recipe.strcategorythumb).into(holder.disImage)
        holder.itemView.rootView.setOnClickListener {
            listener?.onClicked(ArrMainCategory[position].strcategory)
        }
    }

    override fun getItemCount(): Int {
       return ArrMainCategory.size
    }

    interface OnItemClickListener{
        fun onClicked(categoryName : String)
    }
}