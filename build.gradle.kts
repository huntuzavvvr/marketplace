plugins {
	java
	id("org.springframework.boot") version "3.2.5" apply false
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"


java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2025.0.0"

dependencies {
//	implementation("org.springframework.cloud:spring-cloud-starter-config")
//	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
//	testImplementation("org.springframework.boot:spring-boot-starter-test")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}



tasks.withType<Test> {
	useJUnitPlatform()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
    }
    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)

            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")
        }
    }
}