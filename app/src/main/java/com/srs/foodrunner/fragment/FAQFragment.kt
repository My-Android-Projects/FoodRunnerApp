package com.srs.foodrunner.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srs.foodrunner.R
import com.srs.foodrunner.adapter.DashboardRecyclerAdapter
import com.srs.foodrunner.adapter.FAQRecyclerAdapter
import com.srs.foodrunner.model.FAQ
import com.srs.foodrunner.model.Restaruant


class FAQFragment : Fragment() {
    lateinit var recyclerFAQ: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var recyclerAdapter: FAQRecyclerAdapter
    val faqInfoList = arrayListOf<FAQ>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_f_a_q, container, false)
        recyclerFAQ = view.findViewById(R.id.recyclerDashboard)
        progressBar=view.findViewById(R.id.progressBar)
        progressLayout=view.findViewById(R.id.progressLayout)
        progressLayout.visibility=View.GONE
        layoutManager = LinearLayoutManager(activity)
        val faqQuestions  =  resources.obtainTypedArray(R.array.questions)
        val faqAnswers =  resources.obtainTypedArray(R.array.answers)
        activity?.setTitle("Frequently Asked Questions")
        for (i in 0 until faqQuestions.length()) {
            val id=(i+1).toString()
            val faqObject = FAQ(
                id,
                "Q.${id}. ${faqQuestions.getString(i)}",
                "A.${id}. ${faqAnswers.getString(i)}",

            )
            faqInfoList.add(faqObject)
            recyclerAdapter =
                FAQRecyclerAdapter(activity as Context, faqInfoList)
            recyclerFAQ.adapter = recyclerAdapter
            recyclerFAQ.layoutManager = layoutManager
            recyclerFAQ.addItemDecoration(
                DividerItemDecoration(
                    recyclerFAQ.context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )

        }
        return view
    }

}