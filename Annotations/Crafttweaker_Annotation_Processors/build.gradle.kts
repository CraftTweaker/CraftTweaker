plugins {
    java
    eclipse
    idea
    `maven-publish`
}

group = "com.blamejared.crafttweaker"
version = "2.0.0" + (if (System.getenv().containsKey("BUILD_NUMBER")) ".${System.getenv("BUILD_NUMBER")}" else "")

val projectDeps = setOf(":Crafttweaker_Annotations", ":JavaAnnotations")
val baseArchiveName = "Crafttweaker_Annotation_Processors-1.18.1"

base {
    archivesName.set(baseArchiveName)
}

tasks.jar {
    dependsOn.addAll(projectDeps.map { "$it:compileJava" })
}

dependencies {
    projectDeps.forEach { implementation(project(it)) }

    implementation("org.jetbrains:annotations:22.0.0")
    implementation("org.reflections:reflections:0.10.2")
    implementation(files("libs/tools.jar"))
    implementation("com.google.code.gson:gson:2.8.9")
}

publishing {

    publications {

        register("mavenJava", MavenPublication::class) {
            artifactId = baseArchiveName
            from(components["java"])
        }

    }

    repositories {

        maven("file://${System.getenv("local_maven")}")
    }
}