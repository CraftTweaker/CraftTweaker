package com.blamejared.crafttweaker.mixin.client.transform.multiplayer;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.impl.script.RecipeManagerScriptLoader;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
    
    @Shadow
    public abstract RegistryAccess registryAccess();
    
    @Inject(method = "handleUpdateTags", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundUpdateTagsPacket;getTags()Ljava/util/Map;"))
    private void crafttweaker$handleUpdateTags(ClientboundUpdateTagsPacket packet, CallbackInfo ci) {
        
        CraftTweakerAPI.getAccessibleElementsProvider().client().registryAccess(this.registryAccess());
        
        CraftTweakerTagRegistry.INSTANCE.bind(packet.getTags());
        
        RecipeManagerScriptLoader.updateState(RecipeManagerScriptLoader.UpdatedState.TAGS, null);
    }
    
}
