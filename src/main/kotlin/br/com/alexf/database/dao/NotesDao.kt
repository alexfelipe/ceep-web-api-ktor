package br.com.alexf.database.dao

import br.com.alexf.database.dbQuery
import br.com.alexf.database.entities.NoteEntity
import br.com.alexf.database.entities.NoteEntity.id
import br.com.alexf.database.entities.NoteEntity.message
import br.com.alexf.database.entities.NoteEntity.title
import br.com.alexf.models.Note
import org.jetbrains.exposed.sql.*
import java.util.*

interface NotesDao {
    suspend fun getAll(): List<Note>
    suspend fun save(note: Note)
    suspend fun findById(id: UUID): Note?
    suspend fun delete(id: UUID): Boolean
}

object NotesDaoImpl : NotesDao {

    override suspend fun getAll(): List<Note> =
        dbQuery {
            NoteEntity
                .selectAll()
                .map {
                    it.toNote()
                }
        }

    override suspend fun save(note: Note): Unit =
        dbQuery {
            NoteEntity
                .insert {
                    it[id] = note.id
                    it[title] = note.title
                    it[message] = note.message
                }
        }

    override suspend fun findById(id: UUID): Note? =
        dbQuery {
            NoteEntity
                .select {
                    NoteEntity.id eq id
                }.map {
                    it.toNote()
                }.singleOrNull()
        }

    override suspend fun delete(id: UUID): Boolean =
        dbQuery {
            NoteEntity
                .deleteWhere {
                    NoteEntity.id eq id
                } > 0
        }

    private fun ResultRow.toNote() = Note(
        id = this[id],
        title = this[title],
        message = this[message],
    )
}

