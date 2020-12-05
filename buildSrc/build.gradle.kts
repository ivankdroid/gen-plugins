import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    `java-gradle-plugin`
    id("com.jfrog.bintray") version "1.8.5"
}
buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.5")
    }
}

repositories {
    jcenter()
    google()
}

gradlePlugin {
    plugins {
        register("yml-strings-plugin") {
            id = "yml-strings-plugin"
            implementationClass = "io.kit.gen.yml_strings.YmlStringsPlugin"
        }
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.4"
}

dependencies {
    implementation("com.android.tools.build:gradle:4.1.1")
    implementation("com.android.tools.build:gradle-api:4.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.20")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    implementation("org.yaml:snakeyaml:1.19")
}


bintray {
    user = "kuzloivan"
    key = "b72e6a0943ecf804c79fc662dc9e11999b3ba382"
    pkg.repo = "maven"
    pkg.name = "yml-strings"
}

