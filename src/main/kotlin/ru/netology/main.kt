package ru.netology

fun main() {
    val noteService = NoteService()
    val note1 = Note(text = "First note")
    val note2 = Note(text = "Second note")
    val noteUpd = Note(text = "Updated note")
    val note1Added: Note = noteService.add(note1)
    val note2Added: Note = noteService.add(note2)
    println(noteService.getById(note1Added.id))
    println(
        noteService.createCommentWithCid(
            note2Added.id,
            CommentToNote(text = "First comment to the second note")
        )
    )
    println(
        noteService.createCommentWithCid(
            note2Added.id,
            CommentToNote(text = "Second comment to the second note")
        )
    )
    println(
        noteService.createCommentWithCid(
            note1Added.id,
            CommentToNote(text = "First comment to the first note")
        )
    )
    println(
        noteService.createCommentWithCid(
            note1Added.id,
            CommentToNote(text = "Second comment to the first note")
        )
    )
    println(noteService.getComments(note2Added.id, note2Added.ownerId, false))
    println(noteService.update(noteUpd, noteService.elemIds[0]))
    println(noteService.editComment(CommentToNote(text = "Comment was edited"), noteService.commentIds[1]))
    println(noteService.deleteComment(noteService.commentIds[0]))
    println(noteService.restoreComment(noteService.commentIds[0]))
    println(noteService.get())
    println(noteService.comments.toString())
    println(noteService.getComments(note1Added.id, note1Added.ownerId, true))
}