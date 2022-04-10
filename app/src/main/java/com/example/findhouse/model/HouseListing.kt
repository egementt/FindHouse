package com.example.findhouse.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp


data class HouseListing(
    val title: String ="",
    val description: String ="",
    var listingID: String = "",
    val authorID : String = "",
    var photos: MutableList<String> = mutableListOf<String>(), //from firebase
    val price: String = "",
    val listingType: String = ListingType.toString(ListingType.FOR_SALE),
    var house: House? = House(),
    var createdAt : Timestamp = Timestamp.now(),
    var university: University= University(),
    ){



    fun toFirebase(): HashMap<String, Any>{
        return hashMapOf(
            "title" to title,
            "description" to description,
            "listingID" to listingID,
            "authorID" to authorID,
            "photos" to photos,
            "price" to price,
            "listingType" to listingType,
            "house" to (house?.toFirebase() ?: "null"),
            "university" to university,
            "createdAt" to createdAt
        )
    }

    fun toDetailListItemModels(): List<DetailListItemModel>{
        val list = mutableListOf<DetailListItemModel>()

            list.add(DetailListItemModel(key = "Price", value = price))
            list.add(DetailListItemModel(key = "Listing Type", value = listingType))
            list.add(DetailListItemModel(key = "University", value = university.name))

        house?.toDetailListModel()
            ?.forEach { list.add(DetailListItemModel(key = it.key, value = it.value)) }

        return list
    }




    override fun toString(): String {
        return "HouseListing(title='$title', description='$description', photos=$photos, price='$price', listingType='$listingType', house=$house, createdAt=$createdAt)"
    }


}