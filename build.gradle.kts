
buildscript {
    val kotlinVersion: String by project
    val naviVersion: String by project
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath("com.android.tools.build:gradle:7.1.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$naviVersion")
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

