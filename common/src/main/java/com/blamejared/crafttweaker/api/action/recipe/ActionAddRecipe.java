package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;

import java.util.function.Function;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ActionAddRecipe<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    protected final RecipeHolder<T> holder;
    private final String subType;
    private Function<RecipeHolder<T>, String> describeOutputsFunction;
    
    public ActionAddRecipe(IRecipeManager<T> recipeManager, RecipeHolder<T> holder, String subType) {
        
        super(recipeManager);
        this.holder = holder;
        this.subType = subType;
        this.describeOutputsFunction = r -> ItemStackUtil.getCommandString(AccessibleElementsProvider.get().registryAccess(r.value()::getResultItem));
    }
    
    public ActionAddRecipe(IRecipeManager<T> recipeManager, RecipeHolder<T> holder) {
        
        this(recipeManager, holder, "");
    }
    
    @Override
    public void apply() {
        
        getRecipeMutator().add(holder.id(), holder);
    }
    
    @Override
    public String describe() {
        
        return "Adding '%s' recipe%s, with name: '%s' that outputs: '%s'".formatted(getManager().getBracketResourceLocation(), getSubTypeDescription(), holder.id(), describeOutputsFunction.apply(holder));
    }
    
    public ActionAddRecipe<T> outputDescriber(Function<RecipeHolder<T>, String> describeOutputsFunction) {
        
        this.describeOutputsFunction = describeOutputsFunction;
        return this;
    }
    
    private String getSubTypeDescription() {
        
        if(subType != null && !subType.isBlank()) {
            return ", of type: \"" + subType + "\"";
        } else {
            return "";
        }
    }
    
}
