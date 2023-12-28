import com.blamejared.crafttweaker.gradle.Dependencies
import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import com.blamejared.gradle.mod.utils.GMUtils
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import org.gradle.kotlin.dsl.publishing
import net.darkhax.curseforgegradle.Constants as CFG_Constants

plugins {
    id("com.blamejared.crafttweaker.default")
    id("com.blamejared.crafttweaker.loader")
    id("net.minecraftforge.gradle") version ("[6.0,6.2)")
    id("org.parchmentmc.librarian.forgegradle") version ("1.+")
    id("org.spongepowered.mixin") version ("0.7-SNAPSHOT")
    id("net.darkhax.curseforgegradle")
    id("com.modrinth.minotaur")
}

mixin {
    add(sourceSets.main.get(), "${Properties.MOD_ID}.refmap.json")

    config("${Properties.MOD_ID}.mixins.json")
    config("${Properties.MOD_ID}.forge.mixins.json")
}

dependencies {
    "minecraft"("net.minecraftforge:forge:${Versions.MINECRAFT}-${Versions.FORGE}")
    compileOnly(project(":common"))
    compileOnly(fg.deobf("mezz.jei:jei-${Versions.MINECRAFT}-common-api:${Versions.JEI}"))
    compileOnly(fg.deobf("mezz.jei:jei-${Versions.MINECRAFT}-forge-api:${Versions.JEI}"))
    localOnlyRuntime(fg.deobf("mezz.jei:jei-${Versions.MINECRAFT}-forge:${Versions.JEI}"))

    annotationProcessor("org.spongepowered:mixin:${Versions.MIXIN}-SNAPSHOT:processor")

}

minecraft {
    mappings("parchment", "${Versions.PARCHMENT_MINECRAFT}-${Versions.PARCHMENT}-${Versions.MINECRAFT}")
    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    runs {
        all {
            lazyToken("minecraft_classpath") {
                configurations.library.get().copyRecursive().resolve()
                        .joinToString(File.pathSeparator) { it.absolutePath }

                configurations.gametestLibrary.get().copyRecursive().resolve()
                        .joinToString(File.pathSeparator) { it.absolutePath }
            }
        }
        create("client") {
            taskName("Client")
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("-mixin.config=${Properties.MOD_ID}.mixins.json", "-mixin.config=${Properties.MOD_ID}.forge.mixins.json")
            environment("crafttweaker.logger.forward_to_latest_log", "true")
            mods {
                create(Properties.MOD_ID) {
                    source(sourceSets.main.get())
                    source(project(":common").sourceSets.main.get())
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
            environment("crafttweaker.logger.forward_to_latest_log", "true")
            mods {
                create(Properties.MOD_ID) {
                    source(sourceSets.main.get())
                    source(project(":common").sourceSets.main.get())
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
                    source(project(":common").sourceSets.main.get())
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
            args("-mixin.config=${Properties.MOD_ID}.mixins.json", "-mixin.config=${Properties.MOD_ID}.forge.mixins.json")
            mods {
                create(Properties.MOD_ID) {
                    source(sourceSets.main.get())
                    source(sourceSets.gametest.get())

                    source(project(":common").sourceSets.main.get())
                    source(project(":common").sourceSets.gametest.get())

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

publishing {
    publications {
        named("mavenJava", MavenPublication::class) {
            fg.component(this)
        }
    }
}

tasks.create<TaskPublishCurseForge>("publishCurseForge") {
    apiToken = GMUtils.locateProperty(project, "curseforgeApiToken") ?: 0

    val mainFile = upload(Properties.CURSE_PROJECT_ID, file("${project.buildDir}/libs/${base.archivesName.get()}-$version.jar"))
    mainFile.changelogType = "markdown"
    mainFile.changelog = GMUtils.smallChangelog(project, Properties.GIT_REPO)
    mainFile.releaseType = CFG_Constants.RELEASE_TYPE_RELEASE
    mainFile.addJavaVersion("Java ${Versions.MOD_JAVA}")

    doLast {
        project.ext.set("curse_file_url", "${Properties.CURSE_HOMEPAGE_LINK}/files/${mainFile.curseFileId}")
    }
}

modrinth {
    token.set(GMUtils.locateProperty(project, "modrinth_token"))
    projectId.set(Properties.MODRINTH_PROJECT_ID)
    changelog.set(GMUtils.smallChangelog(project, Properties.GIT_REPO))
    versionName.set("Forge-${Versions.MINECRAFT}-$version")
    versionType.set("release")
    uploadFile.set(tasks.jar.get())
}