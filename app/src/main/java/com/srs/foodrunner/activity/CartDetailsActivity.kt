package com.srs.foodrunner.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.srs.foodrunner.R
import com.srs.foodrunner.adapter.CartAdapter
import com.srs.foodrunner.adapter.FAQRecyclerAdapter
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.databinding.ActivityCartDetailsBinding
import com.srs.foodrunner.databinding.ActivityRestaruantDetailsBinding
import com.srs.foodrunner.model.Restaruant
import com.srs.foodrunner.util.ConnectionManager
import com.srs.foodrunner.util.Constants
import org.json.JSONArray
import org.json.JSONObject

class CartDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartDetailsBinding
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: CartAdapter
    lateinit var currentRestaruant: Restaruant
    lateinit var cartItems:ArrayList<CartEntity>
    lateinit var  userid:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        currentRestaruant = intent.getParcelableExtra(Constants.CURRENT_RESTARUANT)!!
        cartItems = intent.getParcelableArrayListExtra(Constants.CART_DETAILS)!!
        val sharedPreferences =
            getSharedPreferences(Constants.MYAPP_PREFERENCES, Context.MODE_PRIVATE)
         userid = sharedPreferences.getString(Constants.USER_ID,"")!!

        showDetails()

        binding.btnPlaceOrder.setOnClickListener{

            if (ConnectionManager().checkConnectivity(this@CartDetailsActivity))
            {
                val queue= Volley.newRequestQueue(this@CartDetailsActivity)
                val url="http://13.235.250.119/v2/place_order/fetch_result"
                val cartObject = JSONObject()
                cartObject.put("user_id", userid)
                cartObject.put("restaurant_id", currentRestaruant.restaruantId)
                cartObject.put("total_cost", recyclerAdapter.getTotalPrice().toString())
                val foodArrayObject= JSONArray()
                for (i in 0 until cartItems.size)
                {
                    val foodObject=JSONObject()
                    foodObject.put("food_item_id",cartItems.get(i).item_id)
                    foodArrayObject.put(i,foodObject)
                }
                cartObject.put("food",foodArrayObject)

                val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, cartObject,
                    Response.Listener {
                        println("Response is $it")
                        try {

                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if (success) {

                                val intent = Intent(this@CartDetailsActivity, OrderConfirmationActivity::class.java)
                                startActivity(intent)
                            }
                            else
                            {
                                Toast.makeText(this@CartDetailsActivity,"Error!!!", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception)
                        {
                            Toast.makeText(this@CartDetailsActivity, "Error: Occurred", Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener {
                        // println("Error is $it")
                        Toast.makeText(this@CartDetailsActivity, "Volly Error", Toast.LENGTH_SHORT).show()
                    })
                {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers=HashMap<String,String>()
                        headers["Content-type"]="application/json"
                        headers["token"]="9bf534118365f1"
                        return headers
                    }
                }
                queue.add(jsonObjectRequest)
            } else {

                val dialog = AlertDialog.Builder(this@CartDetailsActivity)
                dialog.setTitle("Failure")
                dialog.setMessage("Internet Connection Not Found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    finish()
                }
                dialog.setNegativeButton("Exit") { text, listner ->
                    ActivityCompat.finishAffinity(this@CartDetailsActivity)

                }
                dialog.create()
                dialog.show()


            }

        }
    }
    override fun onResume() {
        showDetails()
        super.onResume()
    }
    fun showDetails()
    {
        layoutManager = LinearLayoutManager(this@CartDetailsActivity)
        binding.tvRestaurantName.text=currentRestaruant.restaruantName
        recyclerAdapter =CartAdapter(this@CartDetailsActivity, cartItems)
        binding.recyclerCartMenu.adapter = recyclerAdapter
        binding.recyclerCartMenu.layoutManager = layoutManager
        val totAmount=recyclerAdapter.getTotalPrice()
        binding.btnPlaceOrder.setText("Place Order(Total: Rs.${totAmount})")
    }
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backarrow_white)
        }
        supportActionBar?.title = "Cart Details"
        binding.toolbar.setNavigationOnClickListener {


            onBackPressed()
        }
    }
}