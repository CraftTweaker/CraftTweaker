package com.blamejared.crafttweaker.impl.mod;

import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.mod.PlatformMod;

import java.nio.file.Path;

public record NeoForgeMod(Mod mod, Path modFile, Path modRoot) implements PlatformMod {}
