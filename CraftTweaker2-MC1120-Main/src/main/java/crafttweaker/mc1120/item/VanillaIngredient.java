package crafttweaker.mc1120.item;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public class VanillaIngredient extends Ingredient {
    
    private IIngredient ingredient;
    private IntList stacks = null;
    
    public VanillaIngredient(@Nonnull IIngredient ingredient) {
        this.ingredient = ingredient;
    }
    
    @Override
    public ItemStack[] getMatchingStacks() {
        return CraftTweakerMC.getItemStacks(ingredient.getItemArray());
    }
    
    @Override
    public boolean apply(@Nullable ItemStack itemStack) {
        return ingredient.matches(CraftTweakerMC.getIItemStackForMatching(itemStack));
    }
    
    @Override
    public IntList getValidItemStacksPacked() {
        if(stacks != null) {
            return stacks;
        }
        
        IntList list = new IntArrayList();
        if(ingredient != null) {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            for(ItemStack stack : CraftTweakerMC.getItemStacks(ingredient.getItemArray())) {
                stack.getItem().getSubItems(CreativeTabs.SEARCH, itemStacks);
            }
            itemStacks.stream().mapToInt(RecipeItemHelper::pack).forEach(list::add);
        }
        
        return stacks = list;
    }
    
    @Override
    protected void invalidate() {
        this.stacks = null;
    }
    
    @Override
    public boolean isSimple() {
        return false;
    }
    
    @Override
    public boolean test(@Nullable ItemStack input) {
        return apply(input);
    }
    
    public IIngredient getIngredient() {
        return ingredient;
    }
}
