package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName : String?) : Pair<String?, String?> {
        val parts : List<String>? = fullName?.split(" ")

        val firstName = when(parts?.getOrNull(0)) {
            ""," " -> null
            else -> parts?.getOrNull(0)
        }

        val lastName = when(parts?.getOrNull(1)) {
            ""," " -> null
            else -> parts?.getOrNull(1)
        }
        return firstName to lastName
    }

    val transliterationMap: HashMap<String, String> = hashMapOf(
        "а" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh'",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya"
    )

    fun transliteration(payload : String, divider : String = " "): String {
        var res = ""
        for (i in 0..payload.length-1) {
            when {
                payload.get(i).toString() in transliterationMap.keys ->
                    res += transliterationMap.get(payload.get(i).toString())
                payload.get(i).toString().toLowerCase() in transliterationMap -> {
                    val temp = transliterationMap.get(payload.get(i).toString().toLowerCase())
                    res += temp?.get(0)?.toUpperCase()
                    if (temp?.length!! > 1) {
                        res += temp.substring(1)
                    }
                }
                payload.get(i).toString() == " " ->
                    res += divider
                else ->
                    res += payload.get(i)
            }
        }
        return res
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var res = ""
        val fN: Char? = firstName?.trim()?.getOrNull(0)
        val lN: Char? = lastName?.trim()?.getOrNull(0)
        when(fN) {
            is Char -> res = fN.toUpperCase().toString()
        }
        when(lN) {
            is Char -> res += lN.toUpperCase()
        }
        when(res) {
            "" -> return null
            else -> return res
        }
    }
}