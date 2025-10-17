dependencies {
    implementation(project(":marketplace-common"))
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.6")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
    implementation("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    implementation("org.mapstruct:mapstruct:1.6.3")

    implementation("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    implementation("com.h2database:h2:2.3.232")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.6")
    implementation("org.springframework.boot:spring-boot-starter-security:3.5.6")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation
    implementation("org.springframework.boot:spring-boot-starter-validation:3.5.6")
    // https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")

    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api
//    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.0")
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
//    testImplementation("org.mockito:mockito-core:5.20.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.5.6")

}

tasks.test {
    useJUnitPlatform()
}