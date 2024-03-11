import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    jvmToolchain(17)
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
       browser()
    }
    
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework("Shared") {
            // Make AppleSettings visible from Swift
            export(libs.settings.noarg)
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            transitiveExport = true
        }
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.kmp.common)
        }
        commonTest.dependencies {
            api(libs.kotlin.test.junit)
            api(libs.kotlinx.coroutines.test)
        }
        jvmMain.dependencies {
            api(libs.app.dirs)
        }
        jvmTest.dependencies {
            api(libs.mockk)
        }

        listOf(jvmMain, nativeMain, androidMain)
            .forEach {
                it.dependencies {
                    api(libs.settings.serialization)
                }
            }
    }
}

android {
    namespace = "wiki.zyue.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
