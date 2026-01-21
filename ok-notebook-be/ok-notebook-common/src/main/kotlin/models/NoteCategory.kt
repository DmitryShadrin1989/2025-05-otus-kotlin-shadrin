package ru.otus.otuskotlin.marketplace.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class NoteCategory(private val name: String) {
    fun asString() = name

    companion object {
        val NONE = NoteCategory("")
    }
}