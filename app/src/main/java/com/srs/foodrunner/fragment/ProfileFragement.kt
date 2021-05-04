package com.srs.foodrunner.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.srs.foodrunner.R
import com.srs.foodrunner.util.Constants


class ProfileFragement : Fragment() {

    private lateinit var txt_address: TextInputEditText
    private lateinit var txt_name: TextInputEditText
    private lateinit var txt_mobile: TextInputEditText
    private lateinit var txt_email: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile_fragement, container, false)
        val sharedPreferences =
            activity?.getSharedPreferences(Constants.MYAPP_PREFERENCES, Context.MODE_PRIVATE)
        val userName = sharedPreferences?.getString(Constants.USER_NAME,"")
        val userEmail = sharedPreferences?.getString(Constants.USER_EMAIL,"")
        val userMobile= sharedPreferences?.getString(Constants.USER_MOBILE,"")
        val userAddress= sharedPreferences?.getString(Constants.USER_ADDRESS,"")

        txt_address=view.findViewById(R.id.txt_address)
        txt_name=view.findViewById(R.id.txt_name)
        txt_mobile=view.findViewById(R.id.txt_mobile)
        txt_email=view.findViewById(R.id.txt_email)
        txt_name.setText(userName)
        txt_email.setText(userEmail)
        txt_mobile.setText(userMobile)
        txt_address.setText(userAddress)


        return view
    }


}