package br.com.alexf.plugins

import br.com.alexf.models.Note
import br.com.alexf.repositories.NoteRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import java.util.*

fun Application.configureRouting(
    repository: NoteRepository = NoteRepository()
) {
    routing {
        get("/") {
            call.respond(
                ThymeleafContent("index", emptyMap())
            )
        }
        route("/notes") {
            get {
                val notes = repository.getAll()
                if (notes.isNotEmpty()) {
                    call.respond(notes)
                } else {
                    call.respondText(
                        "No notes found",
                        status = HttpStatusCode.OK
                    )
                }
            }
            get("{id?}") {
                val id: UUID = UUID.fromString(call.parameters["id"])
                    ?: return@get call.respondText(
                        "Missing id",
                        status = HttpStatusCode.BadRequest
                    )
                val foundNote = repository.findById(id)
                    ?: return@get call.respondText(
                        "No note with id: $id found",
                        status = HttpStatusCode.NotFound
                    )
                call.respond(
                    status = HttpStatusCode.OK,
                    message = foundNote
                )
            }
            post {
                val note = call.receive<Note>()
                repository.save(note)
                call.respondText(
                    "Note stored correctly",
                    status = HttpStatusCode.Created
                )
            }
            delete("{id?}") {
                val id = UUID.fromString(call.parameters["id"])
                    ?: return@delete call.respond(
                        HttpStatusCode.BadRequest
                    )
                if (repository.delete(id)) {
                    call.respondText(
                        "Note removed correctly",
                        status = HttpStatusCode.Accepted
                    )
                } else {
                    call.respondText(
                        "Not found",
                        status = HttpStatusCode.NotFound
                    )
                }
            }
        }
    }
}
