package ru.skillbranch.devintensive.extensions

fun String.truncate(size: Int = 16): String {
    var res: String
    res = this.trim()
    if(res.length > size) {
        res = res.substring(0, size).trim() + "..."
    }
    return res
}

fun String.stripHtml(): String {
    var res: String
    res = ""
    var i = 0
    while(i < this.length) {
        if(this.get(i).equals('<')) {
            do {
                i++
            } while (this.get(i) != '>')
            i++
        } else if(this.get(i).equals(' ')) {
            while(this.get(i) == ' ') {
                i++
            }
            res += ' '
        } else {
            res += this.get(i)
            i++
        }
    }
    return res
}