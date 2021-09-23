package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweaker;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ScriptRecipe implements IRecipe<DummyInventory> {
    
    private final ResourceLocation id;
    private final String fileName;
    private final String content;
    
    public ScriptRecipe(ResourceLocation id, String fileName, String content) {
        
        this.id = id;
        this.fileName = fileName;
        this.content = content;
    }
    
    @Override
    public boolean matches(DummyInventory inv, World worldIn) {
        
        return false;
    }
    
    @Override
    public ItemStack getCraftingResult(DummyInventory inv) {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canFit(int width, int height) {
        
        return false;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public ResourceLocation getId() {
        
        return id;
    }
    
    @Override
    public IRecipeSerializer<?> getSerializer() {
        
        return SerializerScript.INSTANCE;
    }
    
    @Override
    public IRecipeType<?> getType() {
        
        return CraftTweaker.RECIPE_TYPE_SCRIPTS;
    }
    
    public String getContent() {
        
        return content;
    }
    
    public String getFileName() {
        
        return fileName;
    }
    
    @Override
    public boolean isDynamic() {
        
        return true;
    }
    
}
