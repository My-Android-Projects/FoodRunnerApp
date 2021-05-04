package com.srs.foodrunner.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.srs.foodrunner.databinding.ActivityLoginBinding
import com.srs.foodrunner.util.ConnectionManager
import com.srs.foodrunner.util.Constants
import org.json.JSONObject

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


            if (ConnectionManager().checkConnectivity(this@LoginActivity))
            {
                val queue= Volley.newRequestQueue(this@LoginActivity)
                val url="http://13.235.250.119/v2/login/fetch_result"
                val jsonParams = JSONObject()
                jsonParams.put("mobile_number", userMobile)
                jsonParams.put("password", userPassword)

                val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonParams,
                    Response.Listener {
                        println("Response is $it")
                        try {

                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if (success) {
                                val userProfile =data.getJSONObject("data")
                                val userName=userProfile.getString("name")
                                val userEmail=userProfile.getString("email")
                                val userAddress=userProfile.getString("address")
                                saveDetails( userName, userPassword,userMobile,userEmail,userAddress)
                                val intent =Intent(this@LoginActivity, HomePageActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else
                            {
                                Toast.makeText(this@LoginActivity,"Error!!!", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception)
                        {
                            Toast.makeText(this@LoginActivity, "Error: Occurred", Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener {
                        // println("Error is $it")
                        Toast.makeText(this@LoginActivity, "Volly Error", Toast.LENGTH_SHORT).show()
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

                val dialog = AlertDialog.Builder(this@LoginActivity)
                dialog.setTitle("Failure")
                dialog.setMessage("Internet Connection Not Found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    finish()
                }
                dialog.setNegativeButton("Exit") { text, listner ->
                    ActivityCompat.finishAffinity(this@LoginActivity)

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
}