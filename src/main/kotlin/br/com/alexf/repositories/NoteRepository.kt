package br.com.alexf.repositories

import br.com.alexf.database.dao.NotesDao
import br.com.alexf.database.dao.NotesDaoImpl
import br.com.alexf.models.Note
import java.util.*

class NoteRepository(
    private val dao: NotesDao = NotesDaoImpl
) {

    suspend fun getAll(): List<Note> = dao.getAll()

    suspend fun findById(id: UUID): Note? = dao.findById(id)


    suspend fun save(note: Note) {
        dao.save(note)
    }

    fun remove(id: String): Boolean {
        TODO()
    }

}