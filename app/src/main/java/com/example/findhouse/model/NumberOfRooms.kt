package com.example.findhouse.model

enum class NumberOfRooms(val numOfRoom:Int, val numOfHall: Int = 1) {
    STUDIO(numOfRoom = 1, numOfHall = 0),
    ONE_PLUS_ONE(numOfRoom = 1),
    TWO_PLUS_ONE(numOfRoom = 2),
    THREE_PLUS_ONE(numOfRoom = 3);

    companion object{
        override fun toString() : String{
            return "${NumberOfRooms.values().first().numOfRoom}+${NumberOfRooms.values().first().numOfHall} "
        }
    }
}