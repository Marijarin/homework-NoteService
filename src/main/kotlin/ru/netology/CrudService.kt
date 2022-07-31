package ru.netology

abstract class CrudService<T, K> where T : Element, K : Comment {
     open val elems = mutableListOf<T>()
     val elemIds = mutableListOf<Int>()
     open val comments = mutableListOf<K>()
     val commentIds = mutableListOf<Int>()
    open fun add(elem: T): T {
        elem.id = elem.hashCode()
        elems += elem
        elemIds += elem.id
        return elems.last()
    }

    open fun update(element: T, ident: Int): Boolean {
        var flag = false
        for ((index, _) in elems.withIndex())
            if (elems[index].id == ident) {
                element.id = ident
                elems[index] = element
                flag = true
            }
        return flag
    }

    open fun delete(elemId: Int): Int {
        if (!elemIds.contains(elemId)) {
            return 0
        }
        for ((index, _) in elems.withIndex()) {
            if (elemId == elems[index].id) {
                elems[index].deleted = true
                elemIds.remove(elemId)
                break
            }
        }
        return 1
    }

    open fun get(): String {
        val existingElems = mutableListOf<T>()
        for (elem in elems){
            if (!elem.deleted){
                existingElems+=elem
            }
        }
        return existingElems.toString()
    }

    open fun getById(elemid: Int): String {
        var found = ""
        for ((index, element) in elems.withIndex()) {
            if (elemid == element.id) {
                found = elems[index].toString()
                break
            } else throw NoteNotFoundException("No note with $elemid")
        }
        return found
    }

    open fun createCommentWithCid(elemId: Int, comment: K): Int {
        if (!elemIds.contains(elemId)) {
            throw ElementNotFoundException("No element with $elemId")
        }
        val cid = comment.hashCode()
        comment.id = cid
        comment.elemId = elemId
        comments += comment
        commentIds += cid
        return cid
    }

    open fun getComments(noteId: Int, ownerId: Int, sort: Boolean, offset: Int = 0, count: Int = 1): String {
        val noteComments = mutableListOf<K>()
        val s : String
        for (comment in comments){
            if (comment.elemId == noteId && !comment.deleted) {
                noteComments += comment
            }
        }
        if (noteComments.isNotEmpty()){
            s = when (sort){
                    true -> noteComments.slice(offset..(offset + count)).toString()
                    false -> noteComments.slice(offset..(offset + count)).asReversed().toString()
                }
            } else return "No comments for note $noteId"
        if (!elemIds.contains(noteId))throw NoteNotFoundException("No note with $noteId")
        return s
    }

    open fun editComment(comment: K, cid: Int): Boolean {
        var flag = false
        for ((index) in comments.withIndex()) {
            if (commentIds[index] == cid) {
                comment.id = cid
                comments[index] = comment
                flag = true
            }
        }
        return flag
    }

    open fun deleteComment(commentId: Int): Int {
        if (!commentIds.contains(commentId)) {
            return 0
        }
        for ((index, comment) in comments.withIndex()) {
            if (commentId == comment.id) {
                comments[index].deleted = true
                commentIds.remove(commentId)
                break
            }
        }
        return 1
    }


    open fun getFriendsNotes(ownerId: Int, offset: Int, count: Int): String {
        return elems.slice(offset..(offset + count)).toString()
    }

    open fun restoreComment(commentId: Int): Int {
        if (commentIds.contains(commentId)) {
            return 0
        } else {
            for ((index, comment) in comments.withIndex()) {
                if (commentId == comment.id && comment.deleted) {
                    comments[index].deleted = false
                    commentIds+= commentId
                    break
                }
                if (commentId == comment.id ){
                    return 0
                }
            }
        }
        return 1
    }
}



