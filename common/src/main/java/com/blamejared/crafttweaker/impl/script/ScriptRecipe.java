package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class ScriptRecipe implements Recipe<Container> {
    
    private static final Function<ScriptRecipe, ResourceLocation> ID_GENERATOR = Util.memoize(scriptRecipe -> {
        final String sanitizedFileName = scriptRecipe.getFileName()
                .toLowerCase(Locale.ENGLISH)
                .replaceAll("[^a-z0-9_.-]", "_");
        return CraftTweakerConstants.rl(sanitizedFileName);
    });
    
    private final String fileName;
    private final String content;
    
    public ScriptRecipe(String fileName, String content) {
        
        this.fileName = fileName;
        this.content = content;
    }
    
    public ResourceLocation getId() {
        
        return ID_GENERATOR.apply(this);
    }
    
    @Override
    public boolean matches(Container container, Level level) {
        
        return false;
    }
    
    @Override
    public ItemStack assemble(Container var1, RegistryAccess var2) {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        
        return false;
    }
    
    @Override
    public ItemStack getResultItem(RegistryAccess var1) {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        
        return ScriptSerializer.INSTANCE;
    }
    
    @Override
    public RecipeType<?> getType() {
        
        return ScriptRecipeType.INSTANCE;
    }
    
    public String getContent() {
        
        return content;
    }
    
    public String getFileName() {
        
        return fileName;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        ScriptRecipe that = (ScriptRecipe) o;
        return Objects.equals(getFileName(), that.getFileName()) && Objects.equals(getContent(), that.getContent());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getFileName(), getContent());
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("ScriptRecipe{");
        sb.append("fileName='").append(fileName).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
    
}
