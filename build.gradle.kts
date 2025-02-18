import java.util.Properties
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.sql.Connection
import java.sql.DriverManager
import java.net.URLClassLoader
import org.gradle.internal.os.OperatingSystem
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.register
import java.io.File


plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.techpuram.sample"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
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

sourceSets {
    create("development") {
        java {
            srcDirs("src/development/java")
            resources.srcDirs("src/development/resources")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-mail") // Spring Mail Starter
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    // Jakarta Mail API and Activation API
    implementation("jakarta.mail:jakarta.mail-api:2.0.0") // Jakarta Mail API 2.0.0
    implementation("jakarta.activation:jakarta.activation-api:2.1.3") // Jakarta Activation API 2.1.3
    
    // Re-add com.sun.mail for compatibility (only at runtime)
    runtimeOnly("com.sun.mail:jakarta.mail:1.6.7") // Required at runtime for compatibility

   
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    // Spring Boot DevTools for development
    developmentOnly("org.springframework.boot:spring-boot-devtools")

	
    runtimeOnly("org.postgresql:postgresql")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  
    // Dependencies for the development source set
    "developmentImplementation"(sourceSets["main"].output) // Access main source set from development
    "developmentImplementation"("org.postgresql:postgresql") // PostgreSQL driver for development
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

// Define the npm command based on the OS
val npmCommand: String by lazy {
    val os = System.getProperty("os.name").toLowerCase()
    if (os.contains("win")) "npm.cmd" else "npm"
}

// Task to validate that npm is available
tasks.register("validateNpm") {
    doLast {
        val process = ProcessBuilder(npmCommand, "--version")
            .redirectErrorStream(true)
            .start()

        val exitCode = process.waitFor()
        if (exitCode != 0) {
            throw GradleException("NPM is not available. Please ensure Node.js and NPM are installed and available in your PATH.")
        }
    }
}

// Task to install Node.js dependencies, including '@sveltejs/adapter-static'
tasks.register<Exec>("installNodeDependencies") {
    group = "frontend"
    description = "Installs Node.js dependencies for the Svelte project"
    workingDir = file("src/UI")
    commandLine(npmCommand, "install")

    // Add the adapter installation if not already installed
    doFirst {
        val adapterInstalled = ProcessBuilder(npmCommand, "list", "@sveltejs/adapter-static")
            .directory(file("src/UI"))
            .redirectErrorStream(true)
            .start()
            .waitFor() == 0

        if (!adapterInstalled) {
            println("Installing @sveltejs/adapter-static...")
            ProcessBuilder(npmCommand, "install", "@sveltejs/adapter-static", "--save-dev")
                .directory(file("src/UI"))
                .redirectErrorStream(true)
                .start()
                .waitFor()
        }
    }

    dependsOn("validateNpm")
}

// Task to build the Svelte application
tasks.register<Exec>("buildSvelte") {
    group = "frontend"
    description = "Builds the Svelte application"
    dependsOn("installNodeDependencies") // Ensure dependencies are installed first
    workingDir = file("src/UI")
    commandLine(npmCommand, "run", "build")
}

// Task to copy Svelte build artifacts to Spring Boot's static resources
tasks.register<Copy>("copySvelteBuild") {
    group = "frontend"
    description = "Copies the Svelte build files to Spring Boot's static resources"
    dependsOn("buildSvelte") // Ensure the Svelte app is built first
    from("src/UI/build") // Adjust based on your Svelte output folder
    into("src/main/resources/static")
}

tasks.named("processResources") {
    dependsOn("copySvelteBuild")
}

// Task to merge and build the final JAR
tasks.named("bootJar") {
    dependsOn("copySvelteBuild") // Ensure Svelte files are copied before creating the JAR
}



// Custom Gradle Task to Reset Database
tasks.register<JavaExec>("resetDatabase") {
    group = "Database"
    description = "Resets the database by running a standalone Java application."

    // Use the project's runtime classpath
   classpath = sourceSets["development"].runtimeClasspath

    // Specify the main class
    mainClass.set("com.techpuram.saascore.DatabaseResetMain") // Replace with your actual main class

    // Pass arguments if necessary
    args("reset-db")

     doFirst {
        println("Classpath: ${sourceSets["development"].runtimeClasspath.asPath}")
    }
}

// Load application.properties
fun loadProperties(): Properties {
    val props = Properties()
    val propertiesFile = file("src/main/resources/application.properties")
    if (propertiesFile.exists()) {
        props.load(FileInputStream(propertiesFile))
    } else {
        throw FileNotFoundException("application.properties not found at ${propertiesFile.absolutePath}")
    }
    return props

}