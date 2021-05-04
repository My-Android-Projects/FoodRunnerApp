package com.srs.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srs.foodrunner.R
import com.srs.foodrunner.databinding.ActivityOrderConfirmationBinding

class OrderConfirmationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOrderConfirmationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOrderconfirmationOK.setOnClickListener{
            val intent = Intent(this@OrderConfirmationActivity, HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}