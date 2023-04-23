val koinVersion: String by rootProject

plugins {
    id("caju.kotlin-common-conventions")
}

dependencies {
    implementation(project(":shared"))
    implementation("io.insert-koin:koin-core:$koinVersion")
}