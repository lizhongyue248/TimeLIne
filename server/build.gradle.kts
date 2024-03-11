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
    implementation(libs.bundles.server.ktor)
    implementation(libs.bundles.server.log)
    implementation(libs.bundles.server.utils)
    implementation(libs.bundles.server.exposed)
    implementation(kotlin("test"))
    implementation(libs.ktor.server.tests)
}
configurations.all {
    exclude(group = "ch.qos.logback")
}