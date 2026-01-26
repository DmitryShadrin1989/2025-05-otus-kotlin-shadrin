package ru.otus.otuskotlin.marketplace.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class NoteRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = NoteRequestId("")
    }
}