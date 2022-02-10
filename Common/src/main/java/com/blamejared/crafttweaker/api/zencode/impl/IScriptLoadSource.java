package com.blamejared.crafttweaker.api.zencode.impl;

import net.minecraft.resources.ResourceLocation;

@FunctionalInterface
public interface IScriptLoadSource {
    
    ResourceLocation id();
    
}
