package ru.netology

import java.time.LocalDateTime

open class Element(
    open var id: Int = -1,
    open val ownerId: Int = 1,
    open val fromId: Int = 1,
    open val date: Int = LocalDateTime.now().nano,
    open val text: String = "Text of an element",
    open var deleted: Boolean = false
) {
}

data class Note(
    override var id: Int = -1,
    override val ownerId: Int = 1,
    override val fromId: Int = 1,
    override val date: Int = LocalDateTime.now().nano,
    override val text: String,
    override var deleted: Boolean = false
) : Element(id, ownerId, fromId, date, text, deleted) {}