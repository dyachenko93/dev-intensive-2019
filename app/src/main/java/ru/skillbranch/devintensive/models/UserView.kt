package ru.skillbranch.devintensive.models

class UserView(
    val id:String,
    val fulllName:String,
    val nickName:String,
    var avatar:String? = null,
    var status:String? = "offline",
    val initials:String?
) {
    fun printMe() {
        println("""
            id: $id
            fulllName: $fulllName
            nickName: $nickName
            avatar: $avatar
            status: $status
            initials: $initials
        """.trimIndent())
    }
}