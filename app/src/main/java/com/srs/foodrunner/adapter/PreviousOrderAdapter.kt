package com.srs.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srs.foodrunner.R
import com.srs.foodrunner.model.Order
import java.text.SimpleDateFormat
import java.util.*

class PreviousOrderAdapter(val context: Context, val itemList: ArrayList<Order>) :
    RecyclerView.Adapter<PreviousOrderAdapter.OrderViewHolder>() {
    var totPrice:Int=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recycler_previousorder_single_row, parent, false)

        return OrderViewHolder(view)
    }


    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tv_restaurantName: TextView = view.findViewById(R.id.tv_restaurantName)
        val tv_orderDate: TextView = view.findViewById(R.id.tv_orderDate)
        val rv_fooditems:RecyclerView=view.findViewById(R.id.recyclerFoodItems)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order : Order = itemList[position]
        holder.tv_restaurantName.text=order.restaurant_name
        val parser = SimpleDateFormat("DD-MM-yyyy HH:MM:SS")
        val formatter = SimpleDateFormat("DD/MM/yyyy")
        val sdate: String = formatter.format(parser.parse(order.order_placed_at))
        //holder.tv_orderDate.text=order.order_placed_at
        holder.tv_orderDate.text=sdate
        val recyclerAdapter: FoodItemAdapter = FoodItemAdapter(context as Context, order.food_items)
        holder.rv_fooditems.adapter = recyclerAdapter
        val layoutManager: RecyclerView.LayoutManager= LinearLayoutManager(context as Context)
        holder.rv_fooditems.layoutManager = layoutManager


    }


    override fun getItemCount(): Int {
        return itemList.size
    }

}