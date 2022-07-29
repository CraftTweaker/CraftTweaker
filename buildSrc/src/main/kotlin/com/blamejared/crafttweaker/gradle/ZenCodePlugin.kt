package com.blamejared.crafttweaker.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.tasks.GenerateModuleMetadata
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import org.gradle.kotlin.dsl.withType
import java.nio.charset.StandardCharsets

class ZenCodePlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        setupDefaults(project)
        applyJavaPlugin(project)
    }

    private fun setupDefaults(project: Project) {
        project.group = "org.openzen.zencode"
        project.version = Versions.ZENCODE

        project.tasks.withType<GenerateModuleMetadata>().all {
            enabled = false
        }

        project.repositories {
            mavenCentral();
        }

    }

    private fun applyJavaPlugin(project: Project) {
        project.plugins.apply(JavaLibraryPlugin::class.java)

        with(project.extensions.getByType(JavaPluginExtension::class.java)) {
            toolchain.languageVersion.set(JavaLanguageVersion.of(Versions.ZENCODE_JAVA))
            withSourcesJar()
        }

        project.tasks {
            withType<JavaCompile>().configureEach {
                this.options.encoding = StandardCharsets.UTF_8.toString();
            }
        }
    }

}
