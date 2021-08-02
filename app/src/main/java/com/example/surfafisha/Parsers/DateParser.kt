package com.example.surfafisha.Parsers

class DateParser {

    companion object {
        private val monthMap = mapOf(
                Pair(1, "января"),
                Pair(2, "февраля"),
                Pair(3, "марта"),
                Pair(4, "апреля"),
                Pair(5, "мая"),
                Pair(6, "июня"),
                Pair(7, "июля"),
                Pair(8, "августа"),
                Pair(9, "сентября"),
                Pair(10, "октября"),
                Pair(11, "ноября"),
                Pair(12, "декабря")
        )

        /** Преобразует дату из вида ГГГГ-ММ-ДД в *день* *месяц* *год* **/
        fun formatDate(date: String) : String? {
            val arr = date.split("-")

            if (arr.size == 3) {

                val year = arr[Date.YEAR.num]
                val month = arr[Date.MONTH.num]
                val day = arr[Date.DAY.num].toInt().toString()

                val stringMonth = monthMap[month.toInt()]

                return "$day $stringMonth $year"
            }
            return null
        }
    }
}

enum class Date(val num: Int) {
    YEAR(0),
    MONTH(1),
    DAY(2)
}