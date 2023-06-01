package com.blamejared.crafttweaker.mixin.common.transform.item;

import com.blamejared.crafttweaker.api.ingredient.IngredientCacheBuster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ingredient.class)
public class MixinIngredient {
    
    @Inject(method = "getItems", at = @At("HEAD"))
    public void crafttweaker$storeDissolve(CallbackInfoReturnable<ItemStack[]> cir) {
        
        if(IngredientCacheBuster.claimed()) {
            IngredientCacheBuster.store((Ingredient) (Object) this);
        }
    }
    
}
