import java.lang.IllegalArgumentException

interface Identifiable {
    val id: Int
    val text: String
}

data class Note(
        override val id: Int,
        val userId: Int,
        val title: String,
        override val text: String,
        val delete: Boolean = false,
        val comments: MutableList<Comment> = mutableListOf()
) : Identifiable

data class Comment(
        override val id: Int = 0,
        val noteId: Int = 0,
        override val text: String,
        val delete: Boolean = false,
) : Identifiable

object NoteService {
    private var noteList = mutableListOf<Note>()
    private var noteComment = mutableListOf<Comment>()

    fun print() {
        for (i in noteList.indices) if (!noteList[i].delete) println(noteList[i])
        println()
        for (i in noteComment.indices) if (!noteComment[i].delete) println(noteComment[i])
        println()
        println()
    }

    fun <T : Identifiable> add(list: List<T>) {
        for (objects in list) {
            when (objects) {
                is Note ->
                    when (noteList.isEmpty()) {
                        true -> noteList.add(objects.copy(delete = false))
                        false ->
                            for (i in noteList.indices) {
                                if (noteList[i].id != objects.id) noteList.add(objects.copy(delete = false))
                            }
                    }

                is Comment -> for (i in noteList.indices) {
                    if (noteList[i].id == objects.noteId) {
                        noteComment.add(objects.copy(delete = false))
                    }
                }

                else -> throw IllegalArgumentException("Unknown type")
            }
        }
    }
    

    fun <T : Identifiable> delete(idDelete: Int, list: List<T>) {
        for (objects in list) {
            when (objects) {
                is Note -> {
                    for (j in noteComment.indices) {
                        if (idDelete == noteComment[j].noteId) {
                            val x = noteComment[j]
                            noteComment.remove(noteComment[j])
                            noteComment.add(j, x.copy(delete = true))
                        }
                    }

                    for (i in noteList.indices) {
                        if (idDelete == noteList[i].id) {
                            val x = noteList[i]
                            noteList.remove(noteList[i])
                            noteList.add(i, x.copy(delete = true))
                        }
                    }
                }

                is Comment ->
                    for (i in noteComment.indices) {
                        if (idDelete == noteComment[i].id) {
                            val x = noteComment[i]
                            noteComment.remove(noteComment[i])
                            noteComment.add(x.copy(delete = true))
                        }
                    }

                else -> throw IllegalArgumentException("Unknown type")
            }
        }
    }

    fun <T : Identifiable> edit(id: Int, list: List<T>) {
        for (objects in list) {
            when (objects) {
                is Note ->
                    for (i in noteList.indices) {
                        val x = objects
                        if (noteList[i].id == id && !noteList[i].delete) {
                            noteList.remove(noteList[i])
                            noteList.add(x)
                        }
                    }

                is Comment ->
                    for (i in noteComment.indices) {
                        if (noteComment[i].id == id && !noteComment[i].delete) {
                            noteComment.remove(noteComment[i])
                            noteComment.add(i, objects)
                        }
                    }
            }
        }
    }

    /*fun addNote(note: Note): Note {
        noteList += note.copy()
        return noteList.last()
    }

    fun createComment(comment: Comment): Comment {
        for (i in noteList.indices) {
            if (noteList[i].id == comment.noteId) {
                noteComment += comment.copy()
            }
        }
        return noteComment.last()
    }

    fun deleteNote(idNoteDelete: Int, note: Note): Boolean {
        for (i in noteList.indices) {
            if (idNoteDelete == note.id) {
                noteList.remove(noteList[i])
                noteDeleteList += noteList[i]
                return true
            }
        }
        return false
    }

    fun deleteComment(idCommentDelete: Int, comment: Comment): Boolean {
        for (i in noteComment.indices) {
            if (idCommentDelete == comment.id) {
                noteComment.remove(noteComment[i])
                noteDeleteComment += noteComment[i]
                return true
            }
        }
        return false
    }

    fun editNote(newNote: Note): Boolean {
        for (i in noteList.indices) {
            if (newNote.id == noteList[i].id) {
                noteList.remove(noteList[i])
                noteList.add(newNote)
                return true
            }
        }
        return false
    }

    fun editComment(newComment: Comment): Boolean {
        for (i in noteComment.indices) {
            if (newComment.id == noteComment[i].id) {
                noteComment.remove(noteComment[i])
                noteComment.add(newComment)
                return true
            }
        }
        return false
    }*/

    fun restoreComment(idComment: Int): Boolean {
        for (i in noteComment.indices) {
            val x = noteComment[i]
            if (idComment == noteComment[i].id && noteComment[i].delete) {
                noteComment.remove(noteComment[i])
                noteComment.add(i, x.copy(delete = false))
                return true
            }
        }
        return false
    }

    fun getById(idNote: Int): Note {
        for (i in noteList.indices) {
            if (idNote == noteList[i].id) {
                return noteList[i]
            }
        }
        return throw IllegalAccessException("")
    }

    fun notesGet(userId: Int): Note {
        for (i in noteList.indices) {
            if (userId == noteList[i].userId && !noteList[i].delete) {
                return noteList[i]
            } else {
                "Not found note"
            }
        }
        return throw IllegalAccessException ("Not found note")
    }

        fun notesGetComments(noteId: Int, comments: Comment): MutableList<Comment> {
            var notesGetComments = mutableListOf<Comment>()
            for (i in noteComment.indices) {
                if (noteId == noteComment[i].noteId) {
                    notesGetComments += comments
                }
            }
            return notesGetComments
        }
    }

class NotFoundException(message: String) : RuntimeException(message)
class NotFoundElement(message: String) : RuntimeException(message)

fun main(args: Array<String>) {
    NoteService.add(listOf(Note(1, 1, "title", "first")))
    NoteService.add(listOf(Note(1, 1, "title", "repit first")))
    NoteService.add(listOf(Note(2, 2, "title", "second")))
    NoteService.add(listOf(Comment(1, 1, "comment")))
    NoteService.add(listOf(Comment(2, 2, "comment2")))
    NoteService.print()
    NoteService.delete(1, list = listOf((Note(1, 1, "title", "first"))))
    NoteService.print()
    NoteService.delete(2, list = listOf(Comment(2, 2, "comment2")))
    NoteService.print()
    NoteService.edit(2, list = listOf(Note(2, 2, "title", "EDITE SECOND NOTE")))
    NoteService.print()
    NoteService.restoreComment(2)
    NoteService.print()
    println("Вывод функции getById ${NoteService.getById(2)}")
    println("Вывод функции notesGet ${NoteService.notesGet(2)}")
    println( NoteService.notesGetComments(2, Comment(2, 2, "comment2")))
}
