package ru.otus.otuskotlin.marketplace.mappers.v1

import kotlinx.datetime.Instant
import ru.otus.kotlin.shadrin.notebook.api.v1.models.Error
import ru.otus.kotlin.shadrin.notebook.api.v1.models.IResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteCreateResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDeleteResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteListResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NotePermissions
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteReadResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteResponseObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteRestoreResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteSearchResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteUpdateResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.ResponseResult
import ru.otus.otuskotlin.marketplace.common.NONE
import ru.otus.otuskotlin.marketplace.common.NoteContext
import ru.otus.otuskotlin.marketplace.common.exceptions.UnknownNoteCommand
import ru.otus.otuskotlin.marketplace.common.models.Note
import ru.otus.otuskotlin.marketplace.common.models.NoteCategory
import ru.otus.otuskotlin.marketplace.common.models.NoteCommand
import ru.otus.otuskotlin.marketplace.common.models.NoteError
import ru.otus.otuskotlin.marketplace.common.models.NoteId
import ru.otus.otuskotlin.marketplace.common.models.NoteLock
import ru.otus.otuskotlin.marketplace.common.models.NotePermissionClient
import ru.otus.otuskotlin.marketplace.common.models.NoteState

fun NoteContext.toTransportNote(): IResponse = when (val cmd = command) {
    NoteCommand.CREATE -> toTransportCreate()
    NoteCommand.READ -> toTransportRead()
    NoteCommand.UPDATE -> toTransportUpdate()
    NoteCommand.DELETE -> toTransportDelete()
    NoteCommand.RESTORE -> toTransportRestore()
    NoteCommand.SEARCH -> toTransportSearch()
    NoteCommand.LIST -> toTransportList()
    NoteCommand.NONE -> throw UnknownNoteCommand(cmd)
}

fun NoteContext.toTransportCreate() = NoteCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote(),
)

fun NoteContext.toTransportRead() = NoteReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun NoteContext.toTransportUpdate() = NoteUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun NoteContext.toTransportDelete() = NoteDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun NoteContext.toTransportRestore() = NoteRestoreResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    note = noteResponse.toTransportNote()
)

fun NoteContext.toTransportSearch() = NoteSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    notes = notesResponse.toTransportNote()
)

fun NoteContext.toTransportList() = NoteListResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    notes = notesResponse.toTransportNote()
)

fun List<Note>.toTransportNote(): List<NoteResponseObject>? = this
    .map { it.toTransportNote() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun Note.toTransportNote(): NoteResponseObject = NoteResponseObject(
    id = id.toTransportNote(),
    title = title.takeIf { it.isNotBlank() },
    content = content.takeIf { it.isNotBlank() },
    category = category.toTransportNote(),
    reminderAt = reminderAt.toTransportNoteTime(),
    updatedAt = updatedAt.toTransportNoteTime(),
    permissions = permissionsClient.toTransportNote(),
)

internal fun NoteId.toTransportNote() = takeIf { it != NoteId.NONE }?.asString()
internal fun Instant?.toTransportNoteTime(): String? = this?.takeIf { it != Instant.NONE }?.toString()
internal fun NoteCategory.toTransportNote(): String = this.asString()
internal fun NoteLock.toTransportNote() = takeIf { it != NoteLock.NONE }?.asString()

private fun Set<NotePermissionClient>.toTransportNote(): Set<NotePermissions>? = this
    .map { it.toTransportNote() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun NotePermissionClient.toTransportNote() = when (this) {
    NotePermissionClient.READ -> NotePermissions.READ
    NotePermissionClient.UPDATE -> NotePermissions.UPDATE
    NotePermissionClient.DELETE -> NotePermissions.DELETE
}

private fun List<NoteError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun NoteError.toTransport() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun NoteState.toResult(): ResponseResult? = when (this) {
    NoteState.RUNNING -> ResponseResult.SUCCESS
    NoteState.FAILING -> ResponseResult.ERROR
    NoteState.FINISHING -> ResponseResult.SUCCESS
    NoteState.NONE -> null
}
