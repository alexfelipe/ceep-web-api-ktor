package br.com.alura.modules

import br.com.alura.models.toNoteResponse
import br.com.alura.requests.NoteRequest
import br.com.alura.requests.toNote
import br.com.alura.services.NoteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureNoteRouting(
    service: NoteService
) {
    routing {
        get("/notes") {
            val response = service.findAll().map {
                it.toNoteResponse()
            }
            call.respond(HttpStatusCode.OK, response)
        }
        post("/notes") {
            val note = call.receive<NoteRequest>().toNote()
            val response = service.create(note).toNoteResponse()
            call.respond(HttpStatusCode.Created, response)
        }
        get("/notes/{id}") {
            val id = UUID.fromString(call.parameters["id"])
            service.findById(id)?.let { note ->
                val response = note.toNoteResponse()
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
        put("/notes/{id}") {
            val id = UUID.fromString(call.parameters["id"])
            val note = call.receive<NoteRequest>().toNote(id)
            service.update(note)
            val response = note.toNoteResponse()
            call.respond(HttpStatusCode.OK, response)
        }
        delete("/notes/{id}") {
            val id = UUID.fromString(call.parameters["id"])
            service.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}

