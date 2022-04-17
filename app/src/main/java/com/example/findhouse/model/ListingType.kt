package com.example.findhouse.model

enum class ListingType {
    FOR_SALE, FOR_RENT;



    companion object{

        fun toArray(): Array<String>{
            return arrayOf(fromListingTypeToString(FOR_SALE), fromListingTypeToString(FOR_RENT))
        }

         fun fromListingTypeToString(listingType: ListingType): String{
            return if (listingType == FOR_RENT ){
                "FOR RENT"
            }else{
                "FOR SALE"
            }
        }

        fun fromStringToListingType(string: String): ListingType{
            return if (string == "FOR RENT"){
                FOR_RENT
            }else
                FOR_SALE
        }
    }
}
