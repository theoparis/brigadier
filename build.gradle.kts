plugins {
	`java-library`
	`maven-publish`
}

group = "com.mojang"
version =  "1.1.9"

buildscript {
    repositories {
        mavenCentral()
        maven("https://libraries.minecraft.net")
    }
}

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net")
}

dependencies {
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
