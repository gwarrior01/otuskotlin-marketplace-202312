plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":api-v1-kmp"))
    implementation(project(":exporter-common"))

    testImplementation(kotlin("test-junit"))
}