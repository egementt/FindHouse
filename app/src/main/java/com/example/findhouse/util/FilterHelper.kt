package com.example.findhouse.util

import com.example.findhouse.model.HouseListing
import com.example.findhouse.model.ListingType

class FilterHelper {


    companion object {

        private var currencyConverter = CurrencyConverter()

        fun filterByListingType(list: List<HouseListing>, type: String): List<HouseListing> {
            return list.filter { x -> x.listingType == type.uppercase() }
        }

        fun filterByPriceRange(
            list: List<HouseListing>,
            minPrice: String,
            maxPrice: String
        ): List<HouseListing> {
            return list.filter { x ->
                currencyConverter.convertCurrency(x.price) > currencyConverter.convertCurrency(
                    minPrice
                ) && currencyConverter.convertCurrency(x.price) < currencyConverter.convertCurrency(
                    maxPrice
                )
            }
        }

        fun filterBySquareMeter(list: List<HouseListing>, squareMeter: Int): List<HouseListing> {
            return list.filter { x -> x.house!!.squareMetre <= squareMeter }
        }

        fun filterByUniversity(newListing: List<HouseListing>, selectedUniversityName: String): List<HouseListing> {
            return newListing.filter { x ->
                x.university.name == selectedUniversityName
            }
        }
    }
}