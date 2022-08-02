package br.com.alexf.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val message: String
)