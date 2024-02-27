plugins {
	`java-library`
	`maven-publish`
}

buildscript {
    repositories {
        mavenCentral()
    }
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}



publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.mojang"
            artifactId = "brigadier"
            version = "1.1.9"

            from(components["java"])
        }
    }
}
