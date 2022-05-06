package com.example.findhouse.util

import java.util.*

class CurrencyConverter {

    fun convertCurrency(currency: String) : Int {
        return currencyToInt(currency)
    }

     private fun currencyToInt(currency: String): Int {
            val str = currency.toCharArray()
            val stack = Stack<Char>()
            var result = ""

            str.forEach {
                if (it != ','){
                    val char =  stack.push(it)
                    result += char
                }
            }
            return result.toInt()
        }

}