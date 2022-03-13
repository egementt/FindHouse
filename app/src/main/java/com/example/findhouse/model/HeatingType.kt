package com.example.findhouse.model

enum class HeatingType{
    NONE,
    NATURAL_GAS,
    FUEL_OIL,
    GEOTHERMAL;


    companion object {
        fun toArray(): Array<String>{
            return arrayOf("NONE", "NATURAL GAS", "FUEL_OIL", "GEOTHERMAL")
        }


    }
}


