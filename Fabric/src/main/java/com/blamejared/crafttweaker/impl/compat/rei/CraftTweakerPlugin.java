package com.blamejared.crafttweaker.impl.compat.rei;

import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipeBase;
import com.blamejared.crafttweaker.impl.compat.rei.display.DefaultCTShapedDisplay;
import com.blamejared.crafttweaker.impl.compat.rei.display.DefaultCTShapelessDisplay;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.impl.client.registry.display.DisplayRegistryImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CraftTweakerPlugin implements REIClientPlugin {
    
    @SuppressWarnings("rawtypes")
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        
        // REI has a bug in their code that means that *all* CrafingRecipe's are
        // handled by their custom crafting recipe display, which decides to cause an AAOB.
        // So lets just do some *magic* to make our displays get registered first.
        if(registry instanceof DisplayRegistryImpl impl) {
            try {
                Field field = impl.getClass().getDeclaredField("fillers");
                field.setAccessible(true);
                List oldList = (List) field.get(impl);
                List savedList = new ArrayList(oldList);
                oldList.clear();
                registry.registerFiller(CTShapedRecipeBase.class, DefaultCTShapedDisplay::new);
                registry.registerFiller(CTShapelessRecipeBase.class, DefaultCTShapelessDisplay::new);
                oldList.addAll(savedList);
            } catch(IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
    
}
