package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.google.gson.JsonElement;
import mcp.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.StackList;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
public abstract class IngredientVanillaPlus extends Ingredient {
    private final IIngredient crtIngredient;

    protected IngredientVanillaPlus(IIngredient crtIngredient, Stream<? extends IItemList> itemLists) {
        super(itemLists);
        this.crtIngredient = crtIngredient;
    }

    protected IngredientVanillaPlus(IIngredient crtIngredient) {
        this(crtIngredient, Stream.of(new StackList(Arrays.stream(crtIngredient.getItems()).map(IItemStack::getInternal).collect(Collectors
                .toList()))));
    }

    public IIngredient getCrTIngredient() {
        return crtIngredient;
    }

    @Override
    public abstract JsonElement serialize();

    @Override
    public abstract IIngredientSerializer<? extends IngredientVanillaPlus> getSerializer();

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return stack != null && crtIngredient.matches(new MCItemStack(stack));
    }
}
