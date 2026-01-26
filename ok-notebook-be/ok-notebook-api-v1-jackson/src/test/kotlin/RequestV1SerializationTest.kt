package ru.otus.otuskotlin.marketplace.api.v1

import ru.otus.kotlin.shadrin.notebook.api.v1.models.IRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteCreateObject
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteCreateRequest
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteDebug
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteRequestDebugMode
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteRequestDebugStubs
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request = NoteCreateRequest(
        debug = NoteDebug(
            mode = NoteRequestDebugMode.STUB,
            stub = NoteRequestDebugStubs.BAD_TITLE
        ),
        note = NoteCreateObject(
            title = "note title",
            content = "note content",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"title\":\\s*\"note title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as NoteCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"note": null}
        """.trimIndent()
        val obj = apiV1Mapper.readValue(jsonString, NoteCreateRequest::class.java)

        assertEquals(null, obj.note)
    }
}
