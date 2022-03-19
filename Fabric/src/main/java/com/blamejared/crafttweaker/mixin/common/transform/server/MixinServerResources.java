package com.blamejared.crafttweaker.mixin.common.transform.server;

import com.blamejared.crafttweaker.impl.loot.modifier.LootModifierManagerReloadListener;
import com.blamejared.crafttweaker.impl.script.ScriptReloadListenerAdapter;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableServerResources.class)
public abstract class MixinServerResources {
    
    @Unique
    private static ReloadableServerResources crafttweaker$currentResources;
    
    @Inject(method = "loadResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void crafttweaker$storeCurrentResources(ResourceManager resourceManager, RegistryAccess.Frozen frozen, Commands.CommandSelection commandSelection, int i, Executor executor, Executor executor2, CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> cir, ReloadableServerResources resources) {
        
        crafttweaker$currentResources = resources;
    }
    
    @ModifyArg(method = "loadResources", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/SimpleReloadInstance;create(Lnet/minecraft/server/packs/resources/ResourceManager;Ljava/util/List;Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Z)Lnet/minecraft/server/packs/resources/ReloadInstance;"))
    private static List<PreparableReloadListener> crafttweaker$addReloadListener(List<PreparableReloadListener> value) {
        
        ArrayList<PreparableReloadListener> listeners = new ArrayList<>(value);
        listeners.add(new LootModifierManagerReloadListener());
        listeners.add(new ScriptReloadListenerAdapter(crafttweaker$currentResources));
        return listeners;
    }
    
    @Inject(method = "loadResources", at = @At(value = "TAIL"))
    private static void crafttweaker$destroyCurrentResources(ResourceManager resourceManager, RegistryAccess.Frozen frozen, Commands.CommandSelection commandSelection, int i, Executor executor, Executor executor2, CallbackInfoReturnable<CompletableFuture<ReloadableServerResources>> cir) {
        
        crafttweaker$currentResources = null;
    }
    
}
