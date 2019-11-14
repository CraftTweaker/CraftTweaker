package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.ICookingRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this blastFurnace
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BlastFurnaceManager")
@Document("vanilla/managers/BlastFurnaceManager")
public class CTBlastFurnaceManager implements ICookingRecipeManager {
    
    @ZenCodeGlobals.Global("blastFurnace")
    public static final CTBlastFurnaceManager INSTANCE = new CTBlastFurnaceManager();
    
    private CTBlastFurnaceManager() {
    }
    
    @Override
    public AbstractCookingRecipe makeRecipe(String name, IItemStack output, IIngredient input, float xp, int cookTime) {
        return new BlastingRecipe(new ResourceLocation(CraftTweaker.MODID, name), "", input.asVanillaIngredient(), output.getInternal(), xp, cookTime);
    }
    
    @Override
    public IRecipeType getRecipeType() {
        return IRecipeType.BLASTING;
    }
    
}
