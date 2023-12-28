package com.blamejared.crafttweaker.impl.recipe.handler.type.vanilla;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.StonecutterRecipe;

import java.util.Optional;

@IRecipeHandler.For(StonecutterRecipe.class)
public final class StoneCutterRecipeHandler implements IRecipeHandler<StonecutterRecipe> {
    
    @Override
    public String dumpToCommandString(final IRecipeManager<? super StonecutterRecipe> manager, final RegistryAccess registryAccess, final RecipeHolder<StonecutterRecipe> holder) {
        
        StonecutterRecipe recipe = holder.value();
        return String.format(
                "stoneCutter.addRecipe(%s, %s, %s);",
                StringUtil.quoteAndEscape(holder.id()),
                ItemStackUtil.getCommandString(recipe.getResultItem(registryAccess)),
                IIngredient.fromIngredient(recipe.getIngredients().get(0)).getCommandString()
        );
    }
    
    @Override
    public <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super StonecutterRecipe> manager, final StonecutterRecipe firstRecipe, final U secondRecipe) {
        
        return false;
    }
    
    @Override
    public Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super StonecutterRecipe> manager, final RegistryAccess registryAccess, final StonecutterRecipe recipe) {
        
        final IIngredient input = IIngredient.fromIngredient(recipe.getIngredients().get(0));
        final IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Metadata.GROUP, recipe.getGroup())
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, input)
                .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.of(recipe.getResultItem(registryAccess)))
                .build();
        return Optional.of(decomposedRecipe);
    }
    
    @Override
    public Optional<StonecutterRecipe> recompose(final IRecipeManager<? super StonecutterRecipe> manager, final RegistryAccess registryAccess, final IDecomposedRecipe recipe) {
        
        final String group = recipe.getOrThrowSingle(BuiltinRecipeComponents.Metadata.GROUP);
        final IIngredient input = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS);
        final IItemStack output = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS);
        return Optional.of(new StonecutterRecipe(group, input.asVanillaIngredient(), output.getInternal()));
    }
    
}
