package com.example.findhouse.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class House(
    val roomNumber: String = "",
    val floorNumber: Int = 1,
    val squareMetre: Int = 1,
    val haveBalcony: Boolean = true,
    val heatingType: String ="None",
    val duePrice: Double = 1.0,
    var address: String = "",
    var location: GeoPoint = GeoPoint(0.0,0.0)
){
    fun toFirebase(): HashMap<String, Any>{
        return hashMapOf(
            "roomNumber" to  roomNumber,
            "floorNumber" to floorNumber,
            "squareMetre" to squareMetre,
            "haveBalcony" to haveBalcony,
            "heatingType" to heatingType,
            "duePrice" to duePrice,
            "address" to address,
            "location" to location
        )
    }

    fun toDetailListModel() : Map<String, Any>{
        return  mapOf(
            "Room Number" to roomNumber,
            "Floor Number" to floorNumber,
            "Square Meter" to squareMetre,
            "Balcony" to haveBalcony,
            "Heating Type" to heatingType,
            "Due Price" to duePrice,
            "Address" to address,
        )
    }

    override fun toString(): String {
        return "House(roomNumber='$roomNumber', floorNumber=$floorNumber, squareMeter=$squareMetre, haveBalcony=$haveBalcony, heatingType='$heatingType', duePrice=$duePrice)"
    }


}

// 17 MART PERSEMBE 12.00
//16 MART DENIZ HOCAYA MAIL AT
//carşamba 2:20