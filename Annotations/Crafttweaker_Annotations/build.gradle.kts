plugins {
    java
    eclipse
    idea
    `maven-publish`
}

group = "com.blamejared.crafttweaker"
version = "2.0.0" + (if (System.getenv().containsKey("BUILD_NUMBER")) ".${System.getenv("BUILD_NUMBER")}" else "")

val baseArchiveName = "Crafttweaker_Annotation-1.18.1"

base {
    archivesName.set(baseArchiveName)
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