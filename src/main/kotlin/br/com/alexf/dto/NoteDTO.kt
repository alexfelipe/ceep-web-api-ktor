package br.com.alexf.dto

import br.com.alexf.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class NoteResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val title: String,
    val message: String
)

@Serializable
class NoteRequest(
    val title: String,
    val message: String
)