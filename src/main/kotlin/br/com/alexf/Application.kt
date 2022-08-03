package br.com.alexf

import br.com.alexf.database.DatabaseFactory
import br.com.alexf.database.dao.NotesDaoImpl
import br.com.alexf.models.Note
import io.ktor.server.application.*
import br.com.alexf.plugins.*
import br.com.alexf.repositories.NoteRepository
import io.ktor.server.thymeleaf.*
import kotlinx.coroutines.runBlocking
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init()
    configureThymeleaf()
    configureSerialization()
    configureRouting(NoteRepository())
}

