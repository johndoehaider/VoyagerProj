import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("androidx.compose.material:material:1.0.0")
            implementation("androidx.compose.ui:ui-tooling-preview:1.0.0")
            implementation("androidx.compose.runtime:runtime-livedata:1.0.0")
            implementation("androidx.activity:activity-compose:1.3.0")
            implementation("androidx.compose.ui:ui:1.0.0")
            implementation("io.ktor:ktor-client-core:2.2.4")
            implementation("io.ktor:ktor-client-cio:2.2.4")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")



        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
            val voyagerVersion = "1.1.0-beta02"
            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
            implementation("io.ktor:ktor-client-core:2.2.4")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
            implementation("io.ktor:ktor-client-core:2.0.0")
            implementation("io.ktor:ktor-client-cio:2.0.0")
            implementation("io.ktor:ktor-client-serialization:2.0.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

        }
    }
}

android {
    namespace = "voyager.proj"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "voyager.proj"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
dependencies {
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.datastore.core.jvm)
}

