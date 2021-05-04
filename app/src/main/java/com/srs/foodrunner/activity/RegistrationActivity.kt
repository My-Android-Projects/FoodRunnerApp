package com.srs.foodrunner.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.srs.foodrunner.R
import com.srs.foodrunner.databinding.ActivityLoginBinding
import com.srs.foodrunner.databinding.ActivityRegistrationBinding
import com.srs.foodrunner.util.ConnectionManager
import com.srs.foodrunner.util.Constants
import org.json.JSONObject

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


            if (ConnectionManager().checkConnectivity(this@RegistrationActivity))
            {
                val queue= Volley.newRequestQueue(this@RegistrationActivity)
            val url="http://13.235.250.119/v2/register/fetch_result"
            val jsonParams = JSONObject()
            jsonParams.put("name", userName)
            jsonParams.put("mobile_number", userMobile)
            jsonParams.put("password", userPassword)
            jsonParams.put("address", userAddress)
            jsonParams.put("email", userEmail)
            val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonParams,
                Response.Listener {
                    println("Response is $it")
                    try {

                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {
                            saveDetails( userName, userPassword,userMobile,userEmail,userAddress)
                            val intent =Intent(this@RegistrationActivity, HomePageActivity::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                             Toast.makeText(this@RegistrationActivity,"Error!!!",Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception)
                    {
                        Toast.makeText(this@RegistrationActivity, "Error: Occurred", Toast.LENGTH_SHORT).show()
                     }
                }, Response.ErrorListener {
                    // println("Error is $it")
                    Toast.makeText(this@RegistrationActivity, "Volly Error", Toast.LENGTH_SHORT).show()
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

            val dialog = AlertDialog.Builder(this@RegistrationActivity)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->
                ActivityCompat.finishAffinity(this@RegistrationActivity)

            }
            dialog.create()
            dialog.show()


        }

        }

    }

    fun saveDetails( userName:String, userPassword:String,userMobile:String,userEmail:String,userAddress:String)
    {


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