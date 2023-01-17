import com.blamejared.crafttweaker.gradle.Dependencies
import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import com.blamejared.modtemplate.Utils
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.minecraftforge.gradle.userdev.tasks.JarJar
import net.darkhax.curseforgegradle.Constants as CFG_Contants

plugins {
    id("com.blamejared.crafttweaker.default")
    id("com.blamejared.crafttweaker.loader")
    id("net.minecraftforge.gradle") version ("5.1.+")
    id("org.parchmentmc.librarian.forgegradle") version ("1.+")
    id("org.spongepowered.mixin") version ("0.7-SNAPSHOT")
    id("net.darkhax.curseforgegradle")
}

mixin {
    add(sourceSets.main.get(), "${Properties.MOD_ID}.refmap.json")

    config("${Properties.MOD_ID}.mixins.json")
    config("${Properties.MOD_ID}.forge.mixins.json")
}

dependencies {
    minecraft("net.minecraftforge:forge:${Versions.MINECRAFT}-${Versions.FORGE}")
    compileOnly(project(":Common"))

    compileOnly(fg.deobf("mezz.jei:jei-${Versions.MINECRAFT}-common-api:${Versions.JEI}"))
    compileOnly(fg.deobf("mezz.jei:jei-${Versions.MINECRAFT}-forge-api:${Versions.JEI}"))
    runtimeOnly(fg.deobf("mezz.jei:jei-${Versions.MINECRAFT}-forge:${Versions.JEI}"))

    annotationProcessor("org.spongepowered:mixin:${Versions.MIXIN}-SNAPSHOT:processor")

    library("com.dwarveddonuts.neverwinter:NeverWinter:${Versions.NEVERWINTER}")
    jarJar("com.dwarveddonuts.neverwinter:NeverWinter:[${Versions.NEVERWINTER}]")
}

minecraft {
    mappings("parchment", "1.19.2-${Versions.PARCHMENT}-${Versions.MINECRAFT}")
    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    runs {
        all {
            lazyToken("minecraft_classpath") {
                sequenceOf(configurations.library, configurations.gametestLibrary)
                        .flatMap { it.get().copyRecursive().resolve() }
                        .joinToString(File.pathSeparator) { it.absolutePath }
            }
        }
        create("client") {
            taskName("Client")
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("-mixin.config=${Properties.MOD_ID}.mixins.json", "-mixin.config=${Properties.MOD_ID}.forge.mixins.json")
            mods {
                create(Properties.MOD_ID) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                    Dependencies.ZENCODE.forEach {
                        source(project(it).sourceSets.main.get())
                    }
                }
            }
        }
        create("server") {
            taskName("Server")
            workingDirectory(project.file("run_server"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("-mixin.config=${Properties.MOD_ID}.mixins.json", "-mixin.config=${Properties.MOD_ID}.forge.mixins.json", "nogui")
            mods {
                create(Properties.MOD_ID) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                    Dependencies.ZENCODE.forEach {
                        source(project(it).sourceSets.main.get())
                    }
                }
            }
        }

        create("data") {
            taskName("Data")
            workingDirectory(project.file("run_game_test"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args(
                    "--mod",
                    Properties.MOD_ID,
                    "--all",
                    "--output",
                    file("src/generated/resources/"),
                    "--existing",
                    file("src/main/resources/")
            )
            args("-mixin.config=${Properties.MOD_ID}.mixins.json", "-mixin.config=${Properties.MOD_ID}.forge.mixins.json")
            mods {
                create(Properties.MOD_ID) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                    Dependencies.ZENCODE.forEach {
                        source(project(it).sourceSets.main.get())
                    }
                }
            }
        }

        create("gameTestServer") {
            taskName("GameTest")
            workingDirectory(project.file("run_game_test"))
            ideaModule("${rootProject.name}.${project.name}.main")
            property("forge.enabledGameTestNamespaces", Properties.MOD_ID)
            forceExit = false
            args("-mixin.config=${Properties.MOD_ID}.mixins.json", "-mixin.config=${Properties.MOD_ID}.forge.mixins.json")
            mods {
                create(Properties.MOD_ID) {
                    source(sourceSets.main.get())
                    source(sourceSets.gametest.get())

                    source(project(":Common").sourceSets.main.get())
                    source(project(":Common").sourceSets.gametest.get())

                    Dependencies.ZENCODE.forEach {
                        source(project(it).sourceSets.main.get())
                    }
                    Dependencies.ZENCODE_TEST.forEach {
                        source(project(it).sourceSets.test.get())
                    }
                }
            }
        }
    }
}

jarJar {
    enable()
}

reobf {
    create("jarJar")
}

tasks {
    create<TaskPublishCurseForge>("publishCurseForge") {
        apiToken = Utils.locateProperty(project, "curseforgeApiToken") ?: 0

        val mainFile = upload(Properties.CURSE_PROJECT_ID, file("${project.buildDir}/libs/${base.archivesName.get()}-$version.jar"))
        mainFile.changelogType = "markdown"
        mainFile.changelog = Utils.getFullChangelog(project)
        mainFile.releaseType = CFG_Contants.RELEASE_TYPE_RELEASE
        mainFile.addJavaVersion("Java ${Versions.MOD_JAVA}")
//      mainFile.addRequirement("jeitweaker")

        doLast {
            project.ext.set("curse_file_url", "${Properties.CURSE_HOMEPAGE_LINK}/files/${mainFile.curseFileId}")
        }
    }

    jar {
        archiveClassifier.set("thin")
    }

    named<JarJar>("jarJar") {
        archiveClassifier.set("")
    }
}