package com.blamejared.crafttweaker.mixin.common.access.brewing;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(PotionBrewing.class)
public interface AccessPotionBrewing {
    
    @Accessor("POTION_MIXES")
    static List<PotionBrewing.Mix<Potion>> crafttweaker$getPOTION_MIXES() {
        
        throw new UnsupportedOperationException();
    }
    
    @Accessor("CONTAINER_MIXES")
    static List<PotionBrewing.Mix<Item>> crafttweaker$getCONTAINER_MIXES() {
        
        throw new UnsupportedOperationException();
    }
    
    @Invoker("addMix")
    static void crafttweaker$callAddMix(Potion pPotionEntry, Item pPotionIngredient, Potion pPotionResult) {throw new UnsupportedOperationException();}
    
}
