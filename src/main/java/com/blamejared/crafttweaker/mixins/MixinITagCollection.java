package com.blamejared.crafttweaker.mixins;

import com.google.common.collect.*;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.Map;

@Mixin(targets = "net.minecraft.tags.ITagCollection$1")
public abstract class MixinITagCollection<T> {
    
    @Shadow(aliases = "val$bimap")
    private BiMap<ResourceLocation, ITag<T>> bimap;
    
    @Inject(method = "getIDTagMap", at = @At("RETURN"), cancellable = true)
    private void func_241833_a(CallbackInfoReturnable<Map<ResourceLocation, ITag<T>>> cir) {
        if(bimap instanceof ImmutableBiMap) {
            bimap = HashBiMap.create(bimap);
        }
        cir.setReturnValue(bimap);
    }
}
