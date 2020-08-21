package mixin;

import com.google.common.collect.HashBiMap;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(targets = "net.minecraft.tags.ITagCollection$1")
public abstract class MixinITagCollection {
    
    
    @Inject(method = "func_241833_a", at = @At("RETURN"))
    private <T> void func_241833_a(CallbackInfoReturnable<Map<ResourceLocation, ITag<T>>> cir) {
        cir.setReturnValue(HashBiMap.create());
    }
}
