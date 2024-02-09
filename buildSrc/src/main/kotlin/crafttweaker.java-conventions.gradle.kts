import com.blamejared.crafttweaker.gradle.Dependencies
import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import com.blamejared.gradle.mod.utils.GMUtils
import org.gradle.jvm.tasks.Jar
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

plugins {
    base
    `java-library`
    idea
    `maven-publish`
}

base.archivesName.set("${Properties.MOD_NAME}-${project.name.lowercase()}-${Versions.MINECRAFT}")
version = GMUtils.updatingVersion(Versions.MOD)
group = Properties.GROUP

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
    withSourcesJar()
    withJavadocJar()
    sourceSets {
        named("main") {
            resources {
                srcDirs.add(project.file("src/generated/resources"))
            }
        }
        register("gametest") {
            resources {
                srcDirs.add(project.file("src/gametest/resources"))
            }
            compileClasspath += sourceSets["main"].runtimeClasspath
            runtimeClasspath += sourceSets["main"].runtimeClasspath
        }
    }
}
@Suppress("UnstableApiUsage")
repositories {
    mavenCentral()
    maven("https://maven.blamejared.com/") {
        name = "BlameJared"
        content {
            includeGroupAndSubgroups("com.blamejared")
            includeGroupAndSubgroups("mezz.jei")
            includeGroupAndSubgroups("com.faux")
            includeGroupAndSubgroups("org.openzen")
        }
    }
    maven("https://repo.spongepowered.org/repository/maven-public/") {
        name = "Sponge"
        content {
            includeGroupAndSubgroups("org.spongepowered")
        }
    }
    maven("https://maven.parchmentmc.org/") {
        name = "ParchmentMC"
        content {
            includeGroupAndSubgroups("org.parchmentmc")
        }
    }
    maven("https://maven.shedaniel.me/") {
        name = "REI"
        content {
            includeGroupAndSubgroups("me.shedaniel")
            includeGroupAndSubgroups("dev.architectury")
        }
    }
}

tasks {
    withType<GenerateModuleMetadata>().configureEach {
        enabled = false
    }
    named<JavaCompile>("compileTestJava").configure {
        options.isFork = true
        options.compilerArgs.add("-XDenableSunApiLintControl")
    }
    named<JavaCompile>("compileJava").configure {
        options.compilerArgs.add("-Acrafttweaker.processor.document.output_directory=${project.rootProject.file(Properties.DOCS_OUTPUT_DIR)}")
        options.compilerArgs.add("-Acrafttweaker.processor.document.multi_source=true")
    }
    withType<JavaCompile>().matching { notNeoTask(it) }.configureEach {
        options.encoding = StandardCharsets.UTF_8.toString()
        options.release.set(Versions.MOD_JAVA.toInt())
        Dependencies.ZENCODE.forEach {
            source(project(it).sourceSets.getByName("main").allSource)
        }
    }
    withType<Javadoc>().matching { notNeoTask(it) }.configureEach {
        options {
            encoding = StandardCharsets.UTF_8.toString()
            // Javadoc defines this specifically as StandardJavadocDocletOptions
            // but only has a getter for MinimalJavadocOptions, but let's just make sure to be safe
            if (this is StandardJavadocDocletOptions) {
                tags("docParam", "docEvent", "docShortDescription", "docObtention")
                addStringOption("Xdoclint:none", "-quiet")
            }
        }
        Dependencies.ZENCODE.forEach {
            source(project(it).sourceSets.getByName("main").allJava)
        }
    }
    named<Jar>("sourcesJar").configure {
        Dependencies.ZENCODE.forEach {
            from(project(it).sourceSets.getByName("main").allSource)
        }
    }
    named<JavaCompile>("compileGametestJava").configure {
        if (this.project.name == "neoforge") {
            Dependencies.ZENCODE.forEach {
                source(project(it).sourceSets.getByName("main").allSource)
            }
            Dependencies.ZENCODE_TEST.forEach {
                source(project(it).sourceSets.getByName("test").allSource)
            }
        }
        if (this.project.name == "forge") {
            project.copy {
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                from(project.sourceSets.getByName("gametest").output)
                Dependencies.ZENCODE.forEach {
                    from(project(it).sourceSets.main.get().output)
                }
                Dependencies.ZENCODE_TEST.forEach {
                    from(project(it).sourceSets.test.get().output)
                }
                into(project.layout.buildDirectory.dir("sourcesSets/main"))
            }
        }
    }
    withType<ProcessResources>().matching { notNeoTask(it) }.configureEach {
        dependsOn(":StdLibs:zipItUp")
        from(project.files(project.evaluationDependsOn(":StdLibs").tasks.getByName("zipItUp").outputs))

        val properties = mapOf(
                "version" to project.version,
                "MOD" to Versions.MOD,
                "JAVA" to Versions.MOD_JAVA,
                "MINECRAFT" to Versions.MINECRAFT,
                "FABRIC_LOADER" to Versions.FABRIC_LOADER,
                "FABRIC" to Versions.FABRIC,
                "FORGE" to Versions.FORGE,
                "FORGE_LOADER" to Versions.FORGE_LOADER,
                "NEO_FORGE" to Versions.NEO_FORGE,
                "NEO_FORGE_LOADER" to Versions.NEO_FORGE_LOADER,
                "GROUP" to Properties.GROUP,
                "NAME" to Properties.MOD_NAME,
                "AUTHOR" to Properties.MOD_AUTHOR,
                "MOD_ID" to Properties.MOD_ID,
                "AVATAR" to Properties.MOD_AVATAR,
                "CURSE_PROJECT_ID" to Properties.CURSE_PROJECT_ID,
                "CURSE_HOMEPAGE_LINK" to Properties.CURSE_HOMEPAGE_LINK,
                "MODRINTH_PROJECT_ID" to Properties.MODRINTH_PROJECT_ID,
                "GIT_REPO" to Properties.GIT_REPO,
                "DESCRIPTION" to Properties.DESCRIPTION,
                "ITEM_ICON" to Properties.ITEM_ICON,
        )
        inputs.properties(properties)
        filesMatching(setOf("fabric.mod.json", "META-INF/mods.toml", "pack.mcmeta")) {
            expand(properties)
        }
    }
    withType<Jar>().matching { notNeoTask(it) }.configureEach {
        from(project.rootProject.file("LICENSE"))
        manifest {
            attributes["Specification-Title"] = Properties.MOD_NAME
            attributes["Specification-Vendor"] = Properties.SIMPLE_AUTHOR
            attributes["Specification-Version"] = archiveVersion
            attributes["Implementation-Title"] = project.name
            attributes["Implementation-Version"] = archiveVersion
            attributes["Implementation-Vendor"] = Properties.SIMPLE_AUTHOR
            attributes["Implementation-Timestamp"] = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
            attributes["Timestamp"] = System.currentTimeMillis()
            attributes["Built-On-Java"] = "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})"
            attributes["Build-On-Minecraft"] = Versions.MINECRAFT
        }
    }
}

