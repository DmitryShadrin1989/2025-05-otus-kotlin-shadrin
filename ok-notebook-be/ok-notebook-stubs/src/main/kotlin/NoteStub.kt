package ru.otus.otuskotlin.marketplace.stubs

import ru.otus.otuskotlin.marketplace.common.models.Note
import ru.otus.otuskotlin.marketplace.stubs.NoteStubExample.MY_NOTE1

object NoteStub {
    fun get(): Note = MY_NOTE1.copy()

    fun prepareResult(block: Note.() -> Unit): Note = get().apply(block)
}
