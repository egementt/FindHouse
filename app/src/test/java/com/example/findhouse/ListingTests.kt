package com.example.findhouse

import org.junit.Test

class ListingTests {

    private val favoriteListingIDList = listOf("I2ZY9ISyQuI0dYH8RcDB", "iTD7NX9sLNzSpXg1NKPm", "8xBt7GykLDX6LL4SZlnO")



    @Test
    fun isListingFavorite(listingID: String){
        assert(favoriteListingIDList.contains(listingID))
    }
}
