dependencies {
    implementation(project(":marketplace-common")   )
    implementation("org.springframework.boot:spring-boot-starter:3.5.6")
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.6")
    implementation("org.projectlombok:lombok:1.18.38")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.5.6")
    implementation("org.postgresql:postgresql:42.7.7")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")


}