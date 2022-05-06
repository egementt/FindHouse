package com.example.findhouse.util

import androidx.room.util.StringUtil
import org.junit.Test
import java.lang.StringBuilder
import java.util.*
import kotlin.math.pow

class CurrencyConverterTest {

    val currencyMin = "1,200"
    val currencyMax = "1,200,000,000,000,000"
    val currencyMaxButInt = 1200000000



    @Test
    fun convertCurrencyToInt(){


        assert( currencyToInt(currencyMin) < currencyToInt(currencyMax) )

    }

    private fun currencyToInt(currency: String): Long {
        val str = currency.toCharArray()
        val stack = Stack<Char>()
        var result = ""

        str.forEach {
            if (it != ','){
                val char =  stack.push(it)
                result += char
            }
        }
        return result.toLong()
    }



}