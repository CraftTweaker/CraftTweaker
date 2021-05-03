package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;

public final class ActionReplaceRecipe extends ActionRecipeBase {
    private final ActionAddRecipe addRecipe;
    private final ActionRemoveRecipeByName removeRecipe;
    private final ResourceLocation oldName;
    private final ResourceLocation newName;
    
    public ActionReplaceRecipe(final IRecipeManager manager, final IRecipe<?> oldRecipe, final IRecipe<?> newRecipe) {
        super(manager);
        this.oldName = oldRecipe.getId();
        this.newName = newRecipe.getId();
        this.removeRecipe = new ActionRemoveRecipeByName(manager, this.oldName);
        this.addRecipe = new ActionAddRecipe(manager, newRecipe);
    }
    
    @Override
    public void apply() {
        this.removeRecipe.apply();
        this.addRecipe.apply();
    }
    
    @Override
    public String describe() {
        return String.format(
                "- Replacing \"%s\" recipe with name \"%s\"%s",
                this.getManager().getBracketResourceLocation(),
                this.oldName,
                this.oldName.equals(this.newName)? "" : String.format(", renaming it to \"%s\"", this.newName)
        );
    }
    
}
