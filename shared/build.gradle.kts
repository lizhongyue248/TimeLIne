import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
}

kotlin {
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
    
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.slf4j)
            api(libs.tinylog)
            api(libs.tinylog.impl)
            api(libs.tinylog.slf4j)
            api(libs.apache.commons)
            api(libs.app.dirs)
            api(libs.serialization.json)
            api(libs.koin)
            api(libs.koin.compose)
            api(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            api(libs.kotlin.test.junit)
            api(libs.kotlinx.coroutines.test)
        }
        jvmTest.dependencies {
            api(libs.mockk)
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
