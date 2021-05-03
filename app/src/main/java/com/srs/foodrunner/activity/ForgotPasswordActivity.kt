package com.srs.foodrunner.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.srs.foodrunner.R
import com.srs.foodrunner.databinding.ActivityForgotPasswordBinding
import com.srs.foodrunner.databinding.ActivityHomePageBinding
import com.srs.foodrunner.util.Constants

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener {
            val intent = Intent(this@ForgotPasswordActivity, ConfirmForgotPasswordActivity::class.java)
            intent.putExtra(Constants.USER_EMAIL, binding.txtEmail.text.toString())
            intent.putExtra(Constants.USER_MOBILE, binding.txtMobile.text.toString())
            startActivity(intent)
        }
        }
}