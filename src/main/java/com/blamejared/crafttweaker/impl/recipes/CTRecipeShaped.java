package com.blamejared.crafttweaker.impl.recipes;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.MirrorAxis;
import com.blamejared.crafttweaker.api.util.ArrayUtil;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
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
    
    private final IIngredient[][][] mirroredIngredients;
    
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
        this.mirroredIngredients = new IIngredient[MirrorAxis.values().length][][];
    
        for(int index = 0; index < this.ingredients.length; index++) {
            if(this.ingredients[index].length < width)
                this.ingredients[index] = ArrayUtil.copyOf(this.ingredients[index], width, MCItemStack.EMPTY.get());
        }
        initMirroredIngredients();
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
        this.mirroredIngredients = new IIngredient[MirrorAxis.values().length][][];
        for(int index = 0; index < this.ingredients.length; index++) {
            if(this.ingredients[index].length < width)
                this.ingredients[index] = ArrayUtil.copyOf(this.ingredients[index], width, MCItemStack.EMPTY.get());
        }
        initMirroredIngredients();
    }
    
    private void initMirroredIngredients() {
        
        this.mirroredIngredients[MirrorAxis.NONE.ordinal()] = ingredients;
        if(mirrorAxis.isMirrored()) {
            if(mirrorAxis.isVertical()) {
                mirroredIngredients[MirrorAxis.VERTICAL.ordinal()] = ArrayUtil.mirror(ingredients);
            }
            if(mirrorAxis.isHorizontal()) {
                IIngredient[][] workingIngredients = ArrayUtil.copy(this.ingredients);
                for(int i = 0; i < workingIngredients.length; i++) {
                    workingIngredients[i] = ArrayUtil.mirror(workingIngredients[i]);
                }
                mirroredIngredients[MirrorAxis.HORIZONTAL.ordinal()] = workingIngredients;
            }
            if(mirrorAxis.isDiagonal()) {
                IIngredient[][] workingIngredients = ArrayUtil.mirror(this.ingredients);
                for(int i = 0; i < workingIngredients.length; i++) {
                    workingIngredients[i] = ArrayUtil.mirror(workingIngredients[i]);
                }
                mirroredIngredients[MirrorAxis.DIAGONAL.ordinal()] = workingIngredients;
            }
        }
    }
    
    
    private IntPair calculateOffset(CraftingInventory inv) {
        
        IntPair offset = calculateOffset(mirroredIngredients[MirrorAxis.NONE.ordinal()], inv);
        if(offset != INVALID || !mirrorAxis.isMirrored()) {
            return offset;
        }
        // These cannot be `else if` due to MirrorAxis.ALL
        if(mirrorAxis.isVertical()) {
            offset = calculateOffset(mirroredIngredients[MirrorAxis.VERTICAL.ordinal()], inv);
            if(offset != INVALID) {
                return offset;
            }
        }
        if(mirrorAxis.isHorizontal()) {
            offset = calculateOffset(mirroredIngredients[MirrorAxis.HORIZONTAL.ordinal()], inv);
            if(offset != INVALID) {
                return offset;
            }
        }
        if(mirrorAxis.isDiagonal()) {
            offset = calculateOffset(mirroredIngredients[MirrorAxis.DIAGONAL.ordinal()], inv);
            if(offset != INVALID) {
                return offset;
            }
        }
        
        return INVALID;
        
    }
    
    private IntPair calculateOffset(IIngredient[][] test, CraftingInventory inv) {
        
        for(int rowOffset = 0; rowOffset <= inv.getHeight() - test.length; rowOffset++) {
            offset:
            for(int columnOffset = 0; columnOffset <= inv.getWidth() - test[0].length; columnOffset++) {
                final boolean[] visited = new boolean[inv.getSizeInventory()];
                
                for(int rowIndex = 0; rowIndex < test.length; rowIndex++) {
                    final IIngredient[] row = test[rowIndex];
                    for(int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                        final IIngredient item = row[columnIndex];
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
        
        IIngredient[][] workingIngredients = mirroredIngredients[MirrorAxis.NONE.ordinal()];
        IntPair offset = calculateOffset(workingIngredients, inv);
        if(offset != INVALID || !mirrorAxis.isMirrored()) {
            return getRemainingItems(inv, offset, workingIngredients);
        }
        
        if(mirrorAxis.isVertical()) {
            workingIngredients = mirroredIngredients[MirrorAxis.VERTICAL.ordinal()];
            offset = calculateOffset(workingIngredients, inv);
            if(offset != INVALID) {
                return getRemainingItems(inv, offset, workingIngredients);
            }
        }
        
        if(mirrorAxis.isHorizontal()) {
            workingIngredients = mirroredIngredients[MirrorAxis.HORIZONTAL.ordinal()];
            offset = calculateOffset(workingIngredients, inv);
            if(offset != INVALID) {
                return getRemainingItems(inv, offset, workingIngredients);
            }
        }
        
        if(mirrorAxis.isDiagonal()) {
            workingIngredients = mirroredIngredients[MirrorAxis.DIAGONAL.ordinal()];
            offset = calculateOffset(workingIngredients, inv);
            // this should fall through to the call below
        }
        
        return getRemainingItems(inv, offset, workingIngredients);
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
