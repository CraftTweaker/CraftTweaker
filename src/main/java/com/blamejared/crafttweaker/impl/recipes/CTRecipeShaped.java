package com.blamejared.crafttweaker.impl.recipes;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.MirrorAxis;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.mojang.datafixers.util.Pair;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CTRecipeShaped implements ICraftingRecipe, net.minecraftforge.common.crafting.IShapedRecipe<CraftingInventory> {
    
    private static final IntPair INVALID = new IntPair(-1, -1);
    
    private final IIngredient[][] ingredients;
    private final IItemStack output;
    /**
     * Only kept so that addons do not break.
     *
     * @deprecated Replaced by {@link #mirrorAxis}
     */
    @Deprecated
    private final boolean mirrored;
    private final MirrorAxis mirrorAxis;
    @Nullable
    private final IRecipeManager.RecipeFunctionMatrix function;
    private final ResourceLocation resourceLocation;
    
    private final int width, height;
    
    /**
     * @deprecated Replaced by {@link #mirrorAxis}
     */
    @Deprecated
    public CTRecipeShaped(String name, IItemStack output, IIngredient[][] ingredients, boolean mirrored, @Nullable IRecipeManager.RecipeFunctionMatrix function) {
        
        this.resourceLocation = new ResourceLocation("crafttweaker", name);
        this.output = output;
        this.ingredients = ingredients;
        this.mirrored = mirrored;
        this.mirrorAxis = mirrored ? MirrorAxis.ALL : MirrorAxis.NONE;
        this.function = function;
        this.height = ingredients.length;
        this.width = Arrays.stream(ingredients).mapToInt(row -> row.length).max().orElse(0);
    }
    
    public CTRecipeShaped(String name, IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable IRecipeManager.RecipeFunctionMatrix function) {
        
        this.resourceLocation = new ResourceLocation("crafttweaker", name);
        this.output = output;
        this.ingredients = ingredients;
        this.mirrored = mirrorAxis.isMirrored();
        this.mirrorAxis = mirrorAxis;
        this.function = function;
        this.height = ingredients.length;
        this.width = Arrays.stream(ingredients).mapToInt(row -> row.length).max().orElse(0);
    }
    
    
    private IntPair calculateOffset(CraftingInventory inv) {
        
        return calculateOffset(this.ingredients, inv);
    }
    
    private IntPair calculateOffset(IIngredient[][] test, CraftingInventory inv) {
        
        for(int rowOffset = 0; rowOffset <= inv.getHeight() - test.length; rowOffset++) {
            offset:
            for(int columnOffset = 0; columnOffset <= inv.getWidth() - test[0].length; columnOffset++) {
                for(Pair<Integer, Integer>[][] transformation : mirrorAxis.getTransformations()) {
                    final boolean[] visited = new boolean[inv.getSizeInventory()];
                    
                    for(int rowIndex = 0; rowIndex < test.length; rowIndex++) {
                        final IIngredient[] row = test[rowIndex];
                        for(int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                            Pair<Integer, Integer> coordinates = transformation[rowIndex][columnIndex];
                            final IIngredient item = test[coordinates.getFirst()][coordinates.getSecond()];
                            final int slotNumber = (rowIndex + rowOffset) * inv.getWidth() + columnIndex + columnOffset;
                            final ItemStack stackInSlot = inv.getStackInSlot(slotNumber);
                            
                            if(item == null && !stackInSlot.isEmpty() || item != null && !item.matches(new MCItemStackMutable(stackInSlot))) {
                                continue offset;
                            }
                            visited[slotNumber] = true;
                        }
                    }
                    
                    for(int i = 0; i < visited.length; i++) {
                        if(!visited[i] && !inv.getStackInSlot(i).isEmpty()) {
                            continue offset;
                        }
                    }
                    return new IntPair(rowOffset, columnOffset);
                }
            }
        }
        return INVALID;
    }
    
    @Override
    public boolean matches(CraftingInventory inv, @Nullable World worldIn) {
        
        return calculateOffset(inv) != INVALID;
    }
    
    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        
        final IntPair offset = calculateOffset(inv);
        if(offset == INVALID) {
            return ItemStack.EMPTY;
        }
        
        if(function == null) {
            return getRecipeOutput();
        }
        
        final int rowOffset;
        final int columnOffset;
        {
            rowOffset = offset.getX();
            columnOffset = offset.getY();
        }
        
        IItemStack[][] stacks = new IItemStack[height][width];
        for(int rowIndex = 0; rowIndex < this.ingredients.length; rowIndex++) {
            final IIngredient[] row = this.ingredients[rowIndex];
            for(int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                final IIngredient ingredient = row[columnIndex];
                if(ingredient == null) {
                    continue;
                }
                final int slotIndex = (rowIndex + rowOffset) * inv.getWidth() + columnIndex + columnOffset;
                stacks[rowIndex][columnIndex] = new MCItemStack(inv.getStackInSlot(slotIndex)).setAmount(1);
            }
        }
        return function.process(this.output, stacks).getImmutableInternal();
    }
    
    @Override
    public boolean canFit(int width, int height) {
        
        return width >= this.width && height >= this.height;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        
        return output.getInternal().copy();
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv) {
        
        return getRemainingItems(inv, calculateOffset(ingredients, inv), this.ingredients);
    }
    
    public NonNullList<ItemStack> getRemainingItems(CraftingInventory inv, IntPair offsetPair, IIngredient[][] ingredients) {
        
        final NonNullList<ItemStack> result = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        
        if(offsetPair == INVALID) {
            return result;
        }
        
        int rowOffset = offsetPair.getX();
        int columnOffset = offsetPair.getY();
        
        for(int rowIndex = 0; rowIndex < ingredients.length; rowIndex++) {
            final IIngredient[] row = ingredients[rowIndex];
            for(int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                final IIngredient ingredient = row[columnIndex];
                if(ingredient == null) {
                    continue;
                }
                final int slotIndex = (rowIndex + rowOffset) * inv.getWidth() + columnIndex + columnOffset;
                result.set(slotIndex, ingredient.getRemainingItem(new MCItemStack(inv.getStackInSlot(slotIndex)))
                        .getInternal());
            }
        }
        
        return result;
    }
    
    @Override
    public NonNullList<Ingredient> getIngredients() {
        
        NonNullList<Ingredient> ingredients = NonNullList.withSize(this.height * this.width, Ingredient.EMPTY);
        for(int row = 0; row < this.ingredients.length; row++) {
            IIngredient[] ingredientRow = this.ingredients[row];
            for(int column = 0; column < ingredientRow.length; column++) {
                ingredients.set(row * width + column, ingredientRow[column].asVanillaIngredient());
            }
        }
        return ingredients;
    }
    
    @Override
    public boolean isDynamic() {
        
        return false;
    }
    
    @Override
    public String getGroup() {
        
        return ICraftingRecipe.super.getGroup();
    }
    
    @Override
    public ResourceLocation getId() {
        
        return resourceLocation;
    }
    
    @Override
    public IRecipeSerializer<CTRecipeShaped> getSerializer() {
        
        return SerializerShaped.INSTANCE;
    }
    
    @Override
    public int getRecipeWidth() {
        
        return width;
    }
    
    @Override
    public int getRecipeHeight() {
        
        return height;
    }
    
    public IIngredient[][] getCtIngredients() {
        
        return this.ingredients;
    }
    
    public IItemStack getCtOutput() {
        
        return this.output;
    }
    
    @Nullable
    public IRecipeManager.RecipeFunctionMatrix getFunction() {
        
        return this.function;
    }
    
    public boolean isMirrored() {
        
        return mirrorAxis.isMirrored();
    }
    
    public MirrorAxis getMirrorAxis() {
        
        return mirrorAxis;
    }
    
    private static final class IntPair {
        
        private final int x, y;
        
        private IntPair(int x, int y) {
            
            this.x = x;
            this.y = y;
        }
        
        int getX() {
            
            return x;
        }
        
        int getY() {
            
            return y;
        }
        
    }
    
}
