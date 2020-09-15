<p align="center"><img width=12.5% src="https://i.blamejared.com/crafttweaker.svg"></p>
<p align="center"><img width=60% src="https://i.blamejared.com/crafttweaker_banner.png"></p>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![GitHub issues](https://img.shields.io/github/issues/CraftTweaker/CraftTweaker?style=flat-square)](https://github.com/CraftTweaker/CraftTweaker/issues)
[![GitHub license](https://img.shields.io/github/license/CraftTweaker/CraftTweaker?color=0690ff&style=flat-square)](https://github.com/CraftTweaker/CraftTweaker/blob/1.16/LICENSE)
![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.blamejared.com%2Fjob%2FCraftTweaker%2Fjob%2F1.16%2F&style=flat-square)
[![Maven](https://img.shields.io/maven-metadata/v?color=C71A36&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fcom%2Fblamejared%2Fcrafttweaker%2FCraftTweaker-1.16.3%2Fmaven-metadata.xml&style=flat-square)](https://maven.blamejared.com/com/blamejared/crafttweaker/)
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

CraftTweaker is a Minecraft mod which allows modpacks and servers to customize the game. With CraftTweaker you can change recipes, script events, add new commands and even change item properties! When used with other mods the possibilities become endless. Ranging from custom machine recipes to entirely new blocks and items!

## Feedback

If you're looking for help with the mod, or just want to come hang out, we have a [Discord](https://discord.blamejared.com). If you're running into a bug or have a feature request, please don't be afraid to make an [issue on the tracker](https://github.com/CraftTweaker/CraftTweaker/issues).

## License

Distributed under the MIT License. See the [LICENSE](https://github.com/CraftTweaker/CraftTweaker/blob/1.16/LICENSE) file for more information.


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
and the outputted `.jar` files will be put in `build/libs/`.

CraftTweaker also has automated markdown documentation, the output of that will be in the `docsOut/` folder.

## Maven


Every push to this repository is built and published to the [BlameJared](https://maven.blamejared.com) maven, to use this builds in your project, simply use the following code in your build.gradle

```gradle
repositories {
    maven { url 'https://maven.blamejared.com' }
}

dependencies {
    runtimeOnly fg.deobf("com.blamejared.crafttweaker:CraftTweaker-1.16.3:[VERSION]")
}
```

Just replace `[VERSION]` with the latest released version, which is currently:

 [![Maven](https://img.shields.io/maven-metadata/v?label=&color=C71A36&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fcom%2Fblamejared%2Fcrafttweaker%2FCraftTweaker-1.16.3%2Fmaven-metadata.xml&style=flat-square)](https://maven.blamejared.com/com/blamejared/crafttweaker/)

 Simply remove the `v` and use that version, so `v7.0.0.0` becomes `7.0.0.0`
