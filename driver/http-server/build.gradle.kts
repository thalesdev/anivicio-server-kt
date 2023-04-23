val ktorVersion: String by rootProject
val kotlinVersion: String by rootProject
val logbackVersion: String by rootProject
val exposedVersion: String by rootProject
val h2Version: String by rootProject
val hikariVersion: String by rootProject
val koinVersion: String by rootProject
val valiKtorVersion: String by rootProject

plugins {
    id("caju.kotlin-application-conventions")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
    id("io.ktor.plugin") version "2.3.0"
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-caching-headers-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-compression-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")
    implementation("io.ktor:ktor-server-websockets-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cio-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")

    implementation("org.valiktor:valiktor-core:$valiKtorVersion")

    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(project(":adapter:mysql"))
    implementation(project(":adapter:crypto"))

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")

}


////application {
////    mainClass.set("io.ktor.server.netty.EngineMain")
//
////    val isDevelopment: Boolean = project.ext.has("development")
////    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
////}
