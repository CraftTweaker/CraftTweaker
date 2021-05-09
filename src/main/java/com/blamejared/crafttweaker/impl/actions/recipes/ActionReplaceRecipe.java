package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Function;
import java.util.function.Supplier;

public class ActionReplaceRecipe extends ActionRecipeBase {
    private final Supplier<ActionAddRecipe> addRecipe;
    private final ActionRemoveRecipeByName removeRecipe;
    private final ResourceLocation oldName;
    private final Supplier<ResourceLocation> newName;
    
    public ActionReplaceRecipe(final IRecipeManager manager, final Function<ResourceLocation, ResourceLocation> nameGenerator,
                               final IRecipe<?> oldRecipe, final Function<ResourceLocation, IRecipe<?>> recipeCreator) {
        super(manager);
        this.oldName = oldRecipe.getId();
        this.newName = Lazy.concurrentOf(() -> nameGenerator.apply(this.oldName));
        this.removeRecipe = new ActionRemoveRecipeByName(manager, this.oldName);
        this.addRecipe = () -> new ActionAddRecipe(manager, recipeCreator.apply(this.newName.get()));
    }
    
    @Override
    public void apply() {
        this.removeRecipe.apply();
        this.addRecipe.get().apply();
    }
    
    @Override
    public String describe() {
        return String.format(
                "- Replacing \"%s\" recipe with name \"%s\"%s",
                this.getManager().getBracketResourceLocation(),
                this.oldName,
                this.oldName.equals(this.newName.get())? "" : String.format(", renaming it to \"%s\"", this.newName.get())
        );
    }
    
}
