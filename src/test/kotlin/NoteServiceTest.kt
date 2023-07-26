import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun addNote() {
        val noteService = NoteService
        val add= noteService.add(listOf(Note(1, 1, "title", "first"))) as Note

        val addResult = add.id

        assertEquals(addResult, 1)
    }
}