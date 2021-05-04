package com.srs.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.srs.foodrunner.R
import com.srs.foodrunner.activity.RestaruantDetailsActivity
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.util.DBCartAsyncTask

class CartAdapter(val context: Context, val itemList:ArrayList<CartEntity>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    var totPrice:Int=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_single_cart_item, parent, false)
        setTotalPrice()
        return CartViewHolder(view)
    }


    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFoodItem: TextView = view.findViewById(R.id.txtFoodItem)
        val txtFoodPrice: TextView = view.findViewById(R.id.txtFoodPrice)
        val imgRemove: ImageView = view.findViewById(R.id.imgRemove)


    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val txtFoodItem: CartEntity = itemList[position]
        holder.txtFoodItem.text = txtFoodItem.item_name
        holder.txtFoodPrice.text = "Rs. ${txtFoodItem.item_price}"
        holder.imgRemove.setOnClickListener {

            val async= DBCartAsyncTask(context, txtFoodItem, 3).execute()
            val result=async.get()
            if(result)
            {
                itemList.remove(txtFoodItem)
                this.notifyDataSetChanged()
                setTotalPrice()
            }

        }


    }


    override fun getItemCount(): Int {
        return itemList.size
    }
     fun setTotalPrice(): Int {
         totPrice=0
        for (i in 0 until itemList.size)
            totPrice=totPrice+ itemList.get(i).item_price.toInt()
        return totPrice
    }
}