val kotestVersion: String by rootProject


plugins {
    id("org.jetbrains.kotlin.jvm")
    jacoco
}



kotlin {
    jvmToolchain(11)
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    constraints {
        implementation("org.apache.commons:commons-text:1.9")
    }

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation ("io.kotest:kotest-property:$kotestVersion")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}
