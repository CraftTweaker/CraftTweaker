package com.blamejared.crafttweaker.api.recipe.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.serializer.CTShapedRecipeSerializer;
import com.blamejared.crafttweaker.api.util.ArrayUtil;
import com.blamejared.crafttweaker.api.util.RecipeUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class CTShapedRecipe extends ShapedRecipe {
    
    private static final Pair<Integer, Integer> INVALID = Pair.of(-1, -1);
    
    private final IIngredient[][] ingredients;
    private final IIngredient[][][] mirroredIngredients;
    private final IItemStack output;
    private final MirrorAxis mirrorAxis;
    @Nullable
    private final RecipeFunction2D function;
    
    public CTShapedRecipe(IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis) {
        
        this(CraftingBookCategory.MISC, output, ingredients, mirrorAxis, null);
    }
    
    public CTShapedRecipe(IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunction2D function) {
        
        this(CraftingBookCategory.MISC, output, ingredients, mirrorAxis, function);
    }
    
    public CTShapedRecipe(CraftingBookCategory category, IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunction2D function) {
        
        super("", category, RecipeUtil.createPattern(ingredients), output.getInternal());
        this.output = output;
        this.ingredients = ingredients;
        this.mirrorAxis = mirrorAxis;
        this.function = function;
        this.mirroredIngredients = new IIngredient[MirrorAxis.values().length][][];
        for(int index = 0; index < this.ingredients.length; index++) {
            if(this.ingredients[index].length < getWidth()) {
                this.ingredients[index] = ArrayUtil.copyOf(this.ingredients[index], getWidth(), IItemStack.empty());
            }
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
    
    private Pair<Integer, Integer> calculateOffset(CraftingContainer inv) {
        
        Pair<Integer, Integer> offset = calculateOffset(mirroredIngredients[MirrorAxis.NONE.ordinal()], inv);
        if(isValidOffset(offset) || !mirrorAxis.isMirrored()) {
            return offset;
        }
        // These cannot be `else if` due to MirrorAxis.ALL
        if(mirrorAxis.isVertical()) {
            offset = calculateOffset(mirroredIngredients[MirrorAxis.VERTICAL.ordinal()], inv);
            if(isValidOffset(offset)) {
                return offset;
            }
        }
        if(mirrorAxis.isHorizontal()) {
            offset = calculateOffset(mirroredIngredients[MirrorAxis.HORIZONTAL.ordinal()], inv);
            if(isValidOffset(offset)) {
                return offset;
            }
        }
        if(mirrorAxis.isDiagonal()) {
            offset = calculateOffset(mirroredIngredients[MirrorAxis.DIAGONAL.ordinal()], inv);
            if(isValidOffset(offset)) {
                return offset;
            }
        }
        
        return INVALID;
    }
    
    private Pair<Integer, Integer> calculateOffset(IIngredient[][] test, CraftingContainer inv) {
        
        for(int rowOffset = 0; rowOffset <= inv.getHeight() - test.length; rowOffset++) {
            offset:
            for(int columnOffset = 0; columnOffset <= inv.getWidth() - test[0].length; columnOffset++) {
                
                final boolean[] visited = new boolean[inv.getContainerSize()];
                
                for(int rowIndex = 0; rowIndex < test.length; rowIndex++) {
                    final IIngredient[] row = test[rowIndex];
                    for(int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                        final IIngredient item = row[columnIndex];
                        final int slotNumber = (rowIndex + rowOffset) * inv.getWidth() + columnIndex + columnOffset;
                        final ItemStack stackInSlot = inv.getItem(slotNumber);
                        
                        if(item == null && !stackInSlot.isEmpty() || item != null && !item.matches(IItemStack.ofMutable(stackInSlot))) {
                            continue offset;
                        }
                        visited[slotNumber] = true;
                    }
                }
                
                for(int i = 0; i < visited.length; i++) {
                    if(!visited[i] && !inv.getItem(i).isEmpty()) {
                        continue offset;
                    }
                }
                return Pair.of(rowOffset, columnOffset);
            }
        }
        return INVALID;
    }
    
    @Override
    public boolean matches(CraftingContainer inv, @Nullable Level worldIn) {
        
        return isValidOffset(calculateOffset(inv));
    }
    
    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        
        final Pair<Integer, Integer> offset = calculateOffset(container);
        if(offset == INVALID) {
            return ItemStack.EMPTY;
        }
        
        if(function == null) {
            return getResultItem(access);
        }
        
        final int rowOffset;
        final int columnOffset;
        {
            rowOffset = offset.getFirst();
            columnOffset = offset.getSecond();
        }
        
        IItemStack[][] stacks = new IItemStack[getHeight()][getWidth()];
        for(int rowIndex = 0; rowIndex < this.ingredients.length; rowIndex++) {
            final IIngredient[] row = this.ingredients[rowIndex];
            for(int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                final IIngredient ingredient = row[columnIndex];
                if(ingredient == null) {
                    continue;
                }
                final int slotIndex = (rowIndex + rowOffset) * container.getWidth() + columnIndex + columnOffset;
                stacks[rowIndex][columnIndex] = IItemStack.of(container.getItem(slotIndex)).setAmount(1);
            }
        }
        return function.process(this.output, stacks).getImmutableInternal();
    }
    
    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        
        return output.getInternal().copy();
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        
        IIngredient[][] workingIngredients = mirroredIngredients[MirrorAxis.NONE.ordinal()];
        
        Pair<Integer, Integer> offset = calculateOffset(workingIngredients, inv);
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
    
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv, Pair<Integer, Integer> offsetPair, IIngredient[][] ingredients) {
        
        final NonNullList<ItemStack> result = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        
        if(offsetPair == INVALID) {
            return result;
        }
        
        int rowOffset = offsetPair.getFirst();
        int columnOffset = offsetPair.getSecond();
        
        for(int rowIndex = 0; rowIndex < ingredients.length; rowIndex++) {
            final IIngredient[] row = ingredients[rowIndex];
            for(int columnIndex = 0; columnIndex < row.length; columnIndex++) {
                final IIngredient ingredient = row[columnIndex];
                if(ingredient == null) {
                    continue;
                }
                final int slotIndex = (rowIndex + rowOffset) * inv.getWidth() + columnIndex + columnOffset;
                result.set(slotIndex, ingredient.getRemainingItem(IItemStack.ofMutable(inv.getItem(slotIndex)))
                        .getInternal());
            }
        }
        
        return result;
    }
    
    @Override
    public RecipeSerializer<CTShapedRecipe> getSerializer() {
        
        return CTShapedRecipeSerializer.INSTANCE;
    }
    
    public IIngredient[][] getCtIngredients() {
        
        return this.ingredients;
    }
    
    public IItemStack getCtOutput() {
        
        return this.output;
    }
    
    @Nullable
    public RecipeFunction2D getFunction() {
        
        return this.function;
    }
    
    public boolean isMirrored() {
        
        return mirrorAxis.isMirrored();
    }
    
    public MirrorAxis getMirrorAxis() {
        
        return mirrorAxis;
    }
    
    private boolean isValidOffset(Pair<Integer, Integer> pair) {
        
        return !pair.equals(INVALID);
    }
    
    public boolean isIncomplete() {
        
        NonNullList<Ingredient> ingredients = this.getIngredients();
        return ingredients.isEmpty() || ingredients.stream()
                .filter((ingredient) -> !ingredient.isEmpty())
                .anyMatch((ingredient) -> ingredient.getItems().length == 0);
    }
    
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        CTShapedRecipe that = (CTShapedRecipe) o;
        
        if(!Arrays.deepEquals(ingredients, that.ingredients)) {
            return false;
        }
        if(!Arrays.deepEquals(mirroredIngredients, that.mirroredIngredients)) {
            return false;
        }
        if(!Objects.equals(output, that.output)) {
            return false;
        }
        if(mirrorAxis != that.mirrorAxis) {
            return false;
        }
        return Objects.equals(function, that.function);
    }
    
    @Override
    public int hashCode() {
        
        int result = Arrays.deepHashCode(ingredients);
        result = 31 * result + Arrays.deepHashCode(mirroredIngredients);
        result = 31 * result + (output != null ? output.hashCode() : 0);
        result = 31 * result + (mirrorAxis != null ? mirrorAxis.hashCode() : 0);
        result = 31 * result + (function != null ? function.hashCode() : 0);
        return result;
    }
    
}
