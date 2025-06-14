plugins {
    kotlin("jvm") version "2.1.20"
}

group = "ru.otus.kotlin.task.manager"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}