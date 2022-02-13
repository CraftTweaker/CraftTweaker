package com.blamejared.crafttweaker.mixin.client.transform.multiplayer;

import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistry;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.tags.TagContainer;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {
    
    @Shadow
    @Final
    private RecipeManager recipeManager;
    
    @Shadow private TagContainer tags;
    
    @Inject(method = "handleUpdateRecipes", at = @At("RETURN"))
    private void handleUpdateRecipes(ClientboundUpdateRecipesPacket clientboundUpdateRecipesPacket, CallbackInfo ci) {
        
        ScriptLoadingOptions.ClientScriptLoader.updateRecipes(() -> recipeManager);
    }
    
    @Inject(method = "handleUpdateTags", at = @At("RETURN"))
    private void handleUpdateTags(ClientboundUpdateTagsPacket packet, CallbackInfo ci) {
    
        if(Services.CLIENT.isSingleplayer()) {
            return;
        }
        
        CrTTagRegistry.INSTANCE.setCurrentTagContainer(() -> tags);
    }
    
}
