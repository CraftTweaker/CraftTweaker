package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.zencode.impl.IScriptLoadSource;
import net.minecraft.resources.ResourceLocation;

public record LoadSource(ResourceLocation id) implements IScriptLoadSource {}