@Suppress("UnstableApiUsage")
configurations {
    val gtl = register("gametestLibrary")
    val library = register("library")
    val lor = register("localOnlyRuntime")
    getByName("gametestImplementation") {
        extendsFrom(gtl.get())
    }
    getByName("implementation") {
        extendsFrom(library.get())
    }
    getByName("runtimeClasspath").extendsFrom(lor.get())
    getByName("gametestRuntimeClasspath").extendsFrom(gtl.get())
    // fabric loader 0.15 adds mixinextras which causes a crash due to us pulling in other ASM versions from ZC
    all {
        resolutionStrategy {
            force("org.ow2.asm:asm:9.6")
            force("org.ow2.asm:asm-commons:9.6")
        }
    }
}

dependencies {
    annotationProcessor("com.blamejared.crafttweaker:Crafttweaker_Annotation_Processors:${Versions.CRAFTTWEAKER_ANNOTATION_PROCESSOR}")
    "gametestLibrary"("org.hamcrest:hamcrest:${Versions.HAMCREST}")
    "gametestLibrary"("org.junit.jupiter:junit-jupiter-engine:${Versions.JUPITER_ENGINE}")
    "gametestLibrary"("org.junit.jupiter:junit-jupiter-params:${Versions.JUPITER_ENGINE}")
    "gametestLibrary"("org.junit.platform:junit-platform-launcher:${Versions.JUNIT_PLATFORM_LAUNCHER}")

    Dependencies.ZENCODE.forEach {
        implementation(project(it))
        "gametestImplementation"(project(it))
    }

    Dependencies.ZENCODE_TEST.forEach {
        "gametestImplementation"(project(it).dependencyProject.sourceSets.test.get().output)
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components.getByName("java"))

        }
    }
    repositories {
        maven("file:///${System.getenv("local_maven")}")
    }
}

idea {
    module {
        excludeDirs.addAll(setOf(project.file("run"), project.file("runs"), project.file("run_server"), project.file("run_client"), project.file("run_game_test")))
    }
}

fun notNeoTask(task: Task): Boolean {
    return !task.name.startsWith("neo")
}