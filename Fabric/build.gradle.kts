import com.blamejared.crafttweaker.gradle.Dependencies
import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import com.blamejared.modtemplate.Utils
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.darkhax.curseforgegradle.Constants as CFG_Contants

plugins {
    id("fabric-loom") version "0.12-SNAPSHOT"
    id("com.blamejared.crafttweaker.default")
    id("com.blamejared.crafttweaker.loader")
    id("net.darkhax.curseforgegradle")
}

dependencies {
    minecraft("com.mojang:minecraft:${Versions.MINECRAFT}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${Versions.MINECRAFT}:${Versions.PARCHMENT}@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${Versions.FABRIC_LOADER}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.FABRIC}")
    implementation(project(":Common"))

    modCompileOnly("me.shedaniel:RoughlyEnoughItems-api-fabric:${Versions.REI}")
    modLocalRuntime("me.shedaniel:RoughlyEnoughItems-fabric:${Versions.REI}")
    modCompileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin-fabric:${Versions.REI}")

    modImplementation("com.faux.fauxcustomentitydata:FauxCustomEntityData-fabric-${Versions.MINECRAFT}:${Versions.FAUX_CUSTOM_ENTITY_DATA}")

    implementation("org.reflections:reflections:${Versions.REFLECTIONS}")?.let { include(it) }
    implementation("org.javassist:javassist:${Versions.JAVA_ASSIST}")?.let { include(it) } // required for reflections

    modImplementation("com.faux.ingredientextension:IngredientExtensionAPI-fabric-${Versions.MINECRAFT}:${Versions.INGREDIENT_EXTENSION_API}")

    gametestCompileOnly(files(project(":Common").dependencyProject.sourceSets.gametest.get().java.srcDirs))
    Dependencies.ZENCODE_TEST.forEach {
        gametestImplementation(project(it).dependencyProject.sourceSets.test.get().output)
    }
}

loom {
    accessWidenerPath.set(project(":Common").file("src/main/resources/${Properties.MOD_ID}.accesswidener"))
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
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run_server")
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
    mainFile.changelog = Utils.getFullChangelog(project)
    mainFile.releaseType = CFG_Contants.RELEASE_TYPE_RELEASE
    mainFile.addJavaVersion("Java ${Versions.MOD_JAVA}")
    mainFile.addRequirement("ingredient-extension-api")
    mainFile.addRequirement("faux-custom-entity-data")
    mainFile.addRequirement("fabric-api")

    doLast {
        project.ext.set("curse_file_url", "${Properties.CURSE_HOMEPAGE_LINK}/files/${mainFile.curseFileId}")
    }
}