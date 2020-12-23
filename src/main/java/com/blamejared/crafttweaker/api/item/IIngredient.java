package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.JSONConverter;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionAnyDamage;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionCustom;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionDamaged;
import com.blamejared.crafttweaker.impl.item.MCIngredientList;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.conditions.MCIngredientConditioned;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeHooks;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.function.Predicate;


/**
 * This is IIngredient!!!
 *
 * @docParam this <tag:items:forge:ingots>
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
    default boolean matches(IItemStack stack) {
        return matches(stack, false);
    }
    
    /**
     * Does the given stack match the ingredient?
     *
     * @param stack        The stack to check
     * @param ignoreDamage Should damage be checked?
     *
     * @docParam stack <item:minecraft:iron_ingot>
     */
    @ZenCodeType.Method
    boolean matches(IItemStack stack, boolean ignoreDamage);
    
    /**
     * Create a Vanilla ingredient matching this one.
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    Ingredient asVanillaIngredient();
    
    /**
     * When this ingredient stack is crafted, what will remain in the grid?
     * Does not check if the stack matches though!
     * Used e.g. in Crafting Table recipes.
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
    
    @ZenCodeType.Method
    default MCIngredientConditioned<IIngredient> onlyDamaged() {
        return new MCIngredientConditioned<>(this, new ConditionDamaged<>());
    }
    
    @ZenCodeType.Method
    default MCIngredientConditioned<IIngredient> anyDamage() {
        return new MCIngredientConditioned<>(this, new ConditionAnyDamage<>());
    }
    
    @ZenCodeType.Method
    default MCIngredientConditioned<IIngredient> onlyIf(String uid, @ZenCodeType.Optional Predicate<IItemStack> function) {
        return new MCIngredientConditioned<>(this, new ConditionCustom<>(uid, function));
    }
    
    static IIngredient fromIngredient(Ingredient ingredient) {
        if(ingredient instanceof IngredientVanillaPlus) {
            return ((IngredientVanillaPlus) ingredient).getCrTIngredient();
        }
        
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
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    default MCIngredientList or(IIngredient other) {
        return new MCIngredientList(new IIngredient[]{this, other});
    }
    
}
