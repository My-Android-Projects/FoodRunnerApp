package com.srs.foodrunner.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.srs.foodrunner.R
import com.srs.foodrunner.databinding.ActivityHomePageBinding
import com.srs.foodrunner.databinding.ActivityRegistrationBinding
import com.srs.foodrunner.fragment.*
import com.srs.foodrunner.util.Constants

class HomePageActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    private lateinit var binding:ActivityHomePageBinding
    var previousMenuItem:MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setTitle("Dashboard")
        val sharedPreferences =
            getSharedPreferences(Constants.MYAPP_PREFERENCES, Context.MODE_PRIVATE)
        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frameLayout)
        navigationView=findViewById(R.id.navigationView)
        setUpToolBar()
        openDashboard()
        val actionBarDrawerToggle= ActionBarDrawerToggle(this@HomePageActivity,binding.drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener {

            if(previousMenuItem!=null)
            {
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId)
            {
                R.id.mnu_home ->{
                    openDashboard()
                    binding.drawerLayout.closeDrawers()

                }
                R.id.mnu_favourites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FavoriteRestaurantsFragment())

                        .commit()
                    supportActionBar?.title="Favourite Restaurants"
                    binding.drawerLayout.closeDrawers()
                }
                R.id.mnu_profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragement())

                        .commit()
                    supportActionBar?.title="My Profile"
                    binding.drawerLayout.closeDrawers()
                }
                R.id.mnu_orderhistory ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, OrderHistoryFragment())
                        .commit()
                    supportActionBar?.title="My Previous Orders"
                    binding.drawerLayout.closeDrawers()
                }
                R.id.mnu_faqs ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FAQFragment())

                        .commit()
                    supportActionBar?.title="Frequently Asked Questions"
                    binding.drawerLayout.closeDrawers()
                }
                R.id.mnu_logout->{
                    binding.drawerLayout.closeDrawers()
                    showAlertDialogToLogOff()
                }                }
            return@setNavigationItemSelectedListener true
        }
    }
    private fun showAlertDialogToLogOff() {

        val builder = AlertDialog.Builder(this@HomePageActivity)
        //set title for alert dialog
        builder.setTitle("Confirmation")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to log out?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            val sharedPreferences =
                getSharedPreferences(Constants.MYAPP_PREFERENCES, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.remove(Constants.USER_MOBILE)
            editor.remove(Constants.USER_PASSWORD)
            editor.remove(Constants.USER_EMAIL)
            editor.remove(Constants.USER_ADDRESS)
            editor.remove(Constants.USER_NAME)
            editor.remove(Constants.IS_USER_LOGGED)

            editor.apply()

            ActivityCompat.finishAffinity(this)

            dialogInterface.dismiss()
            val intent = Intent(this@HomePageActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, _ ->

            dialogInterface.dismiss()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id=item.itemId
    if(id==android.R.id.home)
    {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }
    return super.onOptionsItemSelected(item)
}
    fun setUpToolBar()
    {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title="Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
fun openDashboard()
{
    val fragment= DashboardFragment()
    val transaction=supportFragmentManager.beginTransaction()

    transaction.replace(R.id.frameLayout,fragment)
    transaction.commit()
    supportActionBar?.title="Dashboard"
    binding.navigationView.setCheckedItem(R.id.mnu_home)

}
    override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frameLayout)
        when(frag)
        {
            !is DashboardFragment ->openDashboard()
            else ->super.onBackPressed()
        }
    }

}