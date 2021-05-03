package com.srs.foodrunner.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srs.foodrunner.R
import com.srs.foodrunner.databinding.ActivityHomePageBinding
import com.srs.foodrunner.databinding.ActivityRegistrationBinding
import com.srs.foodrunner.util.Constants

class HomePageActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences =
            getSharedPreferences(Constants.MYAPP_PREFERENCES, Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString(Constants.USER_NAME,"")
        val userEmail = sharedPreferences.getString(Constants.USER_EMAIL,"")
        val userPassword= sharedPreferences.getString(Constants.USER_PASSWORD,"")
        val userMobile= sharedPreferences.getString(Constants.USER_MOBILE,"")
        val userAddress= sharedPreferences.getString(Constants.USER_ADDRESS,"")

        binding.txtName.setText( userName)

        binding.txtEmail.setText( userEmail)
        binding.txtMobile.setText( userMobile)
        binding.txtPassword.setText( userPassword)
        binding.txtDeliveryAddress.setText(userAddress)
        binding.btnLogOff.setOnClickListener{
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.remove(Constants.USER_MOBILE)
            editor.remove(Constants.USER_PASSWORD)
            editor.remove(Constants.USER_EMAIL)
            editor.remove(Constants.USER_ADDRESS)
            editor.remove(Constants.USER_NAME)
            editor.remove(Constants.IS_USER_LOGGED)

            editor.apply()
            val intent = Intent(this@HomePageActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
    fun setUpToolBar()
    {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title="Dashboard"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}