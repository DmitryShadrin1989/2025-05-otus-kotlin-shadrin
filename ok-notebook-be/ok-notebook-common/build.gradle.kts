plugins {
    id("build-jvm")
}

kotlin {
    sourceSets {
        val coroutinesVersion: String by project
        main {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            }
        }
        test {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

    }
}