import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class FirstTest {
    @Test
    fun firstTest() {
        var courseName = "Kotlin Backend Developer"
        assertEquals("This course is Kotlin Backend Developer.", "This course is ${courseName}.")

        courseName = "Spring Boot Developer"
        assertNotEquals("This course is Kotlin Backend Developer.", "This course is ${courseName}.")
    }
}