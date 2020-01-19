package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.JSONConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.item.MCIngredientList;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeHooks;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;


/**
 * This is IIngredient!!!
 *
 * @docParam this <tag:ingotIron>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IIngredient")
@Document("vanilla/api/items/IIngredient")
@ZenWrapper(wrappedClass = "net.minecraft.item.crafting.Ingredient", conversionMethodFormat = "%s.asVanillaIngredient()", displayStringFormat = "%.getCommandString()")
public interface IIngredient extends CommandStringDisplayable {
    
    /**
     * Does the given stack match the ingredient?
     *
     * @param stack The stack to check
     *
     * @docParam stack <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    boolean matches(IItemStack stack);
    
    /**
     * Create a Vanilla ingredient matching this one.
     */
    Ingredient asVanillaIngredient();
    
    /**
     * When this ingredient stack is crafted, what will remain in the grid?
     * Does not check if the stack matches though!
     * Used e.g. in CrT's {@link net.minecraft.item.crafting.ICraftingRecipe#getRemainingItems}
     *
     * @param stack The stack to provide for this ingredient.
     *
     * @docParam stack <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    default IItemStack getRemainingItem(IItemStack stack) {
        return new MCItemStack(ForgeHooks.getContainerItem(stack.getInternal()));
    }
    
    /**
     * Returns the BEP to get this stack
     */
    @ZenCodeType.Getter("commandString")
    String getCommandString();
    
    
    @ZenCodeType.Getter("items")
    IItemStack[] getItems();
    
    static IIngredient fromIngredient(Ingredient ingredient) {
        if(ingredient.hasNoMatchingItems()) {
            return new MCItemStack(ItemStack.EMPTY);
        } else {
            if(ingredient.getMatchingStacks().length == 1) {
                return new MCItemStack(ingredient.getMatchingStacks()[0]);
            } else {
                return new MCIngredientList(Arrays.stream(ingredient.getMatchingStacks()).map(MCItemStack::new).toArray(IItemStack[]::new));
            }
        }
    }

    @ZenCodeType.Caster(implicit = true)
    default MapData asMapData() {
        final IData data = this.asIData();
        return data instanceof MapData ? ((MapData) data) : new MapData();
    }

    @ZenCodeType.Caster(implicit = true)
    default IData asIData() {
        return JSONConverter.convert(this.asVanillaIngredient().serialize());
    }
}
