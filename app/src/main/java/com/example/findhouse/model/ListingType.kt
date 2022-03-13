package com.example.findhouse.model

enum class ListingType {
    FOR_SALE, FOR_RENT;



    companion object{

        fun toArray(): Array<String>{
            return arrayOf(toString(FOR_SALE), toString(FOR_RENT))
        }

        private fun toString(listingType: ListingType): String{
            return if (listingType == ListingType.FOR_RENT ){
                "FOR RENT"
            }else{
                "FOR SALE"
            }
        }

        fun toListingType(string: String): ListingType{
            return if (string == "FOR RENT"){
                FOR_RENT
            }else
                FOR_SALE
        }
    }
}
