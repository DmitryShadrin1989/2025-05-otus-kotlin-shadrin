package ru.otus.otuskotlin.marketplace.common.models

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.NONE

data class Note(
    var id: NoteId = NoteId.NONE,
    var userId: NoteUserId = NoteUserId.NONE,
    var category: NoteCategory = NoteCategory.NONE,
    var title: String = "",
    var content: String = "",
    var isDeleted: Boolean = false,
    var reminderAt: Instant = Instant.NONE,
    var createdAt: Instant = Instant.NONE,
    var updatedAt: Instant = Instant.NONE,
    var lock: NoteLock = NoteLock.NONE,
    val permissionsClient: MutableSet<NotePermissionClient> = mutableSetOf(),
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Note()
    }
}