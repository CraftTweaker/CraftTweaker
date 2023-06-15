import java.io.FileFilter

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "Sponge Snapshots"
        }
        maven("https://maven.minecraftforge.net") {
            name = "Forge"
        }
        maven("https://maven.parchmentmc.org") {
            name = "ParchmentMC"
        }
        maven("https://maven.blamejared.com") {
            name = "BlameJared"
        }
    }
}

rootProject.name = "CraftTweaker"
val excludedProjects = setOf(
        "ModuleDeserializer",
        "ModuleSerializationShared",
        "ModuleSerializer",
        "JavaSource",
        "JavaSourceCompiler",
        "IDE",
        "Constructor",
        "DrawableGui",
        "DrawableGuiIconConverter",
        "CompilerShared"
)

include("ZenCode")
collectSubProjects("ZenCode")

include("Common")
include("Fabric")
include("Forge")

if (file("CraftTweaker-Annotation-Processors").exists()) {
    includeBuild("CraftTweaker-Annotation-Processors") {
        dependencySubstitution {
            substitute(module("com.blamejared.crafttweaker:Crafttweaker_Annotation_Processors")).using(project(":"))
        }
    }
}

fun collectSubProjects(folder: String) {

    file(folder).listFiles(FileFilter {

        if (!it.isDirectory || excludedProjects.contains(it.name)) {
            return@FileFilter false
        }

        return@FileFilter File(it, "build.gradle").isFile || File(it, "build.gradle.kts").isFile
    })?.forEach {
        include(":${it.name}")
        project(":${it.name}").projectDir = File("./$folder/" + it.name)
    }
}
