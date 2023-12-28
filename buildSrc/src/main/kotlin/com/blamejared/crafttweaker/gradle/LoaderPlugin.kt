package com.blamejared.crafttweaker.gradle

import com.blamejared.gradle.mod.utils.GradleModUtilsPlugin
import com.blamejared.gradle.mod.utils.extensions.VersionTrackerExtension
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
        applyGradleModUtils(project)
    }

    private fun applyJavaPlugin(project: Project) {
        val commonJava = commonJava(project)

        project.tasks {
            withType<ProcessResources> {
                from(commonJava.sourceSets.getByName("main").resources)
            }

            withType<JavaCompile> {
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

            if (project.name == "forge") {
                withType(Jar::class.java) {
                    finalizedBy("reobfJar")
                }
            }

        }
    }

    private fun applyDependencies(project: Project) {
        val gametestCompileOnly = project.configurations.getByName("gametestCompileOnly")

        val common = project.project(":common")
        val commonJava = common.extensions.getByType(JavaPluginExtension::class.java)
        gametestCompileOnly.dependencies.add(project.dependencies.create(common))
        gametestCompileOnly.dependencies.add(project.dependencies.create(project.files(commonJava.sourceSets.getByName("gametest").java.srcDirs)))
    }

    private fun applyGradleModUtils(project: Project) {

        project.plugins.apply(GradleModUtilsPlugin::class.java)

        with(project.extensions.getByType(VersionTrackerExtension::class.java)) {
            mcVersion.set(Versions.MINECRAFT)
            homepage.set(Properties.CURSE_HOMEPAGE_LINK)
            author.set(Properties.MOD_AUTHOR)
            projectName.set(Properties.MOD_NAME)
        }
    }

    private fun common(project: Project): Project {
        return project.project(":common")
    }

    private fun commonJava(project: Project): JavaPluginExtension {
        return common(project).extensions.getByType(JavaPluginExtension::class.java)
    }

}
