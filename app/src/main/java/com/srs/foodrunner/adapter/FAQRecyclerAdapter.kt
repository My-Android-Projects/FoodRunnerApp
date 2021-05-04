package com.srs.foodrunner.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srs.foodrunner.R
import com.srs.foodrunner.activity.RestaruantDetailsActivity
import com.srs.foodrunner.model.FAQ

class FAQRecyclerAdapter(val context: Context, val itemList:ArrayList<FAQ>) : RecyclerView.Adapter<FAQRecyclerAdapter.FAQViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recycler_faq_single_item,parent,false)
        return FAQViewHolder(view)
    }
    class FAQViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val faqQuestion: TextView =view.findViewById(R.id.tv_faq_question)
        val faqAnswer: TextView =view.findViewById(R.id.tv_faq_answer)


    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        val faq: FAQ =itemList[position]
        holder.faqQuestion.text=faq.question
        holder.faqAnswer.text=faq.answer



    }



    override fun getItemCount(): Int {
        return itemList.size
    }
}