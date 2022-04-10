package com.example.findhouse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.findhouse.R
import com.google.android.material.chip.Chip

class ImageSliderRecyclerViewAdapter(  private val itemList: ArrayList<String>, private val isLocale: Boolean) : RecyclerView.Adapter<ImageSliderRecyclerViewAdapter.ItemListViewHolder>() {

    class ItemListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val img = view.findViewById<ImageView>(R.id.iw_image_list_item)
        private val counterChip = view.findViewById<Chip>(R.id.chip_image_counter)

        fun bindItems(imageUri: String, position: Int, itemCount: Int){
            img.setImageURI(imageUri.toUri())
            counterChip.text = "${position+1}/ $itemCount"
        }

        fun getImagesFromUri(imageUri: String, position: Int, itemCount: Int){
            val uri = imageUri.toUri()
            val progressDrawable = CircularProgressDrawable(itemView.context)
            counterChip.text = "${position+1}/ $itemCount"
            Glide.with(itemView).load(uri).placeholder(progressDrawable).into(img).waitForLayout().clearOnDetach()
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
        if (isLocale){
            holder.bindItems(itemList[position], position, itemCount)
        }else{
            holder.getImagesFromUri(itemList[position], position, itemCount)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}


