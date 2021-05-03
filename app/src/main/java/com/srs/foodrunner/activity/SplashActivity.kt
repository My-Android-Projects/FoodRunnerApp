package com.srs.foodrunner.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import com.srs.foodrunner.R
import com.srs.foodrunner.util.Constants

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        @Suppress("DEPRECATION")
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R)
        {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler().postDelayed(
            {
                var isLoggedIn :Boolean=false
                val sharedPreferences =
                    getSharedPreferences(Constants.MYAPP_PREFERENCES, Context.MODE_PRIVATE)
                if(sharedPreferences.contains(Constants.IS_USER_LOGGED))
                     isLoggedIn= sharedPreferences.getBoolean(Constants.IS_USER_LOGGED,false)
                if(isLoggedIn)
                {
                    startActivity(Intent(this@SplashActivity, HomePageActivity::class.java))
                }
                else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))

                }
                finish()
            },
            1500
        )

    }
}