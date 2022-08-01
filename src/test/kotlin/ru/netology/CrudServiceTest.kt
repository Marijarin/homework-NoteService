package ru.netology

import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun add() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))
        var result = true
        if (service.elems.isEmpty()){
            result = false
        }
        assertTrue(result)
    }

    @Test
    fun updateExistingWithDifferentId() {

        val service = NoteService()
        val update = Note(text = "Updated note for test")

        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))

        val result = service.update(update,-1)

        assertFalse(result)

    }

    @Test
    fun updateExistingWithSameId() {

        val service = NoteService()
        val update = Note(text = "Updated first note for test")

        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))

        val result = service.update(update,service.elemIds[0])

        assertTrue(result)
    }


    @Test
    fun deleteExisting() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))

        val result = service.delete(service.elems[1].id)

        assertEquals(1, result)
    }

    @Test
    fun deleteNotExisting() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))

        val result = service.delete(-1)
        assertEquals(0, result)

    }

    @Test
    fun get() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))

        val result = service.get()
        var resulted = false
        if (result.contains(("deleted"), true)){
           resulted = true
        }
        assertTrue(resulted)

    }

    @Test(expected = NoteNotFoundException::class)
    fun getByIdShouldThrow() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))

        val result = service.getById(-1)
        var resulted = false
        if (result.contains(("deleted"), true)){
            resulted = true
        }
        assertTrue(resulted)




    }
    @Test
    fun getByIdShouldNotThrow() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))


        val result = service.getById(service.elemIds[0])

        var resulted = false
        if (result.contains(("First note for test"), true)){
            resulted = true
        }
        assertTrue(resulted)
    }

    @Test
    fun createCommentWithCidExistingNote() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))

        val result = service.createCommentWithCid(service.elemIds[1], CommentToNote()).toString()
        val resulted = result.isNotEmpty()
        assertTrue(resulted)

    }
    @Test(expected = ElementNotFoundException::class)
    fun createCommentWithCidNonExistingNote() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))

        val result = service.createCommentWithCid(-1, CommentToNote()).toString()
        val resulted = result.isNotEmpty()
        assertTrue(resulted)

    }

    @Test
    fun getComments() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))
        service.createCommentWithCid(service.elemIds[0], CommentToNote())
        service.createCommentWithCid(service.elemIds[0], CommentToNote(text = "Second comment"))

        val result = service.getComments(service.elemIds[0], service.elems[0].ownerId, sort = true)
        var resulted = false
        if (result.contains(("deleted"), true)){
            resulted = true
        }
        assertTrue(resulted)
    }

    @Test
    fun editComment() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))
        service.createCommentWithCid(service.elemIds[0], CommentToNote())
        service.createCommentWithCid(service.elemIds[0], CommentToNote(text = "Second comment"))

        val result = service.editComment(CommentToNote(text = "Comment was edited"), service.comments[1].id)
        assertTrue(result)


    }

    @Test
    fun deleteCommentExisting() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))
        service.createCommentWithCid(service.elemIds[0], CommentToNote())
        service.createCommentWithCid(service.elemIds[0], CommentToNote(text = "Second comment"))

        val result = service.deleteComment(service.comments[1].id)
        assertEquals(1, result)


    }

    @Test
    fun deleteCommentNonExisting() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))
        service.createCommentWithCid(service.elemIds[0], CommentToNote())
        service.createCommentWithCid(service.elemIds[0], CommentToNote(text = "Second comment"))

        val result = service.deleteComment(-1)
        assertEquals(0, result)

    }

    @Test
    fun restoreCommentDeleted() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))
        service.createCommentWithCid(service.elemIds[0], CommentToNote())
        service.createCommentWithCid(service.elemIds[0], CommentToNote(text = "Second comment"))
        service.deleteComment(service.comments[1].id)

        val result = service.restoreComment(service.comments[1].id)
        assertEquals(1, result)
    }

    @Test
    fun restoreCommentNotDeleted() {
        val service = NoteService()
        service.add(Note(text = "First note for test"))
        service.add(Note(text = "Second note for test"))
        service.add(Note(text = "Third note for test"))
        service.createCommentWithCid(service.elemIds[0], CommentToNote())
        service.createCommentWithCid(service.elemIds[0], CommentToNote(text = "Second comment"))


        service.deleteComment(service.comments[1].id)

        val result = service.restoreComment(service.comments[0].id)
        assertEquals(0, result)
    }
}