package ru.otus.otuskotlin.marketplace.common.models

data class NoteFilter(
    var searchString: String = "",
    var category: NoteCategory = NoteCategory.NONE,
    var isDeleted: Boolean = false,
)