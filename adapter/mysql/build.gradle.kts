val exposedVersion: String by rootProject
val koinVersion: String by rootProject
val mysqlConnectorVersion: String by rootProject
val flywayVersion: String by rootProject

plugins {
    id("caju.kotlin-library-conventions")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
    id("org.flywaydb.flyway") version "7.15.0"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":shared"))

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("mysql:mysql-connector-java:$mysqlConnectorVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")

}

flyway {
    url = "jdbc:mysql://localhost:3306/anivicio"
    user = "root"
    driver = "com.mysql.cj.jdbc.Driver"
    password = "root"
    schemas = arrayOf("anivicio")
    locations = arrayOf("filesystem:src/main/resources/migrations")
}
