import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.compose") version "1.4.0"
    id("org.jetbrains.kotlin.kapt") version "1.8.0"
    id("io.realm.kotlin") version "1.8.0"
}

group = "ru.student.distribution"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven{
        url = uri("https://jitpack.io")
    }
}

val daggerVersion by extra("2.44.2")

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))

    implementation(compose.desktop.currentOs)

    implementation("org.jetbrains.compose.material3:material3-desktop:1.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    api("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    implementation("com.github.mal1s:algorithm-student-distribution:2.0.2")

    implementation("io.realm.kotlin:library-sync:1.8.0")
    implementation("io.realm.kotlin:library-base:1.8.0")

    implementation("br.com.devsrsouza.compose.icons.jetbrains:font-awesome:1.0.0")
    implementation("br.com.devsrsouza.compose.icons.jetbrains:simple-icons:1.0.0")
    implementation("br.com.devsrsouza.compose.icons.jetbrains:octicons:1.0.0")
    implementation("br.com.devsrsouza.compose.icons.jetbrains:eva-icons:1.0.0")
    implementation("br.com.devsrsouza.compose.icons:tabler-icons:1.1.0")

    implementation("com.darkrockstudios:mpfilepicker:1.1.0")

    implementation("com.fasterxml.jackson.core:jackson-core:2.15.0")

    implementation("org.apache.poi:poi-ooxml:5.2.2")
    implementation("org.apache.commons:commons-csv:1.10.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.grapecity.documents:gcexcel:6.0.4")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.8.0")
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

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            includeAllModules = true
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi)
            packageName = "YarmarkaARM"
            packageVersion = "1.0.0"
            outputBaseDir.set(project.buildDir.resolve("E:/"))
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
            windows {
                msiPackageVersion = "1.0.0"
            }
            buildTypes.release {
                proguard {
                    configurationFiles.from("compose-desktop.pro")
                }
            }
        }
    }
}