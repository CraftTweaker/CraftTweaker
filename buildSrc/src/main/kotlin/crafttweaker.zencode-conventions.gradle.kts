import com.blamejared.crafttweaker.gradle.Dependencies
import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import com.blamejared.gradle.mod.utils.GMUtils
import gradle.kotlin.dsl.accessors._8347e3f88f0262002bff02c22a2fad22.*
import gradle.kotlin.dsl.accessors._8347e3f88f0262002bff02c22a2fad22.base
import gradle.kotlin.dsl.accessors._8347e3f88f0262002bff02c22a2fad22.java
import gradle.kotlin.dsl.accessors._8347e3f88f0262002bff02c22a2fad22.main
import gradle.kotlin.dsl.accessors._8347e3f88f0262002bff02c22a2fad22.sourceSets
import org.gradle.api.JavaVersion
import org.gradle.api.Task
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.tasks.GenerateModuleMetadata
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.StandardJavadocDocletOptions
import org.gradle.jvm.tasks.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

plugins {
    base
    `java-library`
}

base.archivesName.set("${Properties.MOD_NAME}-${project.name.lowercase()}-${Versions.MINECRAFT}")
group = "org.openzen.zencode"
version = Versions.ZENCODE

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(Versions.ZENCODE_JAVA))
    withSourcesJar()
}

@Suppress("UnstableApiUsage")
repositories {
    mavenCentral()
}

tasks {
    withType<GenerateModuleMetadata>().configureEach {
        enabled = false
    }
    withType<JavaCompile>().configureEach {
        options.encoding = StandardCharsets.UTF_8.toString()
    }
}
