package com.blamejared.crafttweaker.impl.loot.modifier;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.loot.LootModifierManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public final class LootModifierManagerReloadListener extends SimplePreparableReloadListener<Void> implements IdentifiableResourceReloadListener {
    
    public static final ResourceLocation RELOAD_LISTENER_ID = new ResourceLocation(CraftTweakerConstants.MOD_ID, "loot_modifiers");
    
    @Override
    public ResourceLocation getFabricId() {
        
        return RELOAD_LISTENER_ID;
    }
    
    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        
        return null;
    }
    
    @Override
    protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
        
        LootModifierManager.INSTANCE.modifiers().clear();
    }
    
}
