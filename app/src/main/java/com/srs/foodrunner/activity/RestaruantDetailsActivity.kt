package com.srs.foodrunner.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.srs.foodrunner.R
import com.srs.foodrunner.adapter.RestaurantMenuAdapter
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.database.FavouriteRestaruantEntity
import com.srs.foodrunner.database.FoodRunnerDatabase
import com.srs.foodrunner.databinding.ActivityRestaruantDetailsBinding
import com.srs.foodrunner.model.MenuItem
import com.srs.foodrunner.model.Restaruant
import com.srs.foodrunner.util.*
import java.util.*
import kotlin.collections.HashMap

class RestaruantDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRestaruantDetailsBinding
    lateinit var recyclerRestaruant: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout:RelativeLayout
    lateinit var recyclerAdapter: RestaurantMenuAdapter
    lateinit var  btn_AddOrRemoveFavourities: ImageView
    lateinit var currentRestaruant:Restaruant
    val menuInfoList = arrayListOf<MenuItem>()
    lateinit var toolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRestaruantDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentRestaruant = intent.getParcelableExtra(Constants.CURRENT_RESTARUANT)!!
        setupActionBar()
        recyclerRestaruant = findViewById(R.id.recyclerRestrauntMenu)
        progressBar=findViewById(R.id.progressBar)
        progressLayout=findViewById(R.id.progressLayout)
        btn_AddOrRemoveFavourities=findViewById(R.id.btn_AddOrRemoveFavourities)
        progressLayout.visibility= View.GONE
        layoutManager = LinearLayoutManager(this@RestaruantDetailsActivity)
        //delete cart details
        deleteAllCartDetails()

        displayMenuDetails()

        showFavoriteRestraunt()
        //add/remove favourite
        val favRestaruant=FavouriteRestaruantEntity(
            currentRestaruant.restaruantId,
            currentRestaruant.restaruantName,
            currentRestaruant.restaruantPricePerPerson,
            currentRestaruant.restaruantRating,
            currentRestaruant.restaruantImage
        )
        btn_AddOrRemoveFavourities.setOnClickListener{
            if (!DBRestaruantAsyncTask(applicationContext, favRestaruant, 1).execute().get()) //if not in fav
            {
                println("add to favourite: ${currentRestaruant.restaruantId}")
                val async=DBRestaruantAsyncTask(applicationContext, favRestaruant, 2).execute()
                val result=async.get()
                println("result: $result")
                if(result)
                {
                    btn_AddOrRemoveFavourities.setImageResource(R.drawable.ic_favourites)
                }
                else
                {
                    Toast.makeText(this@RestaruantDetailsActivity,"Error Occurred",Toast.LENGTH_LONG).show()

                }
            }
            else
            {
                val async=DBRestaruantAsyncTask(applicationContext, favRestaruant, 3).execute()
                val result=async.get()
                if(result)
                {
                    btn_AddOrRemoveFavourities.setImageResource(R.drawable.ic_not_favourites)

                }
                else
                {
                    Toast.makeText(this@RestaruantDetailsActivity,"Error Occurred",Toast.LENGTH_LONG).show()

                }
            }

        }

        //***********************************\
        binding.btnProceedToCart.setOnClickListener{
            val async= DBCartGetAllAsyncTask(applicationContext).execute()
            val tempDetails:List<CartEntity> = async.get()

            var cartDetails:ArrayList<CartEntity> = ArrayList()
            for (i in 0 until  tempDetails.size)
            {
                cartDetails.add(tempDetails.get(i))
            }

           if(cartDetails!=null)
           {
               val intent= Intent(this@RestaruantDetailsActivity,CartDetailsActivity::class.java)
               intent.putParcelableArrayListExtra(Constants.CART_DETAILS,cartDetails)
               intent.putExtra(Constants.CURRENT_RESTARUANT,currentRestaruant)
               startActivity(intent)

           }
        }
        }

    override fun onResume() {

        super.onResume()
    }
    private fun displayMenuDetails()
    {
        if (ConnectionManager().checkConnectivity(this@RestaruantDetailsActivity)) {
            val queue = Volley.newRequestQueue(this@RestaruantDetailsActivity)
            val url = "http://13.235.250.119/v2/restaurants/fetch_result/${currentRestaruant.restaruantId}"
            val jsonObjectRequest = object : JsonObjectRequest(
                Method.GET, url, null,
                Response.Listener {

                    println("Response is $it")
                    try {
                        progressLayout.visibility=View.GONE
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")

                        if (success) {
                            val resArray  = data.getJSONArray("data")
                            for (i in 0 until resArray.length()) {
                                val menuItemJsonObject = resArray .getJSONObject(i)
                                val menuObject = MenuItem(
                                    menuItemJsonObject.getString("id"),
                                    menuItemJsonObject.getString("name"),
                                    menuItemJsonObject.getString("cost_for_one"),
                                    menuItemJsonObject.getString("restaurant_id"),
                                    i+1
                                )
                                menuInfoList.add(menuObject)
                                recyclerAdapter =
                                    RestaurantMenuAdapter(this@RestaruantDetailsActivity, menuInfoList)
                                recyclerRestaruant.adapter = recyclerAdapter
                                layoutManager = LinearLayoutManager(this@RestaruantDetailsActivity)
                                recyclerRestaruant.layoutManager = layoutManager


                            }

                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@RestaruantDetailsActivity, "Error: Occurred", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    // println("Error is $it")
                    Toast.makeText(this@RestaruantDetailsActivity, "Volly Error", Toast.LENGTH_SHORT).show()
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

            val dialog = AlertDialog.Builder(this@RestaruantDetailsActivity)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->
                ActivityCompat.finishAffinity(this@RestaruantDetailsActivity)

            }
            dialog.create()
            dialog.show()


        }
    }
    private fun deleteAllCartDetails()
    {
        val async= DBCartAsyncTask(applicationContext, null, 4).execute()
        val result=async.get()
        if(result)
            println("result: $result")
        else
            Toast.makeText(this@RestaruantDetailsActivity,"Error Occurred",Toast.LENGTH_LONG).show()
    }
    private fun showFavoriteRestraunt()
    {
        val favRestaruant=FavouriteRestaruantEntity(
            currentRestaruant.restaruantId,
            currentRestaruant.restaruantName,
            currentRestaruant.restaruantPricePerPerson,
            currentRestaruant.restaruantRating,
            currentRestaruant.restaruantImage
        )

        val checkFav = DBRestaruantAsyncTask(applicationContext, favRestaruant, 1).execute()
        val isFav = checkFav.get()
        if(isFav)
            btn_AddOrRemoveFavourities.setImageResource(R.drawable.ic_favourites)
        else
            btn_AddOrRemoveFavourities.setImageResource(R.drawable.ic_not_favourites)

    }
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backarrow_white)
        }
        supportActionBar?.title=currentRestaruant.restaruantName

        binding.toolbar.setNavigationOnClickListener {
            deleteAllCartDetails()
            onBackPressed()
        }
    }
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        val id=item?.itemId
       /* if(id==R.id.action_sort){
            Collections.sort(restaruantInfoList,ratingcomparator)
            restaruantInfoList.reverse()
        }*/


        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }





}