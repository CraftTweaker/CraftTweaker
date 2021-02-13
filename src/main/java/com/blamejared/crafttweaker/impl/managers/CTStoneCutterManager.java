package com.blamejared.crafttweaker.impl.managers;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionAddRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this stoneCutter
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.StoneCutterManager")
@Document("vanilla/api/managers/StoneCutterManager")
public class CTStoneCutterManager implements IRecipeManager {
    
    @ZenCodeGlobals.Global("stoneCutter")
    public static final CTStoneCutterManager INSTANCE = new CTStoneCutterManager();
    
    /**
     * Adds a recipe to the stone cutter
     *
     * @param recipeName name of the recipe
     * @param output     output {@link IItemStack}
     * @param input      input {@link IIngredient}
     *
     * @docParam recipeName "recipe_name"
     * @docParam output <item:minecraft:grass>
     * @docParam input <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    public void addRecipe(String recipeName, IItemStack output, IIngredient input) {
        recipeName = validateRecipeName(recipeName);
        CraftTweakerAPI.apply(new ActionAddRecipe(this, new StonecuttingRecipe(new ResourceLocation(CraftTweaker.MODID, recipeName), "", input.asVanillaIngredient(), output.getInternal()), ""));
    }
    
    @Override
    public IRecipeType<StonecuttingRecipe> getRecipeType() {
        return IRecipeType.STONECUTTING;
    }
}
