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

    fun transliteration(payload : String, divider : String = " "): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun initials(firstName: String?, lastName: String?): String? {
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