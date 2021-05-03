package com.srs.foodrunner.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srs.foodrunner.R
import com.srs.foodrunner.databinding.ActivityLoginBinding
import com.srs.foodrunner.databinding.ActivityRegistrationBinding
import com.srs.foodrunner.util.Constants

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        binding.btnRegister.setOnClickListener{
            val userMobile : String = binding.txtMobile.text.toString()
            val userPassword : String = binding.txtPassword.text.toString()
            val userName:String=binding.txtName.text.toString()
            val userEmail:String=binding.txtEmail.text.toString()
            val userAddress:String=binding.txtDeliveryAddress.text.toString()

            val sharedPreferences =
                getSharedPreferences(
                    Constants.MYAPP_PREFERENCES,
                    Context.MODE_PRIVATE
                )

            // Create an instance of the editor which is help us to edit the SharedPreference.
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(Constants.USER_MOBILE,userMobile)
            editor.putString(Constants.USER_PASSWORD,userPassword)
            editor.putString(Constants.USER_EMAIL,userEmail)
            editor.putString(Constants.USER_ADDRESS,userAddress)
            editor.putString(Constants.USER_NAME,userName)
            editor.putBoolean(Constants.IS_USER_LOGGED,true)
            editor.apply()
            val intent = Intent(this@RegistrationActivity, HomePageActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarRegistrationActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_backarrow_white)
        }

        binding.toolbarRegistrationActivity.setNavigationOnClickListener { onBackPressed() }
    }

}