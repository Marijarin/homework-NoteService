package ru.netology

import java.time.LocalDateTime

open class Comment (
        open var id: Int = -1,
        open val ownerId: Int = -1, //author's id
        open val date: Int = LocalDateTime.now().nano,
        open val text: String = "This is the text of a comment",
        open var elemId: Int = -1,
        open var deleted: Boolean = false
        ) {
}
data class CommentToNote(
        override var id: Int = -1,
        override val ownerId: Int = -1,
        override val date: Int = LocalDateTime.now().nano,
        override val text: String = "This is a comment to a note",
        override var elemId: Int = -1,
        val guid: Int = 0,
        override var deleted: Boolean = false
) : Comment (id, ownerId, date, text, elemId, deleted)

