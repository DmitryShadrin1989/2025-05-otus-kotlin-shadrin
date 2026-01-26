package ru.otus.otuskotlin.marketplace.stubs

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.marketplace.common.models.*

object NoteStubExample {
    val MY_NOTE1: Note
        get() = Note(
            id = NoteId("note-1"),
            userId = NoteUserId("user-1"),
            category = NoteCategory("home"),
            title = "Home Shopping",
            content = """
                Milk 2 liters
                Bread 2 pieces
                Eggs 10 pieces
            """.trimIndent(),
            isDeleted = false,
            reminderAt = Instant.parse("2026-01-02T12:00:00Z"),
            createdAt = Instant.parse("2026-01-01T12:00:00Z"),
            updatedAt = Instant.parse("2026-01-01T12:00:00Z"),
            lock = NoteLock("123"),
            permissionsClient = mutableSetOf(
                NotePermissionClient.READ,
                NotePermissionClient.UPDATE,
                NotePermissionClient.DELETE,
            )
        )

    val MY_NOTE2 = MY_NOTE1.copy(
        id = NoteId("note-2"),
        category = NoteCategory("summer house"),
        reminderAt = Instant.parse("2026-01-03T12:00:00Z"),
    )
}
