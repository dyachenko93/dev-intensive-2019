package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern : String="HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND):Date {
    var time = this.time

    time += when(units) {
        TimeUnits.SECOND->value * SECOND
        TimeUnits.MINUTE->value * MINUTE
        TimeUnits.HOUR->value * HOUR
        TimeUnits.DAY->value * DAY
    }
    this.time = time
    return this
}

public fun Date.humanizeDiff(date: Date = Date()): String {
    when (this) {
        in Date().add(-1, TimeUnits.SECOND)..Date() -> return "только что"
        in Date().add(-45, TimeUnits.SECOND)..Date() -> return "несколько секунд назад"
        in Date()..Date().add(45, TimeUnits.SECOND) -> return "через несколько секунд"
        in Date().add(-75, TimeUnits.SECOND)..Date() -> return "минуту назад"
        in Date()..Date().add(75, TimeUnits.SECOND) -> return "через минуту"
        in Date().add(-45, TimeUnits.MINUTE)..Date() -> {
            val diff: Long = Math.round((Date().time - this.time) / (60 * 1000.0))
            when(diff % 10) {
                1L -> {
                    when {
                        diff != 11L -> return "$diff минуту назад"
                    }
                }
                in 2..4 -> {
                    when(diff) {
                        !in 11..14 -> return "$diff минуты назад"
                    }
                }
            }
            return "$diff минут назад"
        }
        in Date()..Date().add(45, TimeUnits.MINUTE) -> {
            val diff: Long = Math.round((this.time - Date().time) / (60 * 1000.0))
            when(diff % 10) {
                1L -> {
                    when {
                        diff != 11L -> return "через $diff минуту"
                    }
                }
                in 2..4 -> {
                    when(diff) {
                        !in 11..13 -> return "через $diff минуты"
                    }
                }
            }
            return "через $diff минут"
        }
        in Date().add(-75, TimeUnits.MINUTE)..Date() -> return "час назад"
        in Date()..Date().add(75, TimeUnits.MINUTE) -> return "через час"
        in Date().add(-22, TimeUnits.HOUR)..Date() -> {
            val diff: Long = Math.round((Date().time - this.time) / (60 * 60 * 1000.0))
            when(diff) {
                in listOf(2L, 3L, 4L, 22L) -> return "$diff часа назад"
                in listOf(1L,21L) -> return "$diff час назад"
                else -> return "$diff часов назад"
            }
        }
        in Date()..Date().add(22, TimeUnits.HOUR) -> {
            val diff: Long = Math.round((this.time - Date().time) / (60 * 60 * 1000.0))
            when(diff) {
                in listOf(2L, 3L, 4L, 22L) -> return "через $diff часа"
                in listOf(1L,21L) -> return "через $diff час"
                else -> return "через $diff часов"
            }
        }
        in Date().add(26, TimeUnits.HOUR)..Date() -> return "день назад"
        in Date()..Date().add(26, TimeUnits.HOUR) -> return "через день"
        in Date().add(-360, TimeUnits.DAY)..Date() -> {
            val diff: Long = Math.round((Date().time - this.time) / (24 * 60 * 60 * 1000.0))
            when(diff % 10) {
                1L -> {
                    when {
                        diff != 11L -> return "$diff день назад"
                    }
                }
                in 2..4 -> {
                    when(diff) {
                        !in 11..14 -> return "$diff дня назад"
                    }
                }
            }
            return "$diff дней назад"
        }
        in Date()..Date().add(360, TimeUnits.DAY) -> {
            val diff: Long = Math.round(((this.time - Date().time) / (24 * 60 * 60 * 1000.0)))
            when(diff % 10) {
                1L -> {
                    when {
                        diff != 11L -> return "через $diff день"
                    }
                }
                in 2..4 -> {
                    when(diff) {
                        !in 11..13 -> return "через $diff дня"
                    }
                }
            }
            return "через $diff дней"
        }
        else -> {
            when {
                this < Date() -> return "более года назад"
                else -> return "более чем через год"
            }
        }
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(i: Int): String {
        var res = "$i "
        when(this) {
            SECOND -> {
                when(getPeriod(i)) {
                    0 -> res += "секунд"
                    1 -> res += "секунду"
                    2 -> res += "секунды"
                }
            }
            MINUTE -> {
                when(getPeriod(i)) {
                    0 -> res += "минут"
                    1 -> res += "минуту"
                    2 -> res += "минуты"
                }
            }
            HOUR -> {
                when(getPeriod(i)) {
                    0 -> res += "часов"
                    1 -> res += "час"
                    2 -> res += "часа"
                }
            }
            DAY -> {
                when(getPeriod(i)) {
                    0 -> res += "дней"
                    1 -> res += "день"
                    2 -> res += "дня"
                }
            }
        }
        return res
    }

    fun getPeriod(i: Int): Int {
        var res = 0
        when(i % 10) {
            1 -> {
                if (i % 100 != 11) {
                    res = 1
                }
            }
            in 2..4 -> {
                if(i % 100 !in 12..14) {
                    res = 2
                }
            }
        }
        return res
    }
}