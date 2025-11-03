dependencies {
    implementation(project(":marketplace-common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.5.6");
//    implementation("org.postgresql:postgresql:42.7.4");
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.6")
//    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.projectlombok:lombok:1.18.36");
    annotationProcessor("org.projectlombok:lombok:1.18.36");
    implementation("org.mapstruct:mapstruct:1.5.5.Final");
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final");
    implementation("org.springframework.kafka:spring-kafka:3.3.10")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-mail
    implementation("org.springframework.boot:spring-boot-starter-mail:3.5.6")

    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    // https://mvnrepository.com/artifact/io.zipkin.reporter2/zipkin-reporter-brave
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.opentelemetry:opentelemetry-exporter-zipkin")
}