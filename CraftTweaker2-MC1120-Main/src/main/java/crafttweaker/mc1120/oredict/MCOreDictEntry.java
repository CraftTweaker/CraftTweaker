package crafttweaker.mc1120.oredict;

import crafttweaker.*;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.*;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.actions.*;
import crafttweaker.mc1120.util.CraftTweakerHacks;
import crafttweaker.util.ArrayUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;
import java.util.stream.Collectors;

import static crafttweaker.api.minecraft.CraftTweakerMC.*;

/**
 * @author Stan
 */
public class MCOreDictEntry implements IOreDictEntry {
    
    private static final List<NonNullList<ItemStack>> OREDICT_CONTENTS = CraftTweakerHacks.getOreIdStacks();
    private static final List<NonNullList<ItemStack>> OREDICT_CONTENTS_UN = CraftTweakerHacks.getOreIdStacksUn();
    
    public static final List<IAction> oredictToRemove = new LinkedList<>();
    public static final List<IAction> oredictToAdd = new LinkedList<>();
    
    
    private final String id;
    
    public MCOreDictEntry(String id) {
        this.id = id;
    }
    
    
    // ####################################
    // ### IOreDictEntry implementation ###
    // ####################################
    
    @Override
    public String getName() {
        return id;
    }
    
    @Override
    public boolean isEmpty() {
        return OreDictionary.getOres(getName()).isEmpty();
    }
    
    @Override
    public IItemStack getFirstItem() {
        return getItems().get(0);
    }
    
    @Override
    public void add(IItemStack item) {
        
        ItemStack stack = getItemStack(item);
        if(!stack.isEmpty()) {
            oredictToAdd.add(new ActionOreDictAddItem(id, stack));
        }
    }
    
    @Override
    public void addItems(IItemStack[] items) {
        for(IItemStack item : items) {
            ItemStack stack = getItemStack(item);
            if(!stack.isEmpty()) {
                oredictToAdd.add(new ActionOreDictAddItem(id, stack));
            }
        }
    }
    
    @Override
    public void addAll(IOreDictEntry entry) {
        if(entry instanceof MCOreDictEntry) {
            oredictToAdd.add(new ActionOreDictAddAll(id, ((MCOreDictEntry) entry).id));
        } else {
            CraftTweakerAPI.logError("not a valid entry");
        }
    }
    
    @Override
    public void remove(IItemStack item) {
        oredictToRemove.add(new ActionOreDictRemoveItem(id, item));
    }
    
    @Override
    public void removeItems(IItemStack[] items) {
        for(IItemStack item : items) {
            oredictToRemove.add(new ActionOreDictRemoveItem(id, item));
        }
    }
    
    @Override
    public boolean contains(IItemStack item) {
        for(ItemStack itemStack : OreDictionary.getOres(getName())) {
            if(getIItemStackWildcardSize(itemStack).matches(item)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void mirror(IOreDictEntry other) {
        if(other instanceof MCOreDictEntry) {
            oredictToAdd.add(new ActionOreDictMirror(id, ((MCOreDictEntry) other).id));
        } else {
            CraftTweakerAPI.logError("not a valid oredict entry");
        }
    }
    
    @Override
    public String getMark() {
        return null;
    }
    
    @Override
    public int getAmount() {
        return 1;
    }
    
    @Override
    public List<IItemStack> getItems() {
        return OreDictionary.getOres(getName()).stream().map(CraftTweakerMC::getIItemStackWildcardSize).collect(Collectors.toList());
    }
    
    @Override
    public IItemStack[] getItemArray() {
        List<IItemStack> items = getItems();
        return items.toArray(new IItemStack[items.size()]);
    }
    
    @Override
    public List<ILiquidStack> getLiquids() {
        return Collections.emptyList();
    }
    
    @Override
    public IIngredient amount(int amount) {
        return new IngredientStack(this, amount);
    }
    
    @Override
    public IIngredient transform(IItemTransformer transformer) {
        return new IngredientOreDict(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformer[]{transformer});
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientOreDict(this, null, new IItemCondition[]{condition}, ArrayUtil.EMPTY_TRANSFORMERS);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientOreDict(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS);
    }
    
    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }
    
    @Override
    public boolean matches(IItemStack item) {
        return contains(item);
    }
    
    @Override
    public boolean matchesExact(IItemStack item) {
        return contains(item);
    }
    
    @Override
    public boolean matches(ILiquidStack liquid) {
        return false;
    }
    
    @Override
    public boolean contains(IIngredient ingredient) {
        List<IItemStack> items = ingredient.getItems();
        for(IItemStack item : items) {
            if(!matches(item))
                return false;
        }
        
        return true;
    }
    
    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        return item;
    }
    
    @Override
    public boolean hasTransformers() {
        return false;
    }
    
    @Override
    public Object getInternal() {
        return id;
    }
    
    // #############################
    // ### Object implementation ###
    // #############################
    
    @Override
    public String toString() {
        return "<ore:" + id + ">";
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        return other instanceof MCOreDictEntry && Objects.equals(((MCOreDictEntry) other).id, id);
    }
    
    public static List<NonNullList<ItemStack>> getOredictContents() {
        return OREDICT_CONTENTS;
    }
    
    public static List<NonNullList<ItemStack>> getOredictContentsUn() {
        return OREDICT_CONTENTS_UN;
    }
    
    public String getId() {
        return id;
    }
}
