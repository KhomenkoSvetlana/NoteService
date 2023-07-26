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

    fun <T : Identifiable> add(elem: T): Identifiable {
        return when (elem) {
            is Note -> {
                when (noteList.isEmpty()) {
                    true -> noteList.add(elem.copy(delete = false))
                    false -> {
                        for (i in noteList.indices) if (elem.id != noteList[i].id) noteList.add(elem.copy(delete = false))
                    }
                }
                noteList.last()
            }

            is Comment -> {
                for (i in noteList.indices) {
                    if (noteList[i].id == elem.noteId) {
                        noteComment.add(elem.copy(delete = false))
                    }
                }
                noteComment.last()
            }

            else -> throw Exception("Список для этого типа элементов не определён в сервисе")
        }
    }

    fun <T : Identifiable> delete(elem: T): Boolean {
        return when (elem) {
            is Note -> {
                for (i in noteList.indices) {
                    val x = noteList[i]
                    if (elem.id == noteList[i].id) {
                        noteList.remove(noteList[i])
                        noteList += x.copy(delete = true)
                        noteList.last()
                        return true
                        }
                    }
                for (j in noteComment.indices){
                    val y = noteComment[j]
                    if( noteComment[j].noteId == elem.id){
                        noteComment.remove(noteComment[j])
                        noteComment += y.copy(delete = true)
                        noteComment.last()
                    }
                }
                return false
            }

            is Comment -> {
                for (i in noteComment.indices) {
                    if (elem.id == noteComment[i].id) {
                        noteComment += noteComment[i].copy(delete = true)
                        noteComment.last()
                        return true
                    }
                }
                return false
            }

            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    fun <T : Identifiable> edit(elem: T): Boolean {
        return when (elem) {
            is Note -> {
                for (i in noteList.indices) {
                    if (elem.id == noteList[i].id) { //&& noteList[i].delete == false почему не работает проверка?
                        noteList.remove(noteList[i])
                        noteList += elem.copy()
                        noteList.last()
                        return true
                    }
                }
                return false
            }

            is Comment -> {
                for (i in noteComment.indices) {
                    val x = noteComment[i]
                    if (noteComment[i].id == elem.id && !noteComment[i].delete) {
                        noteComment.remove(noteComment[i])
                        noteComment += x.copy()
                        noteComment.last()
                        return true
                    }
                }
                return false
            }

            else -> throw IllegalArgumentException("Unknown type")
        }
    }

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

    fun getById(idNote: Int): Boolean {
        for (i in noteList.indices) {
            if (idNote == noteList[i].id) {
                noteList += noteList[i]
                return true
            }
        }
        return false
    }

    fun notesGet(userId: Int): Boolean {
        for (i in noteList.indices) {
            if (userId == noteList[i].userId && noteList[i].delete == false) {
                return true
            } else {
                "Not found note"
            }
        }
        return false
    }

    fun notesGetComments(userId: Int): Boolean {
        for (i in noteComment.indices) {
            if (userId == noteComment[i].noteId && noteList[i].delete == false) {
                return true
            } else {
                "Not found note"
            }
        }
        return false
    }
}

class NotFoundException(message: String) : RuntimeException(message)
class NotFoundElement(message: String) : RuntimeException(message)

fun main(args: Array<String>) {
    NoteService.add(elem = Note(1, 1, "title", "text"))
    NoteService.add(elem = Note(1, 1, "title", "repit first"))
    NoteService.add(elem = Note(2, 2, "title", "second"))
    NoteService.add(elem = Comment(1, 1, "comment"))
    NoteService.add(elem = Comment(2, 2, "comment2"))
    NoteService.print()
    NoteService.delete(elem = Note(1, 1, "title", "text"))
    NoteService.print()
    NoteService.edit(elem = Note(2, 2, "title", "second edit"))
    NoteService.print()
    println( NoteService.notesGet(2))
    NoteService.print()


}
