package br.com.alexf.database.entities

import org.jetbrains.exposed.sql.Table

object NoteEntity : Table("notes") {
    val id = uuid("id")
    val title = varchar("title", 100)
    val message = text("message")
    override val primaryKey = PrimaryKey(id)
}