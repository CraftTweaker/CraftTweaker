import com.blamejared.modtemplate.Utils
plugins {
    `java-library`
    `maven-publish`
    id("com.blamejared.modtemplate")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}
val modVersion: String by project
val minecraftVersion: String by project
val commonRunsEnabled: String by project
val commonClientRunName: String? by project
val commonServerRunName: String? by project
val modName: String by project
val modId: String by project

val baseArchiveName = "${modName}-common-${minecraftVersion}"

version = Utils.updatingVersion(modVersion)
tasks.withType<JavaCompile> {
    source(project(":Crafttweaker_Annotations").sourceSets.main.get().allSource)
}

minecraft {
    version(minecraftVersion)
    accessWideners(project.file("src/main/resources/${modId}.accesswidener"))
    runs {
        if (commonRunsEnabled == "true") {
            client(commonClientRunName ?: "vanilla_client") {
                workingDirectory(project.file("run"))
            }
            server(commonServerRunName ?: "vanilla_server") {
                workingDirectory(project.file("run_server"))
            }
        }
    }
}

base {
    archivesName.set(baseArchiveName)
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.4")
    implementation(project(":Crafttweaker_Annotations"))
    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        implementation(project(it.toString()))
    }

    (project.ext["zenCodeTestDeps"] as Set<*>).forEach {
        gametestImplementation(project(it.toString()).dependencyProject.sourceSets.test.get().output)
    }
}

tasks.processResources {
    outputs.upToDateWhen { false }
    dependsOn(":StdLibs:zipItUp")
    from(files(evaluationDependsOn(":StdLibs").tasks.getByName("zipItUp").outputs))

    inputs.property("version", project.version)
}

tasks.withType<JavaCompile> {
    source(project(":Crafttweaker_Annotations").sourceSets.main.get().allSource)

    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        source(project(it.toString()).sourceSets.main.get().allSource)
    }
}

tasks.withType<Javadoc> {
    source(project(":Crafttweaker_Annotations").sourceSets.main.get().allJava)

    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        source(project(it.toString()).sourceSets.main.get().allJava)
    }
}

tasks.named<Jar>("sourcesJar") {
    from(project(":Crafttweaker_Annotations").sourceSets.main.get().allSource)

    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        from(project(it.toString()).sourceSets.main.get().allSource)
    }
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