import com.blamejared.crafttweaker.gradle.Dependencies
import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import com.blamejared.gradle.mod.utils.GMUtils
import net.darkhax.curseforgegradle.Constants
import net.darkhax.curseforgegradle.TaskPublishCurseForge

plugins {
    id("com.blamejared.crafttweaker.default")
    id("com.blamejared.crafttweaker.loader")
    id("net.neoforged.gradle.userdev") version ("7.0.71")
    id("net.darkhax.curseforgegradle")
    id("com.modrinth.minotaur")
}

minecraft {
    accessTransformers.file(file("src/main/resources/META-INF/accesstransformer.cfg"))
}

runs {
    configureEach {
        modSource(project.sourceSets.main.get())
        modSource(project(":common").sourceSets.main.get())
        Dependencies.ZENCODE.forEach {
            modSource(project(it).sourceSets.main.get())
        }
        environmentVariables(mapOf("crafttweaker.logger.forward_to_latest_log" to "true"))
        systemProperty("forge.enabledGameTestNamespaces", Properties.MOD_ID)
    }
    register("client") {
    }
    register("server") {
        programArgument("--nogui")
    }
    register("gameTestServer") {
        modSource(project.sourceSets.gametest.get())
        modSource(project(":common").sourceSets.gametest.get())
        Dependencies.ZENCODE_TEST.forEach {
            modSource(project(it).sourceSets.test.get())
        }

        dependencies {
            runtime(project.configurations.gametestLibrary.get())
        }
    }
    register("data") {
        programArguments(
                "--mod",
                Properties.MOD_ID,
                "--all",
                "--output",
                file("src/generated/resources/").absolutePath,
                "--existing",
                file("src/main/resources/").absolutePath
        )
    }
}

dependencies {
    implementation("net.neoforged:neoforge:${Versions.NEO_FORGE}")
    compileOnly(project(":common"))
//    localOnlyRuntime("me.shedaniel:RoughlyEnoughItems-neoforge:${Versions.REI}")
}

tasks.create<TaskPublishCurseForge>("publishCurseForge") {
    dependsOn(tasks.jar)
    apiToken = GMUtils.locateProperty(project, "curseforgeApiToken") ?: 0

    val mainFile = upload(Properties.CURSE_PROJECT_ID, tasks.jar.get().archiveFile)
    mainFile.changelogType = "markdown"
    mainFile.changelog = GMUtils.smallChangelog(project, Properties.GIT_REPO)
    mainFile.releaseType = Constants.RELEASE_TYPE_RELEASE
    mainFile.addJavaVersion("Java ${Versions.MOD_JAVA}")
    mainFile.addGameVersion(Versions.MINECRAFT)
    mainFile.addModLoader("NeoForge")

    doLast {
        project.ext.set("curse_file_url", "${Properties.CURSE_HOMEPAGE_LINK}/files/${mainFile.curseFileId}")
    }
}

modrinth {
    token.set(GMUtils.locateProperty(project, "modrinth_token"))
    projectId.set(Properties.MODRINTH_PROJECT_ID)
    changelog.set(GMUtils.smallChangelog(project, Properties.GIT_REPO))
    versionName.set("NeoForge-${Versions.MINECRAFT}-$version")
    versionType.set("release")
    gameVersions.set(listOf(Versions.MINECRAFT))
    uploadFile.set(tasks.jar.get())
}
tasks.modrinth.get().dependsOn(tasks.jar)