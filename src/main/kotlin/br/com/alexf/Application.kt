package br.com.alexf

import io.ktor.server.application.*
import br.com.alexf.plugins.*
import br.com.alexf.repositories.NoteRepository

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSerialization()
    configureRouting(NoteRepository())
}
