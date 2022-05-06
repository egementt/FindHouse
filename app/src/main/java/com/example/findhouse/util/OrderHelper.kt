package com.example.findhouse.util

import com.example.findhouse.model.HouseListing
import java.util.*
import kotlin.collections.ArrayList

class OrderHelper() {

    companion object{

         fun orderByLowestPrice(list: List<HouseListing>): List<HouseListing> {
           return list.sortedBy { x-> x.price }
        }

        fun orderByHighestPrice(list: List<HouseListing>): List<HouseListing> {
            return list.sortedByDescending { x-> x.price }
        }

        fun orderByNewest(list: List<HouseListing>): List<HouseListing> {
            return  list.sortedByDescending { x-> x.createdAt }
        }

        fun orderByOldest(list: List<HouseListing>): List<HouseListing> {
            return  list.sortedBy { x-> x.createdAt }
        }
    }
}