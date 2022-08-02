package br.com.alexf.repositories

import br.com.alexf.models.Note
import java.util.*

class NoteRepository {
    companion object {
        private val notes = mutableListOf<Note>(
            Note(
                UUID.randomUUID().toString(),
                "test title",
                "test message"
            )
        )
    }

    val notes get() = Companion.notes.toList()

    fun findById(id: String) =
        notes.find {
            it.id == id
        }

    fun save(note: Note) {
        Companion.notes.add(note)
    }

    fun remove(id: String) =
        Companion.notes.removeIf {
            id == it.id
        }

}