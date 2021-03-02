package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.IngredientAny;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IngredientAny")
@Document("vanilla/api/items/IngredientAny")
public class MCIngredientAny implements IIngredient {
    private static final MCIngredientAny INSTANCE = new MCIngredientAny();
    private static final IItemStack[] ALL_ITEMS = CraftTweakerAPI.game.getMCItemStacks().toArray(new IItemStack[0]);

    private MCIngredientAny() {}

    @ZenCodeType.Method
    public static MCIngredientAny getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        return stack != null && !stack.isEmpty();
    }

    @Override
    public Ingredient asVanillaIngredient() {
        return IngredientAny.INSTANCE;
    }

    @Override
    public String getCommandString() {
        return "IngredientAny.getInstance()";
    }

    @Override
    public IItemStack[] getItems() {
        return ALL_ITEMS;
    }
}
