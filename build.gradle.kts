
plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation("org.mindrot:jbcrypt:0.4")
}

application {
    mainClass.set("controller.ui.PageRankApp")
}