package com.example.findhouse.model

data class House(
    val roomNumber: NumberOfRooms = NumberOfRooms.ONE_PLUS_ONE,
    val floorNumber: Int,
    val squareMeter: Int,
    val haveBalcony: Boolean,
    val heatingType: HeatingType = HeatingType.NONE,
    val duePrice: Double
)

// 17 MART PERSEMBE 12.00
//16 MART DENIZ HOCAYA MAIL AT
//carşamba 2:20