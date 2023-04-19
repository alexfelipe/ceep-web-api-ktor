package br.com.alura

import br.com.alura.modules.configureNoteRouting
import br.com.alura.services.NoteService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.jetbrains.exposed.sql.Database
import java.util.UUID

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    val database = Database.connect(
        url = "jdbc:h2:file:./database/db",
        user = "root",
        driver = "org.h2.Driver",
        password = ""
    )
    val service = NoteService(database)
    configureNoteRouting(service)
}
