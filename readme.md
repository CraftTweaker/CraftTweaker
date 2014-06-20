# MineTweaker 3

Welcome to the MineTweaker 3 source! MineTweaker 3 is the new version of MineTweaker and will be released for both 1.7.2 and 1.6.4, as well as upgraded as new versions of Minecraft become available.

## License and contributions

The source code is freely available in here, at GitHub. You may modify and redistribute it as long as your mark your own version of MineTweaker as CUSTOMIZED (that is, put CUSTOMIZED in both the jar and mod name). The mod itself may be redistributed in modpacks as long as these modpacks are distributed for free. No money can be made from the distribution of the mod.

Want to contribute? That's great! Contact me if you want to propose or discuss new features. Adding new mods is definitely welcome and doesn't need prior permission (just follow the existing structure, add it & make a pull request). Credit will be granted appropriately.

## Project structure and building

The MineTweaker 3 source is setup as a multi-project Gradle project:

- ZenScript is the custom scripting engine
- MineTweaker3-API is the shared API for all MineTweaker versions
- MineTweaker3-MC17-Main is the main MineTweaker implementation for Minecraft 1.7
- MineTweaker3-MC17-Mod-XXX are the mod support projects. They are compiled and obfuscated independently and then assembled later

### Developing and running MineTweaker from source

In order to run the project, you have to execute the setupDecompWorkspace in the MineTweaker3-MC17-Main project. It will prepare a running environment for you. After that, you can simply run minecraft with the runClient task on MineTweaker3-MC17-Main. It will run MineTweaker. (without its mod support libraries)

If you want to work on a specific mod support library, perform the setupDecompWorkspace on that specific mod support and run that project with the runClient task. It will then run MineTweaker + that specific mod support library.

### Adding mod support

When starting a new mod support library, it is best to start from an existing one. For instance, copy the NEI support library, and remove the source files inside the src/java/main directory. Then you can work with it like any other support library.

Before it can be released, you will have to modify the assembleMC17 task to include the new mod support.

### Building a release

The assembleMC17 task is the main building task for building a reobfuscated jar combining all the different mod support libraries into one. The jar will be stored in build/libs/MineTweaker3.jar .
