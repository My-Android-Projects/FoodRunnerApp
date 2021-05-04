package com.srs.foodrunner.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srs.foodrunner.R
import com.srs.foodrunner.adapter.DashboardRecyclerAdapter
import com.srs.foodrunner.adapter.FavouriteRestaurantAdapter
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.database.FavouriteRestaruantEntity
import com.srs.foodrunner.model.Restaruant
import com.srs.foodrunner.util.DBCartGetAllAsyncTask
import com.srs.foodrunner.util.DBRestaurantGetAllAsyncTask
import java.util.ArrayList


class FavoriteRestaurantsFragment : Fragment() {

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: FavouriteRestaurantAdapter
    val restaruantInfoList = arrayListOf<FavouriteRestaruantEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_favorite_restaraunts, container, false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        layoutManager = LinearLayoutManager(activity)
        showDetails()
        return view
    }

    fun showDetails()
    {
        val async= DBRestaurantGetAllAsyncTask(context!!).execute()
        val tempDetails:List<FavouriteRestaruantEntity> = async.get()

        var favouriteRestaruantList: ArrayList<FavouriteRestaruantEntity> = ArrayList()
        for (i in 0 until  tempDetails.size)
        {
            favouriteRestaruantList.add(tempDetails.get(i))
        }
        for (i in 0 until  favouriteRestaruantList.size) {
            restaruantInfoList.add(favouriteRestaruantList.get(i))
        }
        recyclerAdapter =
            FavouriteRestaurantAdapter(activity as Context, restaruantInfoList)
        recyclerDashboard.adapter = recyclerAdapter
        recyclerDashboard.layoutManager = layoutManager
    }


}