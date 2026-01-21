package ru.otus.otuskotlin.marketplace.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.models.*
import ru.otus.otuskotlin.marketplace.common.stubs.NoteStubs

data class NoteContext(
    var command: NoteCommand = NoteCommand.NONE,
    var state: NoteState = NoteState.NONE,
    val errors: MutableList<NoteError> = mutableListOf(),

    var workMode: NoteWorkMode = NoteWorkMode.PROD,
    var stubCase: NoteStubs = NoteStubs.NONE,

    var requestId: NoteRequestId = NoteRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var noteRequest: Note = Note(),
    var noteFilterRequest: NoteFilter = NoteFilter(),
    var notePageRequest: NotePage = NotePage(),

    var noteResponse: Note = Note(),
    var notesResponse: MutableList<Note> = mutableListOf(),
)
