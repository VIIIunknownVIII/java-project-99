import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent


plugins {
	id ("application")
	id("jacoco")
	id("checkstyle")
	id("io.freefair.lombok") version "8.4"
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("io.sentry.jvm.gradle") version "4.11.0"
}

application {
	mainClass = "hexlet.code.AppApplication"
}

group = "hexlet.code"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
	implementation ("net.bytebuddy:byte-buddy:1.14.12")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0.Beta1")
	implementation("net.datafaker:datafaker:2.1.0")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2:2.2.224")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.instancio:instancio-junit:4.1.0")
	testImplementation(platform("org.junit:junit-bom:5.10.1"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
	testImplementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.7")

}

tasks.withType<Test> {
	useJUnitPlatform()
}


tasks.test {
	useJUnitPlatform()
	// https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
	testLogging {
		exceptionFormat = TestExceptionFormat.FULL
		events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
		// showStackTraces = true
		// showCauses = true
		showStandardStreams = true
	}
}