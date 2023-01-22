import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    id("org.jetbrains.compose") version "1.2.1"
    id("org.jetbrains.kotlin.kapt") version "1.7.20"
    application
}

group = "ru.student.distribution"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven{
        url = uri("https://jitpack.io")
    }
}

val daggerVersion by extra("2.44.2")

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))

    implementation(compose.desktop.currentOs)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation("com.google.code.gson:gson:2.10")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    api("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")

    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.xerial:sqlite-jdbc:3.40.0.0")
    implementation("mysql:mysql-connector-java:8.0.30")

    implementation("org.apache.poi:poi-ooxml:5.2.2")
    implementation("com.grapecity.documents:gcexcel:5.0.3")

    implementation("com.github.mal1s:algorithm-student-distribution:1.1.3")
}

kapt {
    arguments {
        arg("kotlite.db.qualifiedName", "ru.student.distribution.DB")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}