plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.okNotebookApiV1Jackson)
    implementation(projects.okNotebookCommon)

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.okNotebookStubs)
}
