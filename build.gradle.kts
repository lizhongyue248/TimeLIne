plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.sqldelight) apply false
}


// Need new enough node for WASM as of Kotlin 1.9.20
//rootProject.extensions.findByType<NodeJsRootExtension>()?.apply {
//    nodeVersion = "21.0.0-v8-canary202309143a48826a08"
//    nodeDownloadBaseUrl = "https://nodejs.org/download/v8-canary"
//}
//tasks.withType<KotlinNpmInstallTask>().configureEach {
//    args.add("--ignore-engines")
//}