plugins {
    kotlin("android")
    id("com.android.application")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}
android {
    buildFeatures {
        viewBinding = true
    }
    compileSdk = 32
    defaultConfig {
        applicationId = "com.assignment.todayweather"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}
val kotlinVersion: String by project
val koinVersion: String by project
val okhttpVersion: String by project
val naviVersion: String by project
val retrofitVersion: String by project
val androidCoreVersion: String by project
val appcompatVersion: String by project
val materialVersion: String by project
val gsonVersion: String by project
val kotlinDatetimeVersion: String by project
val constraintLayoutVersion: String by project
val picassoVersion: String by project
val dexterVersion: String by project
dependencies {
    implementation("androidx.core:core-ktx:$androidCoreVersion")
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$naviVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$naviVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:$okhttpVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinDatetimeVersion")
    implementation("com.squareup.picasso:picasso:$picassoVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")
    implementation("io.insert-koin:koin-test-junit4:$koinVersion")
    implementation("io.insert-koin:koin-test:$koinVersion")
    implementation("com.karumi:dexter:$dexterVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    implementation("com.google.android.gms:play-services-location:19.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
