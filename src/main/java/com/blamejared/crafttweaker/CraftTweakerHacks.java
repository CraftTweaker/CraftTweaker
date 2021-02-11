package com.blamejared.crafttweaker;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.NBTIngredient;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * some functions that need reflect.
 */
public class CraftTweakerHacks {
    
    private static final Constructor<NBTIngredient> NBT_INGREDIENT_CONSTRUCTOR;
    private static final Constructor<CompoundIngredient> COMPOUND_INGREDIENT_CONSTRUCTOR;
    
    private static final Logger LOGGER = Logger.getLogger(CraftTweakerHacks.class.getName());
    
    static {
        NBT_INGREDIENT_CONSTRUCTOR = getConstructor(NBTIngredient.class, ItemStack.class);
        COMPOUND_INGREDIENT_CONSTRUCTOR = getConstructor(CompoundIngredient.class, List.class);
    }
    
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
        } catch(NoSuchMethodException | SecurityException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return constructor;
    }
    
    public static NBTIngredient createNBTIngredient(ItemStack stack) {
        
        try {
            return NBT_INGREDIENT_CONSTRUCTOR.newInstance(stack);
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public static CompoundIngredient createCompoundIngredient(List<Ingredient> ingredients) {
        
        try {
            return COMPOUND_INGREDIENT_CONSTRUCTOR.newInstance(ingredients);
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return null;
    }
    
}
