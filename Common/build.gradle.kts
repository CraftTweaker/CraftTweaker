import com.blamejared.crafttweaker.gradle.Properties
import com.blamejared.crafttweaker.gradle.Versions

buildscript {
    dependencies.add("classpath", "org.spongepowered:vanillagradle:0.2.1-20240507.024226-82")
}

plugins {
    id("com.blamejared.crafttweaker.default")
    id("com.blamejared.modtemplate")
    id("org.spongepowered.gradle.vanilla") version "0.2.1-20240507.024226-82"
}

minecraft {
    version(Versions.MINECRAFT)
    accessWideners(project.file("src/main/resources/${Properties.MOD_ID}.accesswidener"))
}

dependencies {
    compileOnly("org.spongepowered:mixin:${Versions.MIXIN}")
}
