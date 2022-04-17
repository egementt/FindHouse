package com.example.findhouse.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.findhouse.adapter.FilteredListingsRecyclerViewAdapter
import com.example.findhouse.model.Current
import com.example.findhouse.service.DatabaseService
import com.google.android.material.snackbar.Snackbar

class SwipeToDelete(private val rwAdapter : FilteredListingsRecyclerViewAdapter, private val recyclerView: RecyclerView) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    private val itemTouchHelper = ItemTouchHelper(this)

    init {
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val list = Current.favoriteListings
        val position = viewHolder.adapterPosition
        val deletedItem = list[position]
        Current.favoriteListings.removeAt(viewHolder.adapterPosition).let {
            DatabaseService().removeFromFavorites(it.listingID)
        }

        rwAdapter.notifyItemRemoved(position)

    }



}