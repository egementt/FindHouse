package com.example.findhouse.util

import org.junit.Test
import java.sql.Timestamp

class OrderHelperTest{

    private val sampleSortedListForPrice = listOf(1,1200,1600,1500100, 1999999)
    private val sampleSortedListForHighestPrice = listOf(1999999,1500100,1600,1200,1)

    private val sampleSortedListForOldestTime = listOf( Timestamp.valueOf("2019-12-12 12:24:23"),Timestamp.valueOf("2020-12-12 01:24:23"), Timestamp.valueOf("2021-12-12 09:24:23"), Timestamp.valueOf("2022-12-12 04:24:23"))




    @Test
    fun orderByLowestPrice() {
        val shuffledList = sampleSortedListForPrice.shuffled()
        val sortedList = shuffledList.sortedBy { x-> x }
        assert(
            sortedList == sampleSortedListForPrice
        ) { "orderedList: $sampleSortedListForPrice || unorderedList: $sortedList" }
    }

    @Test
    fun orderByHighestPrice() {
        val shuffledList = sampleSortedListForHighestPrice.shuffled()
        val sortedList = shuffledList.sortedByDescending { x-> x }
        assert(
            sortedList == sampleSortedListForHighestPrice
        ) { "orderedList: $sampleSortedListForHighestPrice || unorderedList: $sortedList" }
    }

    @Test
    fun orderByOldest(){
        val shuffledList = sampleSortedListForOldestTime.shuffled()
        val sortedList = shuffledList.sorted()
        assert(sortedList == sampleSortedListForOldestTime) { "Ordered: $sampleSortedListForOldestTime || Unordered: $sortedList" }
    }

    @Test
    fun orderByNewest(){
        val sortedList = sampleSortedListForOldestTime.sortedDescending()
        assert(sortedList == sampleSortedListForOldestTime.reversed()) { "Ordered: ${sampleSortedListForOldestTime.reversed()} || Unordered: $sortedList" }


    }

}