<p align="center"><img width=12.5% src="https://i.blamejared.com/crafttweaker.svg"></p>
<p align="center"><img width=60% src="https://i.blamejared.com/crafttweaker_banner.png"></p>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![GitHub issues](https://img.shields.io/github/issues/CraftTweaker/CraftTweaker?style=flat-square)](https://github.com/CraftTweaker/CraftTweaker/issues)
[![GitHub license](https://img.shields.io/github/license/CraftTweaker/CraftTweaker?color=0690ff&style=flat-square)](https://github.com/CraftTweaker/CraftTweaker/blob/1.19/LICENSE)
[![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.blamejared.com%2Fjob%2FJared%2Fjob%2FMinecraft%2520Mods%2Fjob%2FCraftTweaker%2Fjob%2F1.19%2F&style=flat-square)](https://ci.blamejared.com/job/Jared/job/Minecraft%20Mods/job/CraftTweaker/job/1.19/)
[![Discord](https://img.shields.io/badge/Discord-%237289DA?style=flat-square&logo=Discord&logoColor=white)](https://discord.blamejared.com/)
[![](http://cf.way2muchnoise.eu/crafttweaker.svg?badge_style=flat)](https://minecraft.curseforge.com/projects/crafttweaker)

## Table of Contents

- [Introduction](#introduction)
- [Feedback](#feedback)
- [License](#license)
- [Setup](#setup)
- [Build](#build)
- [Maven](#maven)

## Introduction

CraftTweaker is a Minecraft mod which allows modpacks and servers to customize the game. With CraftTweaker you can change recipes, script events, add new commands and even change item properties! When used with other mods the possibilities become endless! Ranging from custom machine recipes to entirely new blocks and items!

## Feedback

If you're looking for help with the mod, or just want to come hang out, we have a [Discord server](https://discord.blamejared.com).  
If you're running into a bug or have a feature request, please don't be afraid to make an [issue on the tracker](https://github.com/CraftTweaker/CraftTweaker/issues).

## License

Distributed under the MIT License. See the [LICENSE](https://github.com/CraftTweaker/CraftTweaker/blob/1.19/LICENSE) file for more information.

## Setup

To set up the CraftTweaker development environment you must clone the repo and initialize the submodule.

```bash
git clone --recurse-submodules https://github.com/CraftTweaker/CraftTweaker.git
```

After the project has been cloned and initialized you can directly import it into your IDE of choice.

## Build

Building the project is as easy as running a Gradle command!
Simply run:
```bash
gradlew build
```
and the outputted `.jar` files will be put in `build/libs/` folder of each subproject (`Common/build/libs/`, `Fabric/build/libs/` and `Forge/build/libs/`).

CraftTweaker also has automated markdown documentation, the output of that will be in the `docsOut/` folder.

## Maven

Every push to this repository is built and published to the [BlameJared](https://maven.blamejared.com) maven, to use these builds in your project, first add the BlameJared maven to your `repositories` block in your build.gradle file like so:

```groovy
repositories {
    maven { 
        url = 'https://maven.blamejared.com'
        name = 'BlameJared Maven'
    }
}
```

Then, depending on what modloader you are using, you can use the following snippets, just replace `[VERSION]` with the latest version for each artifact.

### Fabric [![Maven](https://img.shields.io/maven-metadata/v?color=C71A36&label=Latest%20version&logo=Latest%20version&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fcom%2Fblamejared%2Fcrafttweaker%2FCraftTweaker-fabric-1.19.3%2Fmaven-metadata.xml&style=flat-square)](https://maven.blamejared.com/com/blamejared/crafttweaker/CraftTweaker-fabric-1.19.3/)

```groovy
dependencies {
    modCompileOnly('com.blamejared.crafttweaker:CraftTweaker-fabric-1.19.3:[VERSION]')
    // Example:
    // modCompileOnly('com.blamejared.crafttweaker:CraftTweaker-fabric-1.19.3:11.0.0')
}
```

### Forge [![Maven](https://img.shields.io/maven-metadata/v?color=C71A36&label=Latest%20version&logo=Latest%20version&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fcom%2Fblamejared%2Fcrafttweaker%2FCraftTweaker-forge-1.19.3%2Fmaven-metadata.xml&style=flat-square)](https://maven.blamejared.com/com/blamejared/crafttweaker/CraftTweaker-forge-1.19.3/)


```groovy
dependencies {
    compileOnly(fg.deobf('com.blamejared.crafttweaker:CraftTweaker-forge-1.19.3:[VERSION]'))
    // Example:
    // compileOnly(fg.deobf('com.blamejared.crafttweaker:CraftTweaker-forge-1.19.3:11.0.0'))
}
```

### Common [![Maven](https://img.shields.io/maven-metadata/v?color=C71A36&label=Latest%20version&logo=Latest%20version&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fcom%2Fblamejared%2Fcrafttweaker%2FCraftTweaker-common-1.19.3%2Fmaven-metadata.xml&style=flat-square)](https://maven.blamejared.com/com/blamejared/crafttweaker/CraftTweaker-common-1.19.3/)

If you are in a multi-modloader environment (Such as [MultiLoader](https://github.com/jaredlll08/MultiLoader-Template)), you can bring the Common artifact (code that does not depend on any specific mod loader but rather just the vanilla game) into your Common project like so:

```groovy
dependencies {
    compileOnly('com.blamejared.crafttweaker:CraftTweaker-common-1.19.3:[VERSION]')
    // Example:
    // compileOnly('com.blamejared.crafttweaker:CraftTweaker-common-1.19.3:11.0.0')
}
```
