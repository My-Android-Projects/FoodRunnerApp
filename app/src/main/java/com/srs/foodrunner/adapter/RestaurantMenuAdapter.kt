package com.srs.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.srs.foodrunner.R
import com.srs.foodrunner.activity.RestaruantDetailsActivity
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.model.MenuItem
import com.srs.foodrunner.util.DBCartAsyncTask

class RestaurantMenuAdapter (val context: Context, val itemList:ArrayList<MenuItem>) :
 RecyclerView.Adapter<RestaurantMenuAdapter.MenuViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_restaruant_menu_item_fragment, parent, false)
        return MenuViewHolder(view)
    }


    class MenuViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val txtCounter: TextView =view.findViewById(R.id.txtCounter)
        val txtFoodItem: TextView =view.findViewById(R.id.txtFoodItem)
        val txtFoodPrice: TextView =view.findViewById(R.id.txtFoodPrice)

        val btnAddOrRemove: Button =view.findViewById(R.id.btn_AddOrRemove)


    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu: MenuItem =itemList[position]
        holder.txtCounter.text=menu.counter.toString()
        holder.txtFoodItem.text=menu.name
        holder.txtFoodPrice.text=menu.cost
        holder.btnAddOrRemove.setOnClickListener{
            val cartEntity=CartEntity(menu.id, menu.name,menu.cost )
            val async= DBCartAsyncTask( context,cartEntity,1).execute()
            val isAdded=async.get()
            if(!isAdded)
             {

                 val async= DBCartAsyncTask( context,cartEntity,2).execute()
                 val result=async.get()
                 println("result: $result")
                 if(result)
                 {
                     holder.btnAddOrRemove.setText("Remove")
                     val noFavColor =ContextCompat.getColor(context, R.color.primary)
                     holder.btnAddOrRemove.setBackgroundColor(noFavColor)
                 }
                 else
                 {
                     Toast.makeText(context,"Error Occurred", Toast.LENGTH_LONG).show()
                 }
             }
             else
             {

                 val async= DBCartAsyncTask( context,cartEntity,3).execute()
                 val result=async.get()
                 println("result: $result")
                 if(result)
                 {
                     holder.btnAddOrRemove.setText("Add")
                     val noFavColor =ContextCompat.getColor(context, R.color.primaryLight)
                     holder.btnAddOrRemove.setBackgroundColor(noFavColor)
                 }
                 else
                 {
                     Toast.makeText(context,"Error Occurred", Toast.LENGTH_LONG).show()
                 }

             }
        }


    }



    override fun getItemCount(): Int {
        return itemList.size
    }


}