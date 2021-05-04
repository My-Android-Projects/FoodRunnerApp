package com.srs.foodrunner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.srs.foodrunner.R
import com.srs.foodrunner.activity.RestaruantDetailsActivity
import com.srs.foodrunner.database.FavouriteRestaruantEntity
import com.srs.foodrunner.util.Constants
import com.srs.foodrunner.util.DBRestaruantAsyncTask

class FavouriteRestaurantAdapter(val context: Context, val itemList:ArrayList<FavouriteRestaruantEntity>) :
    RecyclerView.Adapter<FavouriteRestaurantAdapter.FavouriteRestaurantViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteRestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_dashboard_single_row_fragment, parent, false)
        return FavouriteRestaurantViewHolder(view)
    }

    class FavouriteRestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtRestaruantName: TextView = view.findViewById(R.id.txtRestarauntName)
        val txtRestaruantPrice: TextView = view.findViewById(R.id.txtRestarauntPrice)
        val txtRestaruantRating: TextView = view.findViewById(R.id.txtRestarauntRating)
        val imgRestaruantImage: ImageView = view.findViewById(R.id.imgRestaruant)

        val llContent: LinearLayout = view.findViewById(R.id.llContent)
        val imgFavourites: ImageView = view.findViewById(R.id.imgFavourites)

    }

    override fun onBindViewHolder(holder: FavouriteRestaurantViewHolder, position: Int) {
        val restaruant: FavouriteRestaruantEntity = itemList[position]
        holder.txtRestaruantName.text = restaruant.restaruantName
        holder.txtRestaruantPrice.text = restaruant.restaruantPricePerPerson
        holder.txtRestaruantRating.text = restaruant.restaruantRating
        Picasso.get().load(restaruant.restaruantImage).error(R.drawable.ic_default_restaruant)
            .into(holder.imgRestaruantImage)
        holder.imgFavourites.setImageResource(R.drawable.ic_favourites)


        holder.imgFavourites.setOnClickListener() {
            val async =
                DBRestaruantAsyncTask(context, restaruant, 3).execute()
            val isRemoved = async.get()
            if (isRemoved) {
                itemList.remove(restaruant)
                this.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
            }

        }

    }


    override fun getItemCount(): Int {
        return itemList.size
    }
}