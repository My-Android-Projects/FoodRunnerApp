package com.srs.foodrunner.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.srs.foodrunner.R
import com.srs.foodrunner.adapter.DashboardRecyclerAdapter
import com.srs.foodrunner.adapter.PreviousOrderAdapter
import com.srs.foodrunner.model.FoodItem
import com.srs.foodrunner.model.Order
import com.srs.foodrunner.model.Restaruant
import com.srs.foodrunner.util.ConnectionManager
import com.srs.foodrunner.util.Constants


class OrderHistoryFragment : Fragment() {
    lateinit var recyclerPreviousOrder: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    private var orderInfoList=ArrayList<Order>()
    lateinit var recyclerAdapter: PreviousOrderAdapter
    private lateinit var userid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)
        val sharedPreferences =
            activity?.getSharedPreferences(Constants.MYAPP_PREFERENCES, Context.MODE_PRIVATE)
        userid = sharedPreferences?.getString(Constants.USER_ID, "")!!
        recyclerPreviousOrder = view.findViewById(R.id.recyclerOrderHistory)

        layoutManager = LinearLayoutManager(activity)
        if (ConnectionManager().checkConnectivity(activity as Context)){
            val queue = Volley.newRequestQueue(activity as Context)
            val url = "http://13.235.250.119/v2/orders/fetch_result/${userid}"
                val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null,
                    Response.Listener {
                        println("Response is $it")
                        try {

                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")

                            if (success) {
                                val resArray  = data.getJSONArray("data")
                                for (i in 0 until resArray.length()) {
                                    val jsonobject = resArray .getJSONObject(i)
                                    val foodjasonarray=jsonobject.getJSONArray("food_items")
                                    val footList=ArrayList<FoodItem>()

                                    for(j in 0  until foodjasonarray.length() )
                                    {
                                        val fobject=foodjasonarray.getJSONObject(j)
                                         val ft=FoodItem(
                                            fobject.getString("food_item_id"),
                                            fobject.getString("name"),
                                            fobject.getString("cost"),
                                        )
                                        footList.add(ft)
                                    }
                                    val orderObject = Order(
                                        jsonobject.getString("order_id"),
                                        jsonobject.getString("restaurant_name"),
                                        jsonobject.getString("total_cost"),
                                        jsonobject.getString("order_placed_at"),
                                        footList
                                    )
                                    orderInfoList.add(orderObject)
                                    recyclerAdapter =
                                        PreviousOrderAdapter(activity as Context, orderInfoList)
                                    recyclerPreviousOrder.adapter = recyclerAdapter
                                    recyclerPreviousOrder.layoutManager = layoutManager
                                    recyclerPreviousOrder.addItemDecoration(
                                        DividerItemDecoration(
                                            recyclerPreviousOrder.context,
                                            (layoutManager as LinearLayoutManager).orientation
                                        )
                                    )

                                }

                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: Occurred", Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener {
                        // println("Error is $it")
                        Toast.makeText(context, "Volly Error", Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers=HashMap<String,String>()
                        headers["Content-type"]="application/json"
                        headers["token"]="9bf534118365f1"
                        return headers
                    }
                }
                queue.add(jsonObjectRequest)
            } else {

            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()
        }
        return view


    }

}