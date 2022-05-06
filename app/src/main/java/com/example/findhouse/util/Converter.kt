package com.example.findhouse.util

import com.example.findhouse.model.HouseListing

class Converter {
    companion object{

        fun listToArrayList(list: List<HouseListing>): ArrayList<HouseListing> {
            val arrayList : ArrayList<HouseListing> = arrayListOf()
            list.forEach {
                arrayList.add(it)
            }
            return arrayList
        }
    }
}