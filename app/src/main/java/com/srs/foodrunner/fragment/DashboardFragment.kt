package com.srs.foodrunner.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.srs.foodrunner.R
import com.srs.foodrunner.adapter.DashboardRecyclerAdapter
import com.srs.foodrunner.model.Restaruant
import com.srs.foodrunner.util.ConnectionManager
import java.util.*
import kotlin.collections.HashMap

class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout

    val restaruantInfoList = arrayListOf<Restaruant>()
    var ratingcomparator= Comparator<Restaruant>{restaruant1,restaruant2->
        if(restaruant1.restaruantRating.compareTo(restaruant2.restaruantRating,true)==0)
        {
            restaruant1.restaruantName.compareTo(restaruant2.restaruantName, true)
        }
        else {
            restaruant1.restaruantRating.compareTo(restaruant2.restaruantRating, true)
        }
    }

    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        progressBar=view.findViewById(R.id.progressBar)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressLayout.visibility=View.VISIBLE
        layoutManager = LinearLayoutManager(activity)
        showDetails()
        return view
    }
    fun showDetails()
    {
        if (ConnectionManager().checkConnectivity(activity as Context)) {
            val queue = Volley.newRequestQueue(activity as Context)
            val url = "http://13.235.250.119/v2/restaurants/fetch_result"
            val jsonObjectRequest = object : JsonObjectRequest(Method.GET, url, null,
                Response.Listener {
                    println("Response is $it")
                    try {
                        progressLayout.visibility=View.GONE
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")

                        if (success) {
                            val resArray  = data.getJSONArray("data")
                            for (i in 0 until resArray.length()) {
                                val restaruantJsonObject = resArray .getJSONObject(i)
                                val restaurantObject = Restaruant(
                                    restaruantJsonObject.getString("id"),
                                    restaruantJsonObject.getString("name"),
                                    restaruantJsonObject.getString("cost_for_one"),
                                    restaruantJsonObject.getString("rating"),
                                    restaruantJsonObject.getString("image_url")
                                )
                                restaruantInfoList.add(restaurantObject)
                                recyclerAdapter =
                                    DashboardRecyclerAdapter(activity as Context, restaruantInfoList)
                                recyclerDashboard.adapter = recyclerAdapter
                                recyclerDashboard.layoutManager = layoutManager
                                recyclerDashboard.addItemDecoration(
                                    DividerItemDecoration(
                                        recyclerDashboard.context,
                                        (layoutManager as LinearLayoutManager).orientation
                                    )
                                )

                            }

                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: Occurred", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener {
                    // println("Error is $it")
                    Toast.makeText(context, "Volly Error", Toast.LENGTH_SHORT).show()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String,String>()
                    headers["Content-type"]="application/json"
                    headers["token"]="9bf534118365f1"
                    return headers
                }
            }
            queue.add(jsonObjectRequest)
        } else {

            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listner ->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()


        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_drawer,menu)
    }
    override fun onResume() {
        showDetails()
        super.onResume()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        if(id==R.id.action_sort){
            Collections.sort(restaruantInfoList,ratingcomparator)
            restaruantInfoList.reverse()
        }


        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }



}