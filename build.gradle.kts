plugins {
    kotlin("jvm") version "1.9.0" // Use a versão mais recente do Kotlin
    application // Adicione o plugin application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5") // Dependência MQTT
    implementation("io.moquette:moquette-broker:0.15") // Broker MQTT (opcional)
    implementation("org.slf4j:slf4j-api:1.7.36") // Logging (opcional)
    implementation("org.slf4j:slf4j-simple:1.7.36") // Logging (opcional)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17) // Define a versão do Java como 17
}

application {
    mainClass.set("SubscriberKt") // Define a classe principal
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}