package com.srs.foodrunner.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.srs.foodrunner.databinding.ActivityLoginBinding
import com.srs.foodrunner.util.Constants

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lnkSignup.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.lnkForgotPassword.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener{
            val userMobile : String = binding.txtMobile.text.toString()
            val userPassword : String = binding.txtPassword.text.toString()

            val sharedPreferences =
                getSharedPreferences(
                    Constants.MYAPP_PREFERENCES,
                    Context.MODE_PRIVATE
                )

            // Create an instance of the editor which is help us to edit the SharedPreference.
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(Constants.USER_MOBILE,userMobile)
            editor.putString(Constants.USER_PASSWORD,userPassword)
            editor.putString(Constants.USER_EMAIL,"default@email.com")
            editor.putString(Constants.USER_ADDRESS,"defaultAddress")
            editor.putString(Constants.USER_NAME,"defaultName")
            editor.putBoolean(Constants.IS_USER_LOGGED,true)
            editor.apply()
            val intent = Intent(this@LoginActivity, HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}