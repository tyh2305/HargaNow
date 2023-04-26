package com.example.harganow.domain.model

import java.util.Calendar

data class ItemDate(
    var day: Int,
    var month: Int,
    var year: Int,
) {

    constructor() : this(0, 0, 0)

    companion object {
        fun fromString(input: String): ItemDate? {
            // Use regex to validate input
            var reg = Regex("^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])\$")
            if (reg.matches(input)) {
                val date = input.split("-")
                val day = date[0].toInt()
                val month = date[1].toInt()
                val year = date[2].toInt()
                return ItemDate(day, month, year)
            } else {
                return ItemDate(1,1,2023)
            }
        }

        fun fromDate(date: java.util.Date): ItemDate {
            val cal = java.util.Calendar.getInstance()
            cal.time = date
            return ItemDate(
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.YEAR)
            )
        }

        fun toString(date: ItemDate): String {
            return "${date.day}/${date.month}/${date.year}"
        }
    }
}

