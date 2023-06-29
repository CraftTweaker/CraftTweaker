package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;

import java.util.function.Function;

import net.minecraft.world.item.crafting.Recipe;

public class ActionAddRecipe<T extends Recipe<?>> extends ActionRecipeBase<T> {
    
    protected final T recipe;
    private final String subType;
    private Function<T, String> describeOutputsFunction;
    
    public ActionAddRecipe(IRecipeManager<T> recipeManager, T recipe, String subType) {
        
        super(recipeManager);
        this.recipe = recipe;
        this.subType = subType;
        this.describeOutputsFunction = r -> ItemStackUtil.getCommandString(AccessibleElementsProvider.get().registryAccess(r::getResultItem));
    }
    
    public ActionAddRecipe(IRecipeManager<T> recipeManager, T recipe) {
        
        this(recipeManager, recipe, "");
    }
    
    @Override
    public void apply() {
        
        getRecipeMutator().add(recipe.getId(), recipe);
    }
    
    @Override
    public String describe() {
        
        return "Adding '%s' recipe%s, with name: '%s' that outputs: '%s'".formatted(getManager().getBracketResourceLocation(), getSubTypeDescription(), recipe.getId(), describeOutputsFunction.apply(recipe));
    }
    
    public ActionAddRecipe<T> outputDescriber(Function<T, String> describeOutputsFunction) {
        
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
