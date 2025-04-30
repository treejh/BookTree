plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
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
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    //implementation ("org.springframework.boot:spring-boot-starter-validation")

    //implementation("org.springframework.boot:spring-boot-starter-validation")

    //데이터 베이스 관련 의존성
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    //swagger 의존성
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

    //S3 의존성
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")


    //시큐리티 의존성
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.2.RELEASE")


    //jwt 의존성
    // JWT & JSON
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Gson - JSON 메시지를 다루기 위한 라이브러리
    implementation("com.google.code.gson:gson")


    //Oauth2
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // Redis 의존성
    // implementation ("org.springframework.boot:spring-boot-starter-data-redis")

    //무중단 배포 의존성 추가 ( get 요청 )
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //이메일 의존성 추가
    implementation("org.springframework.boot:spring-boot-starter-mail")


    //// thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")

}

//tasks.withType<Test> {
//    useJUnitPlatform()
//}
