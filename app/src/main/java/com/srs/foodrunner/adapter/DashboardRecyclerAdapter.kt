package com.srs.foodrunner.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
//import com.squareup.picasso.Picasso
import com.srs.foodrunner.R
import com.srs.foodrunner.activity.RestaruantDetailsActivity
import com.srs.foodrunner.database.FavouriteRestaruantEntity

import com.srs.foodrunner.model.Restaruant
import com.srs.foodrunner.util.Constants
import com.srs.foodrunner.util.DBRestaruantAsyncTask

class DashboardRecyclerAdapter(val context: Context, val itemList:ArrayList<Restaruant>) :RecyclerView.Adapter<DashboardRecyclerAdapter.DashBoardViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row_fragment,parent,false)
        return DashBoardViewHolder(view)
    }
    class DashBoardViewHolder(view:View):RecyclerView.ViewHolder(view)
    {
        val txtRestaruantName:TextView=view.findViewById(R.id.txtRestarauntName)
        val txtRestaruantPrice:TextView=view.findViewById(R.id.txtRestarauntPrice)
       val txtRestaruantRating:TextView=view.findViewById(R.id.txtRestarauntRating)
        val imgRestaruantImage: ImageView =view.findViewById(R.id.imgRestaruant)
        val llContent:LinearLayout=view.findViewById(R.id.llContent)
    val imgFavourites:ImageView=view.findViewById(R.id.imgFavourites)

    }

    override fun onBindViewHolder(holder: DashBoardViewHolder, position: Int) {
        val restaruant: Restaruant =itemList[position]
        holder.txtRestaruantName.text=restaruant.restaruantName
        holder.txtRestaruantPrice.text=restaruant.restaruantPricePerPerson
        holder.txtRestaruantRating.text=restaruant.restaruantRating
        Picasso.get().load(restaruant.restaruantImage).error(R.drawable.ic_default_restaruant).into(holder.imgRestaruantImage)


        val favRestaruant= FavouriteRestaruantEntity(
            restaruant.restaruantId,
            restaruant.restaruantName,
            restaruant.restaruantPricePerPerson,
            restaruant.restaruantRating,
            restaruant.restaruantImage
        )
        val checkFav = DBRestaruantAsyncTask(context , favRestaruant, 1).execute()
        val isFav = checkFav.get()
        if(isFav)
            holder.imgFavourites.setImageResource(R.drawable.ic_favourites)
        else
            holder.imgFavourites.setImageResource(R.drawable.ic_not_favourites)


        holder.llContent.setOnClickListener(){
            val intent= Intent(context,RestaruantDetailsActivity::class.java)
            intent.putExtra(Constants.CURRENT_RESTARUANT,restaruant)
            context.startActivity(intent)

        }

    }



    override fun getItemCount(): Int {
        return itemList.size
    }
}