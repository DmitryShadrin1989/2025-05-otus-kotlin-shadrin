import kotlinx.datetime.Instant
import org.junit.Test
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDebug
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDeleteRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDeleteResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteRequestDebugMode
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteRequestDebugStubs
import ru.otus.otuskotlin.marketplace.common.NONE
import ru.otus.otuskotlin.marketplace.common.NoteContext
import ru.otus.otuskotlin.marketplace.common.models.NoteCategory
import ru.otus.otuskotlin.marketplace.common.models.NoteCommand
import ru.otus.otuskotlin.marketplace.common.models.NoteError
import ru.otus.otuskotlin.marketplace.common.models.NoteRequestId
import ru.otus.otuskotlin.marketplace.common.models.NoteState
import ru.otus.otuskotlin.marketplace.common.models.NoteUserId
import ru.otus.otuskotlin.marketplace.common.models.NoteWorkMode
import ru.otus.otuskotlin.marketplace.common.stubs.NoteStubs
import ru.otus.otuskotlin.marketplace.mappers.v1.fromTransport
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportDeleteNote
import ru.otus.otuskotlin.marketplace.mappers.v1.toTransportNote
import ru.otus.otuskotlin.marketplace.stubs.NoteStub
import kotlin.test.assertEquals

class NoteDeleteRequestMapperTest {

    @Test
    fun fromTransport() {
        val req = NoteDeleteRequest(
            debug = NoteDebug(
                mode = NoteRequestDebugMode.STUB,
                stub = NoteRequestDebugStubs.SUCCESS,
            ),
            note = NoteStub.get().toTransportDeleteNote()
        )
        val expected = NoteStub.prepareResult {
            userId = NoteUserId.NONE
            createdAt = Instant.NONE
            updatedAt = Instant.NONE
            reminderAt = Instant.NONE
            title = ""
            content = ""
            category = NoteCategory.NONE
            permissionsClient.clear()
        }

        val context = NoteContext()
        context.fromTransport(req)

        assertEquals(NoteStubs.SUCCESS, context.stubCase)
        assertEquals(NoteWorkMode.STUB, context.workMode)
        assertEquals(expected, context.noteRequest)
    }

    @Test
    fun toTransport() {
        val context = NoteContext(
            requestId = NoteRequestId("1234"),
            command = NoteCommand.DELETE,
            noteResponse = NoteStub.get(),
            errors = mutableListOf(
                NoteError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = NoteState.RUNNING,
        )

        val req = context.toTransportNote() as NoteDeleteResponse

        assertEquals(req.note, NoteStub.get().toTransportNote())
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}