package com.example.findhouse.model

enum class NumberOfRooms(val numOfRoom:Int, val numOfHall: Int = 1) {
    STUDIO(numOfRoom = 1, numOfHall = 0),
    ONE_PLUS_ONE(numOfRoom = 1),
    TWO_PLUS_ONE(numOfRoom = 2),
    THREE_PLUS_ONE(numOfRoom = 3);

    companion object{


        fun toArray(): Array<String> {
            return arrayOf("1+0","1+1","2+1","3+1")
        }
    }
}