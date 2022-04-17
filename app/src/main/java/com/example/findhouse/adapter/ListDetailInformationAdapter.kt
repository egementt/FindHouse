package com.example.findhouse.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findhouse.R
import com.example.findhouse.model.DetailListItemModel
import com.example.findhouse.model.House
import com.example.findhouse.model.HouseListing

//todo create one model that holds key,value for houselisting & listing models.

class ListDetailInformationAdapter(houseListing: HouseListing) :
    RecyclerView.Adapter<ListDetailInformationAdapter.ListDetailViewHolder>() {

    private var list: List<DetailListItemModel> = houseListing.toDetailListItemModels()


    class ListDetailViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItems(detailListItemModel: DetailListItemModel) {
            view.findViewById<TextView>(R.id.tw_detail_head).text = detailListItemModel.key.plus(":")

            view.findViewById<TextView>(R.id.tw_detail_value).text =
                detailListItemModel.value.toString()
        }
    }



override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_detail, parent, false)

    return ListDetailViewHolder(view)
}

override fun onBindViewHolder(holder:ListDetailViewHolder, position: Int) {
    holder.bindItems(list[position])
}

override fun getItemCount(): Int {
    return list.size
}


}
