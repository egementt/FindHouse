package com.example.findhouse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.findhouse.R
import com.example.findhouse.model.Current
import com.example.findhouse.model.University
import com.example.findhouse.util.AdapterItemClickListener

class UniversitiesRecyclerViewAdapter() :
    RecyclerView.Adapter<UniversitiesRecyclerViewAdapter.UniversityViewHolder>()
{
    private lateinit var mlistener: onItemClickListener
    private val universityList = Current.universities

   interface onItemClickListener{
       fun onItemClick(position: Int)
   }

    fun setOnItemClickListener(listener: onItemClickListener){
        mlistener = listener

    }

    class UniversityViewHolder( val view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bindItems(university: University){
            view.findViewById<ImageView>(R.id.iw_uni).setImageResource(university.image)
            view.findViewById<TextView>(R.id.tw_universityName).text = university.name
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_university,
            parent,
            false
        )

        return UniversityViewHolder(view, mlistener)
    }

    override fun onBindViewHolder(holder: UniversityViewHolder, position: Int) {
      holder.bindItems(universityList[position])

    }

    override fun getItemCount(): Int {
        return universityList.size
    }

}