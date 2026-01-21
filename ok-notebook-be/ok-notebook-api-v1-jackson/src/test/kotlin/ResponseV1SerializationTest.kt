package ru.otus.otuskotlin.marketplace.api.v1

import ru.otus.kotlin.shadrin.notebook.api.v1.models.IResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteCreateResponse
import ru.otus.kotlin.shadrin.notebook.api.v1.models.NoteResponseObject
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response = NoteCreateResponse(
        note = NoteResponseObject(
            title = "note title",
            content = "note description",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"title\":\\s*\"note title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as NoteCreateResponse

        assertEquals(response, obj)
    }
}
