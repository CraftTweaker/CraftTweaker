import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import com.blamejared.gradle.mod.utils.GMUtils
import com.diluv.schoomp.Webhook
import com.diluv.schoomp.message.Message
import com.diluv.schoomp.message.embed.Embed
import java.io.IOException
import java.util.*

plugins {
    java
    idea
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
}

version = GMUtils.updatingVersion(Versions.MOD)

tasks.wrapper {
    //Define wrapper values here to not have to always do so when updating gradlew.properties
    gradleVersion = "8.5"
    distributionType = Wrapper.DistributionType.BIN
}

subprojects {

    // We can't add this plugin to the ZC build.gradle
    if (project.projectDir.parent == rootProject.file("ZenCode").absolutePath) {
        apply(plugin = "crafttweaker.zencode-conventions")
    }
}

tasks.create("gameTest") {
    dependsOn(":fabric:runGameTest", ":forge:GameTest", ":neoforge:runGameTestServer")
    group = "verification"
}

tasks.create("postDiscord") {
    val taskName = "publishCurseForge"
    dependsOn(":fabric:${taskName}", ":forge:${taskName}", ":neoforge:${taskName}")
    doLast {
        try {

            // Create a new webhook instance for Discord
            val webhook = Webhook(GMUtils.locateProperty(project, "discordCFWebhook"), "${Properties.MOD_NAME} CurseForge Gradle Upload")

            // Craft a message to send to Discord using the webhook.
            val message = Message()
            message.username = Properties.MOD_NAME
            message.avatarUrl = Properties.MOD_AVATAR
            message.content = "${Properties.MOD_NAME} $version for Minecraft ${Versions.MINECRAFT} has been published!"

            val embed = Embed()
            val downloadSources = StringJoiner("\n")

            mapOf(Pair("fabric", "<:fabric:932163720568782878>"), Pair("forge", "<:forge:932163698003443804>"), Pair("neoforge", "<:neoforged:1184738260371644446>"))
                    .filter {
                        project(":${it.key}").ext.has("curse_file_url")
                    }.map { "${it.value} [${it.key.capitalize(Locale.ENGLISH)}](${project(":${it.key}").ext.get("curse_file_url")})" }
                    .forEach { downloadSources.add(it) }

            listOf("common", "fabric", "forge", "neoforge")
                    .map { project(":${it}") }
                    .map { "<:maven:932165250738970634> `\"${it.group}:${it.base.archivesName.get()}:${it.version}\"`" }
                    .forEach { downloadSources.add(it) }

            // Add Curseforge DL link if available.
            val downloadString = downloadSources.toString()

            if (downloadString.isNotEmpty()) {

                embed.addField("Download", downloadString, false)
            }

            embed.addField("Changelog", GMUtils.smallChangelog(project, Properties.GIT_REPO).take(1000), false)

            embed.color = 0xF16436
            message.addEmbed(embed)

            webhook.sendMessage(message)
        } catch (e: IOException) {

            project.logger.error("Failed to push CF Discord webhook.")
            project.file("post_discord_error.log").writeText(e.stackTraceToString())
        }
    }

}

val apDir = "CraftTweaker-Annotation-Processors"

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