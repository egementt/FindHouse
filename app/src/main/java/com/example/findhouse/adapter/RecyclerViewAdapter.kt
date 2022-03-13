package com.example.findhouse.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.findhouse.R

class RecyclerViewAdapter(val itemList: ArrayList<Uri>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemListViewHolder>() {

    class ItemListViewHolder(private val imageView: View) : RecyclerView.ViewHolder(imageView) {

        fun bindItems(imageUri: Uri){
            val img = imageView.findViewById<ImageView>(R.id.imageListItem)
            img.setImageURI(imageUri)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemListViewHolder {
        return ItemListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindItems(itemList[position])

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}


