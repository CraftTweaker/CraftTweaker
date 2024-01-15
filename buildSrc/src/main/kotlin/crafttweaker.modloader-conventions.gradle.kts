import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions
import org.gradle.jvm.tasks.Jar

plugins {
    id("crafttweaker.java-conventions")
    id("com.blamejared.gradle-mod-utils")
    id("com.modrinth.minotaur")
    id("net.darkhax.curseforgegradle")
}

dependencies {
    "gametestCompileOnly"(project(":common"))
}

tasks {
    withType<ProcessResources>().matching { notNeoTask(it) }.configureEach {
        from(project(":common").sourceSets.main.get().resources)
    }

    withType<JavaCompile>().matching { notNeoTask(it) }.configureEach {
        source(project(":common").sourceSets.main.get().allSource)
    }

    withType<Javadoc>().matching { notNeoTask(it) }.configureEach {
        source(project(":common").sourceSets.main.get().allJava)
    }

    named<Jar>("sourcesJar") {
        from(project(":common").sourceSets.main.get().allSource)
    }

    named("compileGametestJava", JavaCompile::class.java) {
        source(project(":common").sourceSets.getByName("gametest").java)
    }

    named("processGametestResources", ProcessResources::class.java) {
        from(project(":common").sourceSets.getByName("gametest").resources)
    }

    if (project.name == "forge") {
        withType(Jar::class.java) {
            finalizedBy("reobfJar")
        }
    }

}

versionTracker {
    mcVersion.set(Versions.MINECRAFT)
    homepage.set(Properties.CURSE_HOMEPAGE_LINK)
    author.set(Properties.SIMPLE_AUTHOR)
    projectName.set(Properties.MOD_NAME)
}

fun notNeoTask(task: Task): Boolean {
    return !task.name.startsWith("neo")
}