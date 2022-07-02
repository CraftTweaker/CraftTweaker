package com.blamejared.crafttweaker.mixin.common.transform.item;

import com.blamejared.crafttweaker.api.ingredient.IngredientCacheBuster;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Ingredient.class)
public class MixinIngredient {
    
    @Inject(method = "dissolve", at = @At("HEAD"))
    public void crafttweaker$storeDissolve(CallbackInfo ci) {
        
        if(IngredientCacheBuster.claimed()) {
            IngredientCacheBuster.store((Ingredient) (Object) this);
        }
    }
    
}
