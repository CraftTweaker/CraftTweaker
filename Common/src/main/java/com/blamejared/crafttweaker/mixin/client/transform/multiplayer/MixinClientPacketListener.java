package com.blamejared.crafttweaker.mixin.client.transform.multiplayer;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.impl.script.RecipeManagerScriptLoader;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.protocol.common.ClientboundUpdateTagsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public abstract class MixinClientPacketListener {
    
    // TODO 1.20.2 This is now frozen????? We need to use the configuration phase or something??
    @Shadow
    protected abstract RegistryAccess.Frozen registryAccess();
    
    @Inject(method = "handleUpdateTags", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/common/ClientboundUpdateTagsPacket;getTags()Ljava/util/Map;"))
    private void crafttweaker$handleUpdateTags(ClientboundUpdateTagsPacket packet, CallbackInfo ci) {
        
        CraftTweakerAPI.getAccessibleElementsProvider().client().registryAccess(this.registryAccess());
        
        CraftTweakerTagRegistry.INSTANCE.bind(packet.getTags());
        
        RecipeManagerScriptLoader.updateState(RecipeManagerScriptLoader.UpdatedState.TAGS, null);
    }
    
}
