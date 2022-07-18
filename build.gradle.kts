import com.blamejared.modtemplate.Utils
import com.diluv.schoomp.Webhook
import com.diluv.schoomp.message.Message
import com.diluv.schoomp.message.embed.Embed
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

// Not sure how to do this without the buildscript block
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.diluv.schoomp:Schoomp:1.2.6")
    }
}

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
val gitRepo: String by project
val modAvatar: String by project
val modVersion: String by project

plugins {
    `java-library`
    idea
    id("com.blamejared.modtemplate") version ("3.0.0.37")
}

version = Utils.updatingVersion(modVersion)

tasks.wrapper {
    //Define wrapper values here so as to not have to always do so when updating gradlew.properties
    gradleVersion = "7.4.1"
    distributionType = Wrapper.DistributionType.BIN
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

    this.ext.set(
            "zenCodeDeps",
            setOf(
                    ":CodeFormatter",
                    ":CodeFormatterShared",
                    ":JavaIntegration",
                    ":JavaAnnotations",
                    ":JavaBytecodeCompiler",
                    ":JavaShared",
                    ":Validator",
                    ":Parser",
                    ":CodeModel",
                    ":Shared"
            )
    )
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
        apply(plugin = "java")
        apply(plugin = "idea")
        val library by configurations.creating
        val gametestLibrary by configurations.creating

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
        configurations {
            implementation.get().extendsFrom(library)
            this.getByName("gametestImplementation").extendsFrom(gametestLibrary)
        }

        dependencies {
            annotationProcessor("com.blamejared.crafttweaker:Crafttweaker_Annotation_Processors:3.0.0.8")

            implementation("com.google.code.findbugs:jsr305:3.0.2")

            gametestLibrary("com.google.truth:truth:1.1.3")
            // This is required for Truth since MC uses an old Guava version, however in 1.18 the game uses an updated version.
            gametestLibrary("com.google.guava:guava:31.1-jre")
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
                    this.tags("docParam", "docEvent", "docShortDescription", "docObtention")
                    this.addStringOption("Xdoclint:none", "-quiet")
                }
            }
        }
    }
    // </editor-fold>
    // <editor-fold desc="Other projects">
    else {
    }
    // </editor-fold>
}

tasks.create("gameTest") {
    dependsOn(":Fabric:runGameTest", ":Forge:GameTest")
    group = "verification"
}

tasks.create("postDiscord") {

    doLast {
        try {

            // Create a new webhook instance for Discord
            val webhook = Webhook(
                    System.getenv("discordCFWebhook"),
                    "$modName CurseForge Gradle Upload"
            )

            // Craft a message to send to Discord using the webhook.
            val message = Message()
            message.username = modName
            message.avatarUrl = modAvatar
            message.content = "$modName $version for Minecraft $minecraftVersion has been published!"

            val embed = Embed()
            val downloadSources = StringJoiner("\n")

            if (project(":Fabric").ext.has("curse_file_url")) {

                downloadSources.add("<:fabric:932163720568782878> [Fabric](${project(":Fabric").ext.get("curse_file_url")})")
            }

            if (project(":Forge").ext.has("curse_file_url")) {

                downloadSources.add("<:forge:932163698003443804> [Forge](${project(":Forge").ext.get("curse_file_url")})")
            }

            downloadSources.add(
                    "<:maven:932165250738970634> `\"${project(":Common").group}:${project(":Common").base.archivesName.get()}:${
                        project(":Common").version
                    }\"`"
            )
            downloadSources.add(
                    "<:maven:932165250738970634> `\"${project(":Fabric").group}:${project(":Fabric").base.archivesName.get()}:${
                        project(":Fabric").version
                    }\"`"
            )
            downloadSources.add(
                    "<:maven:932165250738970634> `\"${project(":Forge").group}:${project(":Forge").base.archivesName.get()}:${
                        project(":Forge").version
                    }\"`"
            )

            // Add Curseforge DL link if available.
            val downloadString = downloadSources.toString()

            if (downloadString.isNotEmpty()) {

                embed.addField("Download", downloadString, false)
            }

            // Just use the Forge changelog for now, the files are the same anyway.
            embed.addField("Changelog", Utils.getCIChangelog(project, gitRepo).take(1000), false)

            embed.color = 0xF16436
            message.addEmbed(embed)

            webhook.sendMessage(message)
        } catch (e: IOException) {

            project.logger.error("Failed to push CF Discord webhook.")
        }
    }

}

val apDir = "CraftTweaker-Annotation-Processors";

tasks.create("checkoutAP") {
    doFirst {
        if (!rootProject.file(apDir).exists() || (rootProject.file(apDir).listFiles() ?: arrayOf()).isEmpty()) {
            exec {
                commandLine("git", "clone", "https://github.com/CraftTweaker/CraftTweaker-Annotation-Processors.git")
            }
        } else {
            throw GradleException("$apDir folder already exists and is not empty!")
        }
    }
}

tasks.create<Delete>("clearAP") {
    doFirst {
        delete(rootProject.file(apDir))
    }
}