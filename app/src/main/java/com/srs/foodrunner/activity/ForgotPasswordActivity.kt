package com.srs.foodrunner.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.srs.foodrunner.R
import com.srs.foodrunner.databinding.ActivityForgotPasswordBinding
import com.srs.foodrunner.databinding.ActivityHomePageBinding
import com.srs.foodrunner.util.ConnectionManager
import com.srs.foodrunner.util.Constants
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener {

            if (ConnectionManager().checkConnectivity(this@ForgotPasswordActivity))
            {
                val queue= Volley.newRequestQueue(this@ForgotPasswordActivity)
                val url="http://13.235.250.119/v2/forgot_password/fetch_result"
                val jsonParams = JSONObject()
                jsonParams.put("mobile_number", binding.txtMobile.text.toString())
                jsonParams.put("email", binding.txtEmail.text.toString())
                val jsonObjectRequest = object : JsonObjectRequest(Method.POST, url, jsonParams,
                    Response.Listener {
                        println("Response is $it")
                        try {

                            val data = it.getJSONObject("data")
                            val success = data.getBoolean("success")
                            if (success) {


                                val intent = Intent(this@ForgotPasswordActivity, ConfirmForgotPasswordActivity::class.java)
                                intent.putExtra(Constants.USER_EMAIL, binding.txtEmail.text.toString())
                                intent.putExtra(Constants.USER_MOBILE, binding.txtMobile.text.toString())
                                startActivity(intent)
                            }
                            else
                            {
                                Toast.makeText(this@ForgotPasswordActivity,"Error!!!", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception)
                        {
                            Toast.makeText(this@ForgotPasswordActivity, "Error: Occurred", Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener {
                        // println("Error is $it")
                        Toast.makeText(this@ForgotPasswordActivity, "Volly Error", Toast.LENGTH_SHORT).show()
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

                val dialog = AlertDialog.Builder(this@ForgotPasswordActivity)
                dialog.setTitle("Failure")
                dialog.setMessage("Internet Connection Not Found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsIntent)
                    finish()
                }
                dialog.setNegativeButton("Exit") { text, listner ->
                    ActivityCompat.finishAffinity(this@ForgotPasswordActivity)

                }
                dialog.create()
                dialog.show()


            }

        }


        }
}