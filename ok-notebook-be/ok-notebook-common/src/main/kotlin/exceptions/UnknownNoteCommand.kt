package ru.otus.otuskotlin.marketplace.common.exceptions

import ru.otus.otuskotlin.marketplace.common.models.NoteCommand

class UnknownNoteCommand(command: NoteCommand) : Throwable("Wrong command $command at mapping toTransport stage")