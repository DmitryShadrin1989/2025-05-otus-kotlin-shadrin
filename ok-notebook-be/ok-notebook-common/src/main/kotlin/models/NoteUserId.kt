package ru.otus.otuskotlin.marketplace.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class NoteUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = NoteUserId("")
    }
}