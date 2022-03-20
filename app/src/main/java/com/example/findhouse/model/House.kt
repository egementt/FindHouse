package com.example.findhouse.model

data class House(
    val roomNumber: String = "",
    val floorNumber: Int = 1,
    val squareMeter: Int = 1,
    val haveBalcony: Boolean = true,
    val heatingType: String ="None",
    val duePrice: Double = 1.0
){
    fun toFirebase(): HashMap<String, Any>{
        return hashMapOf(
            "roomNumber" to  roomNumber,
            "floorNumber" to floorNumber,
            "squareMetre" to squareMeter,
            "haveBalcony" to haveBalcony,
            "heatingType" to heatingType,
            "duePrice" to duePrice
        )
    }

    override fun toString(): String {
        return "House(roomNumber='$roomNumber', floorNumber=$floorNumber, squareMeter=$squareMeter, haveBalcony=$haveBalcony, heatingType='$heatingType', duePrice=$duePrice)"
    }


}

// 17 MART PERSEMBE 12.00
//16 MART DENIZ HOCAYA MAIL AT
//carşamba 2:20