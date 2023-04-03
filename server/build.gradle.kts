import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.4.32"
}

group = "com.example"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")

    // Auto Documentation
    implementation("org.springdoc:springdoc-openapi-ui:1.6.8")

    // Scraping
    implementation("org.seleniumhq.selenium:selenium-java:4.1.4")
    implementation("org.jsoup:jsoup:1.14.3")


    //JSON Objects
    implementation("com.google.code.gson:gson:2.10.1")

    //DB
    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("mysql:mysql-connector-java:8.0.32")

    //CSV
    implementation("com.opencsv:opencsv:5.6")

    //API
    implementation("com.konghq:unirest-java:3.14.2")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.reactivestreams:reactive-streams:1.0.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")




    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("com.ninja-squad:springmockk:4.0.2")



}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
