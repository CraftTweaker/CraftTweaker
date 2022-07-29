import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions

plugins {
    id("com.blamejared.crafttweaker.default")
    id("com.blamejared.modtemplate")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

minecraft {
    version(Versions.MINECRAFT)
    accessWideners(project.file("src/main/resources/${Properties.MOD_ID}.accesswidener"))
}

dependencies {
    compileOnly("org.spongepowered:mixin:${Versions.MIXIN}")
}
