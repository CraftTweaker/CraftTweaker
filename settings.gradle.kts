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
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "net.minecraftforge.gradle") {
                useModule("${requested.id}:ForgeGradle:${requested.version}")
            }
            if (requested.id.id == "org.spongepowered.mixin") {
                useModule("org.spongepowered:mixingradle:${requested.version}")
            }
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
collectSubProjects("Annotations")

include("Common")
include("Fabric")
include("Forge")

fun collectSubProjects(folder: String) {

    File(rootDir, folder).listFiles(FileFilter {

        if (!it.isDirectory || excludedProjects.contains(it.name)) {
            return@FileFilter false
        }

        return@FileFilter File(it, "build.gradle").isFile || File(it, "build.gradle.kts").isFile
    })?.forEach {
        include(":${it.name}")
        project(":${it.name}").projectDir = File("./$folder/" + it.name)
    }
}
