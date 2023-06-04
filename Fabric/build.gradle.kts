import com.blamejared.crafttweaker.gradle.Dependencies
import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import com.blamejared.gradle.mod.utils.GMUtils
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.darkhax.curseforgegradle.Constants as CFG_Constants

plugins {
    id("fabric-loom") version "1.1-SNAPSHOT"
    id("com.blamejared.crafttweaker.default")
    id("com.blamejared.crafttweaker.loader")
    id("net.darkhax.curseforgegradle")
    id("com.modrinth.minotaur")
}

dependencies {
    minecraft("com.mojang:minecraft:${Versions.MINECRAFT}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${Versions.MINECRAFT}:${Versions.PARCHMENT}@zip")
    })
    implementation("org.jetbrains:annotations:23.0.0")
    modImplementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC}")
    compileOnly(project(":Common"))

    modLocalRuntime("me.shedaniel:RoughlyEnoughItems-api-fabric:${Versions.REI}") {
        exclude("net.fabricmc", "fabric-loader")
    }
    modLocalRuntime("me.shedaniel:RoughlyEnoughItems-fabric:${Versions.REI}") {
        exclude("net.fabricmc", "fabric-loader")
    }

    implementation("org.reflections:reflections:${Versions.REFLECTIONS}")?.let { include(it) }
    implementation("org.javassist:javassist:${Versions.JAVA_ASSIST}")?.let { include(it) } // required for reflections

    modImplementation("com.faux.fauxcustomentitydata:FauxCustomEntityData-fabric-1.19.3:${Versions.FAUX_CUSTOM_ENTITY_DATA}")

    gametestCompileOnly(files(project(":Common").dependencyProject.sourceSets.gametest.get().java.srcDirs))
    Dependencies.ZENCODE_TEST.forEach {
        gametestImplementation(project(it).dependencyProject.sourceSets.test.get().output)
    }
}

loom {
    accessWidenerPath.set(project(":Common").file("src/main/resources/${Properties.MOD_ID}.accesswidener"))
    mixin {
        this.defaultRefmapName.set("${Properties.MOD_ID}.refmap.json")
    }
    mods {
        register("crafttweaker") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.gametest.get())
            Dependencies.ZENCODE.forEach {
                sourceSet(project(it).sourceSets.main.get())
                sourceSet(project(it).sourceSets.test.get())
            }
            Dependencies.ZENCODE_TEST.forEach {
                sourceSet(project(it).sourceSets.main.get())
                sourceSet(project(it).sourceSets.test.get())
            }
        }
    }
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
            programArg("--username=Dev")
            environmentVariable("crafttweaker.logger.forward_to_latest_log", true)
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run_server")
            environmentVariable("crafttweaker.logger.forward_to_latest_log", true)
        }
        create("GameTest") {
            server()
            source(sourceSets.gametest.get())
            vmArgs("-Dfabric-api.gametest=1")
            configName = "Fabric Game Test"
            ideConfigGenerated(true)
            runDir("run_game_test")
        }
    }
}

tasks.create<TaskPublishCurseForge>("publishCurseForge") {
    apiToken = System.getenv("curseforgeApiToken") ?: 0

    val mainFile = upload(Properties.CURSE_PROJECT_ID, file("${project.buildDir}/libs/${base.archivesName.get()}-$version.jar"))
    mainFile.changelogType = "markdown"
    mainFile.changelog = GMUtils.smallChangelog(project, Properties.GIT_REPO)
    mainFile.releaseType = CFG_Constants.RELEASE_TYPE_RELEASE
    mainFile.addGameVersion(Versions.MINECRAFT)
    mainFile.addJavaVersion("Java ${Versions.MOD_JAVA}")
    mainFile.addRequirement("faux-custom-entity-data")
    mainFile.addRequirement("fabric-api")

    doLast {
        project.ext.set("curse_file_url", "${Properties.CURSE_HOMEPAGE_LINK}/files/${mainFile.curseFileId}")
    }
}

modrinth {
    token.set(GMUtils.locateProperty(project, "modrinth_token"))
    projectId.set(Properties.MODRINTH_PROJECT_ID)
    changelog.set(GMUtils.smallChangelog(project, Properties.GIT_REPO))
    versionName.set("Fabric-${Versions.MINECRAFT}-$version")
    versionType.set("release")
    uploadFile.set(tasks.remapJar.get())
    dependencies {
        required.project("ingredient-extension-api")
        required.project("faux-custom-entity-data")
        required.project("fabric-api")
    }
}
