package ru.skillbranch.devintensive.extensions

fun String.truncate(size: Int = 16): String {
    var res: String
    res = this.trim()
    if(res.length > size) {
        res = res.substring(0, size).trim() + "..."
    }
    return res
}