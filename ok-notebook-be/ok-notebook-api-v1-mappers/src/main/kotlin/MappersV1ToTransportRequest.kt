package ru.otus.otuskotlin.marketplace.mappers.v1

import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteCreateObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDeleteRestoreObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteReadObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteUpdateObject
import ru.otus.otuskotlin.marketplace.common.models.Note


fun Note.toTransportCreateNote() = NoteCreateObject(
    title = title,
    content = content,
    category = category.toTransportNote(),
    reminderAt = reminderAt.toTransportNoteTime(),
)

fun Note.toTransportReadNote() = NoteReadObject(
    id = id.toTransportNote()
)

fun Note.toTransportUpdateNote() = NoteUpdateObject(
    title = title,
    content = content,
    category = category.toTransportNote(),
    reminderAt = reminderAt.toTransportNoteTime(),
    lock = lock.toTransportNote(),
)

fun Note.toTransportDeleteNote() = NoteDeleteRestoreObject(
    id = id.toTransportNote(),
    lock = lock.toTransportNote(),
)