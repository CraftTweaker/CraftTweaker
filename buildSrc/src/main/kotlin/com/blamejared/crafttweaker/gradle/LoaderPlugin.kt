package com.blamejared.crafttweaker.gradle

import com.blamejared.modtemplate.ModTemplatePlugin
import com.blamejared.modtemplate.extensions.ModTemplateExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.gradle.language.jvm.tasks.ProcessResources

class LoaderPlugin : Plugin<Project> {
    private lateinit var gametestLibrary: Configuration
    private lateinit var library: Configuration

    override fun apply(project: Project): Unit = project.run {

        applyJavaPlugin(project)
        applyDependencies(project)
        applyModTemplate(project)
    }

    private fun applyJavaPlugin(project: Project) {
        val commonJava = commonJava(project)

        project.tasks {
            withType<ProcessResources>() {
                from(commonJava.sourceSets.getByName("main").resources)
            }

            withType<JavaCompile>() {
                source(commonJava(project).sourceSets.getByName("main").allSource)
            }

            withType<Javadoc> {
                source(commonJava(project).sourceSets.getByName("main").allJava)
            }

            named<Jar>("sourcesJar") {
                from(commonJava(project).sourceSets.getByName("main").allSource)
            }

            named("compileGametestJava", JavaCompile::class.java) {
                source(commonJava.sourceSets.getByName("gametest").java)
            }

            named("processGametestResources", ProcessResources::class.java) {
                outputs.upToDateWhen { false }
                from(commonJava.sourceSets.getByName("gametest").resources)
            }

            withType(ProcessResources::class.java) {
                from(commonJava.sourceSets.getByName("main").resources)
            }

            if (project.name.equals("Forge")) {
                withType(Jar::class.java) {
                    finalizedBy("reobfJar")
                }
            }

        }
    }

    private fun applyDependencies(project: Project) {
        val gametestCompileOnly = project.configurations.getByName("gametestCompileOnly")

        val common = project.project(":Common")
        val commonJava = common.extensions.getByType(JavaPluginExtension::class.java)
        gametestCompileOnly.dependencies.add(project.dependencies.create(common))
        gametestCompileOnly.dependencies.add(project.dependencies.create(project.files(commonJava.sourceSets.getByName("gametest").java.srcDirs)))
    }

    private fun applyModTemplate(project: Project) {

        project.plugins.apply(ModTemplatePlugin::class.java)

        with(project.extensions.getByType(ModTemplateExtension::class.java)) {
            mcVersion(Versions.MINECRAFT)
            curseHomepage(Properties.CURSE_HOMEPAGE_LINK)
            displayName(Properties.MOD_NAME)
            modLoader(project.name)
            changelog {
                // Don't register the task since we will never use it, but the properties are used
                enabled(false)
                firstCommit(Properties.GIT_FIRST_COMMIT)
                repo(Properties.GIT_REPO)
            }

            versionTracker {
                enabled(true)
                endpoint(System.getenv("versionTrackerAPI"))
                author(Properties.MOD_AUTHOR)
                projectName("${Properties.MOD_NAME}-${project.name}")
                homepage(Properties.CURSE_HOMEPAGE_LINK)
                uid(System.getenv("versionTrackerKey"))
            }

        }
//
//        final modTemplate = project . extensions . findByType ModTemplateExtension
//        modTemplate.mcVersion(ext['minecraft.version'])
//        modTemplate.curseHomepage(ext['mod.curse'])
//        modTemplate.displayName(ext['mod.name'])
//        modTemplate.changelog.with {
//            firstCommit(ext['mod.first-commit'])
//            repo(ext['mod.repo'])
//            changelogFile('changelog.md')
//        }
//        modTemplate.versionTracker.with {
//            endpoint(System.getenv('versionTrackerAPI'))
//            author(ext['mod.author'])
//            projectName(ext['mod.name'])
//            homepage(ext['mod.curse'])
//        }
//        modTemplate.webhook.with {
//            url(System.getenv('discordCFWebhook'))
//            curseId(ext['mod.curse-id'])
//            avatarUrl(ext['mod.avatar'])
//        }
    }

    private fun notCommon(project: Project): Boolean {
        return !project.name.equals("Common")
    }

    private fun common(project: Project): Project {
        return project.project(":Common")
    }

    private fun commonJava(project: Project): JavaPluginExtension {
        return common(project).extensions.getByType(JavaPluginExtension::class.java)
    }

    private fun depJava(project: Project, other: String): JavaPluginExtension {
        return project.project(other).extensions.getByType(JavaPluginExtension::class.java)
    }
}
