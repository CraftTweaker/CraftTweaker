package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.ICookingRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SmokingRecipe;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this smoker
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.SmokerManager")
@Document("vanilla/api/managers/SmokerManager")
public class CTSmokerManager implements ICookingRecipeManager {
    
    @ZenCodeGlobals.Global("smoker")
    public static final CTSmokerManager INSTANCE = new CTSmokerManager();
    
    private CTSmokerManager() {
    }
    
    @Override
    public AbstractCookingRecipe makeRecipe(String name, IItemStack output, IIngredient input, float xp, int cookTime) {
        return new SmokingRecipe(new ResourceLocation(CraftTweaker.MODID, name), "", input.asVanillaIngredient(), output.getInternal(), xp, cookTime);
    }
    
    @Override
    public IRecipeType getRecipeType() {
        return IRecipeType.SMOKING;
    }
    
}
