package com.srs.foodrunner.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srs.foodrunner.R
import com.srs.foodrunner.adapter.CartAdapter
import com.srs.foodrunner.adapter.FAQRecyclerAdapter
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.databinding.ActivityCartDetailsBinding
import com.srs.foodrunner.databinding.ActivityRestaruantDetailsBinding
import com.srs.foodrunner.model.Restaruant
import com.srs.foodrunner.util.Constants

class CartDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartDetailsBinding
    lateinit var recyclerCart: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var recyclerAdapter: CartAdapter
    lateinit var currentRestaruant: Restaruant
    lateinit var cartItems:ArrayList<CartEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        showDetails()

        binding.btnPlaceOrder.setOnClickListener{
            val intent = Intent(this@CartDetailsActivity, OrderConfirmationActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        showDetails()
        super.onResume()
    }
    fun showDetails()
    {
        recyclerCart = findViewById(R.id.recyclerCartMenu)
        layoutManager = LinearLayoutManager(this@CartDetailsActivity)
        currentRestaruant = intent.getParcelableExtra(Constants.CURRENT_RESTARUANT)!!
        cartItems = intent.getParcelableArrayListExtra(Constants.CART_DETAILS)!!
        binding.tvRestaurantName.text=currentRestaruant.restaruantName

        recyclerAdapter =CartAdapter(this@CartDetailsActivity, cartItems)
        recyclerCart.adapter = recyclerAdapter
        recyclerCart.layoutManager = layoutManager
        val totAmount=recyclerAdapter.setTotalPrice()
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