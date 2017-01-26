# CraftTweaker

Welcome to the Crafttwaeker source! Crafttweaker is the official continuation of Minetweaker 3 and plans on staying updated for the latest Minecraft versions.

## License and contributions

The mod is licensed under the MIT license with the one stipulation that the name has to be changed, the reason for this is so that we do not have to deal with code that we didn't write.

The mod itself may be redistributed in modpacks as long as these modpacks are distributed for free. No money can be made from the distribution of the mod.

Want to contribute? That's great! Contact me if you want to propose or discuss new features. Adding new mods is definitely welcome and doesn't need prior permission (just follow the existing structure, add it & make a pull request). Credit will be granted appropriately.

## Project structure and building

The MineTweaker 3 source is setup as a multi-project Gradle project:

- ZenScript is the custom scripting engine
- MineTweaker3-API is the shared API for all MineTweaker versions
- MineTweaker3-MCXYZ-Main are the main MineTweaker implementations for Minecraft X.Y.Z
- MineTweaker3-MCXYZ-Mod-XXX are the mod support projects. They are compiled and obfuscated independently and then assembled later

### Developing and running MineTweaker from source

Configuration settings are stored in configuration.gradle.

In order to use the project, you have to execute the setupDecompWorkspaceAll. It will prepare all the subprojects for you. After that, you can simply run minecraft with the runClient task in MineTweaker3-XYZ-Main project, which will run MineTweaker (without mod support). Likewise, you can use the runClient task on any of its mod support library subprojects, which will run MineTweaker with only the mod support for that specific mod.

The following global tasks are available:

- setupDecompWorkspaceAll: sets up all workspaces in all subprojects
- assembleMC1102: builds MineTweaker for Minecraft 1.10.2
- assembleAll: builds all versions of MineTweaker

### Adding mod support

When starting a new mod support library, it is best to start from an existing one. For instance, copy the NEI support library, and remove the source files inside the src/java/main directory. Then you can work with it like any other support library. Make sure to update configuration.gradle to add the projects in there.

### Building a release

Use the assembleAll task to build all versions, or assembleMCxxx tasks to build a specific version. The files will be output in /build/libs .

## Customized builds

You may modify and redistribute your own customized version of MineTweaker as long as you mark your own version of MineTweaker as CUSTOMIZED (that is, put CUSTOMIZED in both the jar and mod name) and as long as you make the modified source code publicly available. Obviously, I cannot offer support for customized builds. Just be so kind to mention the original source and author (Stan) ;)
