plugins {
    id("java")
    id("application")
    id("com.gradleup.shadow") version "9.3.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.googlecode.lanterna:lanterna:3.2.0-alpha1")
    implementation("net.java.dev.jna:jna:5.18.1")
    implementation("net.java.dev.jna:jna-platform:5.18.1")
}

application {
    mainClass.set("Main")
}