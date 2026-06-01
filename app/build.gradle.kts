import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    // Default
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Kapt
    kotlin("kapt")

    // Ktor
    kotlin("plugin.serialization") version "2.2.0"

    // Hilt
    alias(libs.plugins.dagger.hilt.android)
    //alias(libs.plugins.dagger.hilt.android)
    //alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.drake.droidblox"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.drake.droidblox"
        minSdk = 27
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    buildFeatures {
        aidl = true
        compose = true
        buildConfig = true
    }
    // by dagger for adding @ApplicationContext
//    kotlinOptions {
//        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
//    }
}

dependencies {
    // DroidBlox Core

    // Projects
    implementation(project(":aidl"))
    implementation(project(":logger"))

    // AndroidX
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.documentfile)
    implementation(libs.androidx.hilt)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Apktool
    implementation(files("libs/apktool.jar"))
    
    // Coil3
    implementation(libs.coil3.compose)
    implementation(libs.coil3.network.ktor3)

    // Dagger Hilt
    implementation(libs.dagger.hilt.android)
    androidTestImplementation(libs.dagger.hilt.android.testing)
    testImplementation(libs.dagger.hilt.android.testing)

    kapt(libs.dagger.hilt.compiler)
    kaptAndroidTest(libs.dagger.hilt.compiler)
    kaptTest(libs.dagger.hilt.compiler)

    // Google Accompanist
    implementation(libs.accompanist.navigation.animation)

    // Kizzy
    implementation(libs.kizzyRPC)

    // Ktor
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.websockets)
    implementation(libs.ktor3.client.content.negotiation)
    implementation(libs.ktor3.serialization.kotlinx.json)
    implementation(libs.org.jetbrains.kotlin.plugin.serialization.gradle.plugin)

    // Javax
    implementation(libs.javax)

    // Junit
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)

    // Robolectric
    testImplementation(libs.robolectric)
}

kapt {
    correctErrorTypes = true
}

// https://stackoverflow.com/a/79612057
//hilt {
//    enableAggregatingTask = false
//}