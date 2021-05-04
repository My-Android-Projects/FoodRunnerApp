package com.srs.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srs.foodrunner.R
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.model.FoodItem
import com.srs.foodrunner.util.DBCartAsyncTask

class FoodItemAdapter(val context: Context, val itemList:ArrayList<FoodItem>) :
    RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>() {
    var totPrice:Int=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_single_cart_item, parent, false)

        return FoodItemViewHolder(view)
    }


    class FoodItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFoodItem: TextView = view.findViewById(R.id.txtFoodItem)
        val txtFoodPrice: TextView = view.findViewById(R.id.txtFoodPrice)

    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val foodItem: FoodItem = itemList[position]
        holder.txtFoodItem.text = foodItem.name
        holder.txtFoodPrice.text = "Rs. ${foodItem.cost}"



    }


    override fun getItemCount(): Int {
        return itemList.size
    }


}