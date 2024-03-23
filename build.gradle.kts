plugins {
	`java-library`
	`maven-publish`
	checkstyle
	id("com.diffplug.spotless") version "6.25.0"
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
    testImplementation("org.mockito:mockito-core:5.11.0")
		testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.openjdk.jmh:jmh-core:1.37")
		testImplementation("com.google.guava:guava:33.1.0-jre")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

spotless {
  java {
    googleJavaFormat("1.20.0").aosp().reflowLongStrings().skipJavadocFormatting()
    formatAnnotations()
    trimTrailingWhitespace()
    indentWithTabs()
    endWithNewline()
  }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.theoparis"
            artifactId = "brigadier"
            version = "1.1.9"

            from(components["java"])
        }
    }
}
