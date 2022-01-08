package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ScriptSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements IScriptSerializer {
    
    public static final ScriptSerializer INSTANCE = new ScriptSerializer();
    
    public ScriptSerializer() {
        
        setRegistryName(CraftTweakerConstants.rl("script"));
    }
    
}
