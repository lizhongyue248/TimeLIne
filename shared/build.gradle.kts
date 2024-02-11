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
            implementation(libs.slf4j)
            implementation(libs.tinylog)
            implementation(libs.tinylog.impl)
            implementation(libs.tinylog.slf4j)
            implementation(libs.apache.commons)
            implementation(libs.app.dirs)
            implementation(libs.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test.junit)
            implementation(libs.kotlinx.coroutines.test)
        }
        jvmTest.dependencies {
            implementation(libs.mockk)
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
