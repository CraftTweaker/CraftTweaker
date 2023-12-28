package com.blamejared.crafttweaker.mixin.common.access.server;

import net.minecraft.server.ReloadableServerResources;
import net.minecraft.tags.TagManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ReloadableServerResources.class)
public interface AccessReloadableServerResources {
    
    @Accessor("tagManager")
    TagManager crafttweaker$getTagManager();
    
}
