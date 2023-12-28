package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class ScriptRecipeType implements RecipeType<ScriptRecipe> {
    
    public static final ScriptRecipeType INSTANCE = new ScriptRecipeType();
    
    private ScriptRecipeType() {}
    
    public ResourceLocation id() {
        
        return CraftTweakerConstants.rl("scripts");
    }
    
    @Override
    public String toString() {
        
        return id().toString();
    }
    
}
