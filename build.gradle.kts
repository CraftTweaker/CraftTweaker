
import java.text.SimpleDateFormat
import java.util.*

val minecraftVersion: String by project
val commonRunsEnabled: String by project
val commonClientRunName: String by project
val commonServerRunName: String by project
val forgeVersion: String by project
val forgeAtsEnabled: String by project
val fabricVersion: String by project
val fabricLoaderVersion: String by project
val modName: String by project
val modAuthor: String by project
val modId: String by project
val modJavaVersion: String by project
val zenCodeJavaVersion: String by project

plugins {
    `java-library`
    idea
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "Sponge / Mixin"
        }
        maven("https://maven.blamejared.com") {
            name = "BlameJared Maven"
        }
        maven("https://dvs1.progwml6.com/files/maven") {
            name = "JEI"
            content {
                includeGroup("mezz.jei")
            }
        }
        maven("https://maven.shedaniel.me") {
            name = "REI"
            content {
                includeGroup("me.shedaniel")
                includeGroup("me.shedaniel.cloth")
                includeGroup("dev.architectury")
            }
        }
        maven("https://maven.parchmentmc.org") {
            name = "ParchmentMC"
        }
    }

    // occasionally look to see if forge and fabric have fixed this...
    tasks.withType<GenerateModuleMetadata> {

        enabled = false
    }

}

subprojects {

    this.ext.set("zenCodeDeps", setOf(":CodeFormatter", ":CodeFormatterShared", ":JavaIntegration", ":JavaAnnotations", ":JavaBytecodeCompiler", ":JavaShared", ":Validator", ":Parser", ":CodeModel", ":Shared"))
    this.ext.set("zenCodeTestDeps", setOf(":ScriptingExample"))

    // <editor-fold desc="ZenCode projects">
    if (project.projectDir.parent == rootProject.file("ZenCode").absolutePath) {
        apply(plugin = "java-library")
        group = "org.openzen.zencode"
        version = "0.3.8"

        extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(zenCodeJavaVersion.toInt()))
            withSourcesJar()
        }

        tasks.withType<JavaCompile> {
            options.encoding = "UTF-8"
        }


    }
    // </editor-fold>
    // <editor-fold desc="Modloader projects">
    else if (setOf("Common", "Forge", "Fabric").contains(project.name)) {
        apply(plugin = "java-library")
        apply(plugin = "idea")
        val library by configurations.creating

        configurations {
            implementation.get().extendsFrom(library)
        }

        idea {
            module {
                excludeDirs.add(project.file("run"))
                excludeDirs.add(project.file("run_server"))
            }
        }

        sourceSets {
            create("gametest") {
                resources {
                    srcDirs.add(project.file("src/gametest/resources"))
                }
                compileClasspath += sourceSets.main.get().runtimeClasspath
                runtimeClasspath += sourceSets.main.get().runtimeClasspath
            }
        }

        dependencies {
            library(project(":Crafttweaker_Annotations"))
            annotationProcessor(project(":Crafttweaker_Annotation_Processors"))

            implementation("com.google.code.findbugs:jsr305:3.0.1")

            "gametestImplementation"("com.google.truth:truth:1.1.3")
            "gametestImplementation"("com.google.truth.extensions:truth-java8-extension:1.1.3")
            // This is required for Truth since MC uses an old Guava version, however in 1.18 the game uses an updated version.
            "gametestImplementation"("com.google.guava:guava:31.0.1-jre")
        }

        extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(modJavaVersion))
            withJavadocJar()
            withSourcesJar()
        }

        tasks.withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release.set(modJavaVersion.toInt())
            options.compilerArgs.add("-Acrafttweaker.processor.document.output_directory=${rootProject.file("docsOut")}")
            options.compilerArgs.add("-Acrafttweaker.processor.document.multi_source=true")

        }

        tasks {
            compileTestJava {
                options.isFork = true
                options.compilerArgs.add("-XDenableSunApiLintControl")
            }

            jar {
                manifest {
                    attributes(
                            "Specification-Title" to modName,
                            "Specification-Vendor" to modAuthor,
                            "Specification-Version" to archiveVersion,
                            "Implementation-Title" to project.name,
                            "Implementation-Version" to archiveVersion,
                            "Implementation-Vendor" to modAuthor,
                            "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
                            "Timestamp" to System.currentTimeMillis(),
                            "Built-On-Java" to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                            "Build-On-Minecraft" to minecraftVersion
                    )
                }
            }
        }

        tasks.withType<Javadoc> {

            options {

                // Javadoc defines this specifically as StandardJavadocDocletOptions
                // but only has a getter for MinimalJavadocOptions, but let's just make sure to be safe
                if (this is StandardJavadocDocletOptions) {
                    this.tags("docParam", "docEvent", "docShortDescription")
                    this.addStringOption("Xdoclint:none", "-quiet")
                }
            }
        }
    }
    // </editor-fold>
    // <editor-fold desc="Other projects">
    else {
        apply(plugin = "java-library")
        // Any AP specific stuff can go here
    }
    // </editor-fold>
}