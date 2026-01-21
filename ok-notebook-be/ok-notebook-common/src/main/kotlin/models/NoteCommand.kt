package ru.otus.otuskotlin.marketplace.common.models

enum class NoteCommand {
    NONE,
    CREATE,
    READ,
    UPDATE,
    DELETE,
    RESTORE,
    SEARCH,
    LIST,
}