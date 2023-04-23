val hopliteVersion: String by rootProject

plugins {
    id("caju.kotlin-common-conventions")
    application
}

dependencies {
    implementation( "com.sksamuel.hoplite:hoplite-core:$hopliteVersion")
    implementation( "com.sksamuel.hoplite:hoplite-hocon:$hopliteVersion")
}