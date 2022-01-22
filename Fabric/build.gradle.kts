import com.blamejared.modtemplate.Utils
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import net.darkhax.curseforgegradle.Constants as CFG_Contants

plugins {
    `maven-publish`
    id("fabric-loom") version "0.10-SNAPSHOT"
    id("com.blamejared.modtemplate")
    id("net.darkhax.curseforgegradle") version ("1.0.8")
}

val modVersion: String by project
val minecraftVersion: String by project
val fabricVersion: String by project
val fabricLoaderVersion: String by project
val modName: String by project
val modAuthor: String by project
val modId: String by project
val modAvatar: String by project
val curseProjectId: String by project
val curseHomepageLink: String by project
val gitFirstCommit: String by project
val gitRepo: String by project
val modJavaVersion: String by project

val baseArchiveName = "${modName}-fabric-${minecraftVersion}"

version = Utils.updatingSemVersion(modVersion)
base {
    archivesName.set(baseArchiveName)
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-1.18.1:2021.12.19@zip")
    })
    modImplementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricVersion}")
    implementation(project(":Common"))
    implementation(project(":Crafttweaker_Annotations"))

    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        implementation(project(it.toString()))
    }

    modImplementation("me.shedaniel:RoughlyEnoughItems-fabric:7.1.357")

    implementation("org.reflections:reflections:0.10.2")?.let { include(it) }
    implementation("org.javassist:javassist:3.28.0-GA")?.let { include(it) } // required for reflections


    modImplementation("com.faux.ingredientextension:IngredientExtensionAPI-fabric-1.18.1:2.0.1")

    gametestCompileOnly(files(project(":Common").dependencyProject.sourceSets.gametest.get().java.srcDirs))
    (project.ext["zenCodeTestDeps"] as Set<*>).forEach {
        gametestImplementation(project(it.toString()).dependencyProject.sourceSets.test.get().output)
    }
}

loom {
    accessWidenerPath.set(project(":Common").file("src/main/resources/${modId}.accesswidener"))
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
            vmArgs("-Dfabric-api.gametest=1",
                "-Dfabric-api.gametest.report-file=${file("fabric-game-tests.html").path}")
            configName = "Fabric Game Test"
            ideConfigGenerated(true)
        }
    }
}


modTemplate {
    mcVersion(minecraftVersion)
    curseHomepage(curseHomepageLink)
    displayName(modName)
    modLoader("Fabric")
    changelog.apply {
        enabled(true)
        firstCommit(gitFirstCommit)
        repo(gitRepo)
    }
    versionTracker.apply {
        enabled(true)
        endpoint(System.getenv("versionTrackerAPI"))
        author(modAuthor)
        projectName("${modName}-Fabric")
        homepage(curseHomepageLink)
        uid(System.getenv("versionTrackerKey"))
    }
}

tasks.compileGametestJava {
    source(project(":Common").sourceSets.gametest.get().java)
}

tasks.processResources {
    outputs.upToDateWhen { false }
    from(project(":Common").sourceSets.main.get().resources)
    dependsOn(":StdLibs:zipItUp")
    from(files(evaluationDependsOn(":StdLibs").tasks.getByName("zipItUp").outputs))

    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }

    filesMatching("*.mixins.json") {
        expand("refmap_target" to "$baseArchiveName-")
    }
}
tasks.withType<JavaCompile> {
    source(project(":Common").sourceSets.main.get().allSource)
    source(project(":Crafttweaker_Annotations").sourceSets.main.get().allSource)

    (project.ext["zenCodeDeps"] as Set<*>).forEach {
        source(project(it.toString()).sourceSets.main.get().allSource)
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

tasks.create<TaskPublishCurseForge>("publishCurseForge") {
    apiToken = System.getenv("curseforgeApiToken") ?: 0

    val mainFile = upload(curseProjectId, file("${project.buildDir}/libs/$baseArchiveName-$version.jar"))
    mainFile.changelogType = "markdown"
    mainFile.changelog = Utils.getFullChangelog(project)
    mainFile.releaseType = CFG_Contants.RELEASE_TYPE_RELEASE
    mainFile.addJavaVersion("Java $modJavaVersion")
    mainFile.addRequirement("ingredient-extension-api")
    mainFile.addRequirement("fabric-api")

    doLast {
        project.ext.set("curse_file_url", "${curseHomepageLink}/files/${mainFile.curseFileId}")
    }
}