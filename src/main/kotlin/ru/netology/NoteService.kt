package ru.netology

class NoteService : CrudService<Note, CommentToNote>(){
    override val elems = mutableListOf<Note>()
    override val comments = mutableListOf<CommentToNote>()
}