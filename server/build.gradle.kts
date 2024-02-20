plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "wiki.zyue"
version = "1.0.0"
application {
    mainClass.set("wiki.zyue.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    api(libs.slf4j)
    api(libs.tinylog)
    api(libs.tinylog.impl)
    api(libs.tinylog.slf4j)
}