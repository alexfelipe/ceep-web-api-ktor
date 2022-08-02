package br.com.alexf.database.dao

import br.com.alexf.database.dbQuery
import br.com.alexf.models.Note
import br.com.alexf.models.NoteEntity
import br.com.alexf.models.NoteEntity.id
import br.com.alexf.models.NoteEntity.message
import br.com.alexf.models.NoteEntity.title
import org.jetbrains.exposed.sql.*
import java.util.UUID

interface NotesDao {
    suspend fun getAll(): List<Note>
    suspend fun save(note: Note)
    suspend fun findById(id: UUID): Note?
}

object NotesDaoImpl : NotesDao {

    override suspend fun getAll(): List<Note> =
        dbQuery {
            NoteEntity.selectAll().map { row ->
                row.toNote()
            }
        }

    override suspend fun save(note: Note): Unit =
        dbQuery {
            NoteEntity.insert {
                it[id] = note.id
                it[title] = note.title
                it[message] = note.message
            }
        }

    override suspend fun findById(id: UUID): Note? =
        dbQuery {
            NoteEntity.select {
                NoteEntity.id eq id
            }.map {
                it.toNote()
            }.singleOrNull()
        }

    private fun ResultRow.toNote() = Note(
        id = this[id],
        title = this[title],
        message = this[message],
    )
}

