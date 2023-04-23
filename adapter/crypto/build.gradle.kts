val koinVersion: String by rootProject
val argonVersion: String by rootProject

plugins {
    id("caju.kotlin-library-conventions")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":shared"))

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("de.mkammerer:argon2-jvm:$argonVersion")
}