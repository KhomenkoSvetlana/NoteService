import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun addNote() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))

        val addResult = addNote.id

        assertEquals(addResult, 1)
    }

    @Test
    fun addComment() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val addComment = noteService.add(elem = Comment(1,1,"text"))

        val addResult = addComment.id

       assertEquals(addResult, 1)
    }

    @Test
    fun deleteNoteTrue() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val deleteNote = noteService.delete(elem = Note (1, 1, "title", "first"))

        assertTrue(deleteNote)
    }

    @Test
    fun deleteNoteFalse() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val deleteNote = noteService.delete(elem = Note (2, 1, "title", "first"))

        assertFalse(deleteNote)
    }

    @Test
    fun deleteCommentTrue() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val addComment = noteService.add(elem = Comment(1,1,"text"))
        val deleteComment = noteService.delete(elem = Comment(1,1,"text"))

        assertTrue(deleteComment)
    }

    @Test
    fun deleteCommentFalse() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val addComment = noteService.add(elem = Comment(1,1,"text"))
        val deleteComment = noteService.delete(elem = Comment(2,1,"text"))

        assertFalse(deleteComment)
    }

    @Test
    fun editNoteTrue() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val editNote = noteService.edit(elem = Note (1, 1, "title", "second"))

        assertTrue(editNote)
    }

    @Test
    fun editNoteFalse() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val editNote = noteService.edit(elem = Note (5, 1, "title", "second"))

        assertFalse(editNote)
    }

    @Test
    fun editCommentTrue() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val addComment = noteService.add(elem = Comment(1,1,"text"))
        val editNote = noteService.edit(elem = Comment(1,1,"text2"))

        assertTrue(editNote)
    }

    @Test
    fun editCommentFalse() {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val addComment = noteService.add(elem = Comment(1,1,"text"))
        val editNote = noteService.edit(elem = Comment(2,1,"text2"))

        assertFalse(editNote)
    }

    @Test
    fun restoreCommentTrue () {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val addComment = noteService.add(elem = Comment(1,1,"text"))
        val deleteComment = noteService.delete(elem = Comment(1,1,"text"))
        val restoreComment = noteService.restoreComment(1)
        assertTrue(restoreComment)
    }

    @Test
    fun restoreCommentFalse () {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val addComment = noteService.add(elem = Comment(1,1,"text"))
        val deleteComment = noteService.delete(elem = Comment(1,1,"text"))
        val restoreComment = noteService.restoreComment(2)
        assertFalse(restoreComment)
    }

    @Test
    fun getByIdTrue () {
        val noteService = NoteService
        val addNote= noteService.add(elem = Note (1, 1, "title", "first"))
        val getById = noteService.getById(1)
        val result = getById.id

        assertEquals(result, 1)
    }


    @Test
    fun notesGetTrue () {
        val noteService = NoteService
        val addNote1= noteService.add(elem = Note (1, 1, "title", "first"))
        val addNote2= noteService.add(elem = Note (2, 1, "title", "second"))
        val notesGet = noteService.notesGet(1)

        val result = notesGet[0].userId

        assertEquals(result, 1)
    }

    @Test
    fun notesGetComments() {
        val noteService = NoteService
        val addNote1= noteService.add(elem = Note (1, 1, "title", "first"))
        val addComment = noteService.add(elem = Comment(1,1,"text"))
        val notesGetComments = noteService.notesGetComments(1)

        val result = notesGetComments[0].noteId

        assertEquals(result, 1)
    }






}