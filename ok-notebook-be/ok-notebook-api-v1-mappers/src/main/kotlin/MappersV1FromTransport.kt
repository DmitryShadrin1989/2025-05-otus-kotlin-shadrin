package ru.otus.otuskotlin.marketplace.mappers.v1

import kotlinx.datetime.Instant
import ru.otus.kotlin.shadrin.notebook.api.v1.models.IRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteCreateObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteCreateRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDebug
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDeleteRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDeleteRestoreObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteListFilter
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteListRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteReadObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteReadRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteRequestDebugMode
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteRequestDebugStubs
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteRestoreRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteSearchFilter
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteSearchRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteUpdateObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteUpdateRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.PageRequest
import ru.otus.otuskotlin.marketplace.common.NONE
import ru.otus.otuskotlin.marketplace.common.NoteContext
import ru.otus.otuskotlin.marketplace.common.models.Note
import ru.otus.otuskotlin.marketplace.common.models.NoteCategory
import ru.otus.otuskotlin.marketplace.common.models.NoteCommand
import ru.otus.otuskotlin.marketplace.common.models.NoteFilter
import ru.otus.otuskotlin.marketplace.common.models.NoteId
import ru.otus.otuskotlin.marketplace.common.models.NoteLock
import ru.otus.otuskotlin.marketplace.common.models.NotePage
import ru.otus.otuskotlin.marketplace.common.models.NoteWorkMode
import ru.otus.otuskotlin.marketplace.common.stubs.NoteStubs
import ru.otus.otuskotlin.marketplace.mappers.v1.exceptions.UnknownRequestClass

fun NoteContext.fromTransport(request: IRequest) = when (request) {
    is NoteCreateRequest -> fromTransport(request)
    is NoteReadRequest -> fromTransport(request)
    is NoteUpdateRequest -> fromTransport(request)
    is NoteDeleteRequest -> fromTransport(request)
    is NoteRestoreRequest -> fromTransport(request)
    is NoteSearchRequest -> fromTransport(request)
    is NoteListRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

fun NoteContext.fromTransport(request: NoteCreateRequest) {
    command = NoteCommand.CREATE
    noteRequest = request.note?.toInternal() ?: Note()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun NoteContext.fromTransport(request: NoteReadRequest) {
    command = NoteCommand.READ
    noteRequest = request.note.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun NoteContext.fromTransport(request: NoteUpdateRequest) {
    command = NoteCommand.UPDATE
    noteRequest = request.note?.toInternal() ?: Note()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun NoteContext.fromTransport(request: NoteDeleteRequest) {
    command = NoteCommand.DELETE
    noteRequest = request.note.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun NoteContext.fromTransport(request: NoteRestoreRequest) {
    command = NoteCommand.RESTORE
    noteRequest = request.note.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun NoteContext.fromTransport(request: NoteSearchRequest) {
    command = NoteCommand.SEARCH
    noteFilterRequest = request.noteFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun NoteContext.fromTransport(request: NoteListRequest) {
    command = NoteCommand.LIST
    noteFilterRequest = request.filter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    notePageRequest = request.page.toInternal()
}

private fun String?.toNoteId() = this?.let { NoteId(it) } ?: NoteId.NONE
private fun String?.toNoteLock() = this?.let { NoteLock(it) } ?: NoteLock.NONE
private fun String?.toNoteCategory() = this?.let { NoteCategory(it) } ?: NoteCategory.NONE
private fun String?.toNoteDateTime(): Instant = this?.toInstantOrNone() ?: Instant.NONE

private fun String.toInstantOrNone(): Instant = try {
    Instant.parse(this)
} catch (e: IllegalArgumentException) {
    Instant.NONE
}

private fun NoteDebug?.transportToWorkMode(): NoteWorkMode = when (this?.mode) {
    NoteRequestDebugMode.PROD -> NoteWorkMode.PROD
    NoteRequestDebugMode.TEST -> NoteWorkMode.TEST
    NoteRequestDebugMode.STUB -> NoteWorkMode.STUB
    null -> NoteWorkMode.PROD
}

private fun NoteDebug?.transportToStubCase(): NoteStubs = when (this?.stub) {
    NoteRequestDebugStubs.SUCCESS -> NoteStubs.SUCCESS
    NoteRequestDebugStubs.NOT_FOUND -> NoteStubs.NOT_FOUND
    NoteRequestDebugStubs.BAD_ID -> NoteStubs.BAD_ID
    NoteRequestDebugStubs.BAD_TITLE -> NoteStubs.BAD_TITLE
    NoteRequestDebugStubs.BAD_CONTENT -> NoteStubs.BAD_CONTENT
    NoteRequestDebugStubs.CANNOT_DELETE -> NoteStubs.CANNOT_DELETE
    NoteRequestDebugStubs.CANNOT_UPDATE -> NoteStubs.CANNOT_UPDATE
    NoteRequestDebugStubs.CANNOT_CREATE -> NoteStubs.CANNOT_CREATE
    NoteRequestDebugStubs.BAD_SEARCH_STRING -> NoteStubs.BAD_SEARCH_STRING
    null -> NoteStubs.NONE
}

private fun NoteCreateObject.toInternal(): Note = Note(
    category = this.category.toNoteCategory(),
    title = this.title ?: "",
    content = this.content ?: "",
    reminderAt = this.reminderAt.toNoteDateTime(),
)

private fun NoteReadObject?.toInternal(): Note = if (this != null) {
    Note(id = id.toNoteId())
} else {
    Note()
}

private fun NoteUpdateObject.toInternal(): Note = Note(
    id = this.id.toNoteId(),
    category = this.category.toNoteCategory(),
    title = this.title ?: "",
    content = this.content ?: "",
    reminderAt = this.reminderAt.toNoteDateTime(),
    lock = lock.toNoteLock(),
)

private fun NoteDeleteRestoreObject?.toInternal(): Note = if (this != null) {
    Note(
        id = id.toNoteId(),
        lock = lock.toNoteLock(),
    )
} else {
    Note()
}

private fun NoteSearchFilter?.toInternal(): NoteFilter = NoteFilter(
    searchString = this?.searchString ?: "",
    category = this?.category.toNoteCategory()
)

private fun NoteListFilter?.toInternal(): NoteFilter = NoteFilter(
    category = this?.category.toNoteCategory(),
    isDeleted = this?.isDeleted ?: false,
)

private fun PageRequest?.toInternal(): NotePage = NotePage(
    page = this?.page ?: 1,
    propertySize = this?.propertySize ?: 10,
)