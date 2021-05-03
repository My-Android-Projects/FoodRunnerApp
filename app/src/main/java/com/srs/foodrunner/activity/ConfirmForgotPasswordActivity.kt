package com.srs.foodrunner.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srs.foodrunner.R.*
import com.srs.foodrunner.databinding.ActivityConfirmForgotPasswordBinding
import com.srs.foodrunner.databinding.ActivityForgotPasswordBinding
import com.srs.foodrunner.util.Constants

class ConfirmForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Confirm Reset Password")
        binding= ActivityConfirmForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userEmail:String=intent.getStringExtra(Constants.USER_EMAIL)!!
        val userMobile:String=intent.getStringExtra(Constants.USER_MOBILE)!!
        binding.txtMobile.setText(userMobile)
        binding.txtEmail.setText(userEmail)


    }
}