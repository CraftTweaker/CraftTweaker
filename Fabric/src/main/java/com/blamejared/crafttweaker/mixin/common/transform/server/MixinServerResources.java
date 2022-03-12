package com.blamejared.crafttweaker.mixin.common.transform.server;

import com.blamejared.crafttweaker.impl.loot.modifier.LootModifierManagerReloadListener;
import com.blamejared.crafttweaker.impl.script.ScriptReloadListenerAdapter;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ServerResources;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResources.class)
public abstract class MixinServerResources {
    
    @Final
    @Shadow
    private ReloadableResourceManager resources;
    
    @Shadow
    public abstract RecipeManager getRecipeManager();
    
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void crafttweaker$init$injectScriptsListener(RegistryAccess registryAccess, Commands.CommandSelection commandSelection, int i, CallbackInfo ci) {
        
        this.resources.registerReloadListener(new LootModifierManagerReloadListener());
        this.resources.registerReloadListener(new ScriptReloadListenerAdapter(this::getRecipeManager));
    }
    
}
