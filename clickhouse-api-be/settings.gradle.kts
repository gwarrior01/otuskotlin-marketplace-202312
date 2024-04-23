rootProject.name = "clickhouse-api-be"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(":api-v1-kmp")
include(":api-v1-mappers")
include(":exporter-common")
include(":exporter-app-common")
include(":exporter-ktor")
include(":business-logic")
include(":exporter-stubs")