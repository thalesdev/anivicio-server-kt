val kotlinSerializationVersion: String by rootProject

plugins {
    id("caju.kotlin-library-conventions")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
}
