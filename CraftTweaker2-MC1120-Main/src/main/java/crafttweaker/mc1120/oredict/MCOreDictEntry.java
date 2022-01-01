package crafttweaker.mc1120.oredict;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.*;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.actions.*;
import crafttweaker.mc1120.util.CraftTweakerHacks;
import crafttweaker.util.ArrayUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static crafttweaker.api.minecraft.CraftTweakerMC.*;

/**
 * @author Stan
 */
public class MCOreDictEntry implements IOreDictEntry {
    
    private static final List<NonNullList<ItemStack>> OREDICT_CONTENTS = CraftTweakerHacks.getOreIdStacks();
    private static final List<NonNullList<ItemStack>> OREDICT_CONTENTS_UN = CraftTweakerHacks.getOreIdStacksUn();
    private static final Field ORE_FIELD;
    public static final Map<String, List<IItemStack>> REMOVED_CONTENTS = new HashMap<>();
    
    static {
        Field toSet = null;
        try {
            toSet = OreIngredient.class.getDeclaredField("ores");
            toSet.setAccessible(true);
        } catch(NoSuchFieldException e) {
            CraftTweaker.LOG.catching(e);
        }
        ORE_FIELD = toSet;
    }
    private final String id;
    
    public MCOreDictEntry(String id) {
        this.id = id;
    }
    
    @SuppressWarnings("unchecked")
    public static MCOreDictEntry getFromIngredient(OreIngredient oreIngredient) {
        if(ORE_FIELD != null)
            try {
                //Unfortunately there's no way of getting the oreList from the oreIngredient without Reflection
                NonNullList<ItemStack> ores = (NonNullList<ItemStack>) MCOreDictEntry.ORE_FIELD.get(oreIngredient);
                
                for(String oreName : OreDictionary.getOreNames()) {
                    if(ores == OreDictionary.getOres(oreName, false))
                        return new MCOreDictEntry(oreName);
                }
                
            } catch(IllegalAccessException e) {
                CraftTweaker.LOG.catching(e);
            }
        return null;
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
        List<IItemStack> items = getItems();
        if(items.isEmpty()) {
            CraftTweakerAPI.logInfo("No first item found for oredict " + getName() + "! Replacing with null reference");
            return null;
        }
        return items.get(0);
    }
    
    @Override
    public void add(IItemStack... items) {
        final List<IItemStack> removedContents = REMOVED_CONTENTS.get(id);
        for(IItemStack item : items) {
            if(removedContents != null)
                removedContents.removeIf(removedItem -> removedItem.matches(item));
            ItemStack stack = getItemStack(item);
            if(!stack.isEmpty()) {
                CraftTweakerAPI.apply(new ActionOreDictAddItem(id, stack));
            }
        }
    }
    
    @Override
    public void addItems(IItemStack[] items) {
        add(items);
    }
    
    @Override
    public void addAll(IOreDictEntry... entries) {
        for (IOreDictEntry entry : entries){
            if(entry instanceof MCOreDictEntry) {
                CraftTweakerAPI.apply(new ActionOreDictAddAll(id, ((MCOreDictEntry) entry).id));
            } else {
                CraftTweakerAPI.logError("not a valid entry! " + entry.toString());
            }
        }
    }
    
    @Override
    public void remove(IItemStack... items) {
        REMOVED_CONTENTS.computeIfAbsent(id, s -> new ArrayList<>());
        for(IItemStack item : items) {
            REMOVED_CONTENTS.get(id).add(item);
            ItemStack result = ItemStack.EMPTY;
            for(ItemStack itemStack : OreDictionary.getOres(id)) {
                if(item.matches(getIItemStackForMatching(itemStack, true))) {
                    result = itemStack;
                    break;
                }
            }
            
            if(!result.isEmpty()) {
                CraftTweakerAPI.apply(new ActionOreDictRemoveItem(id, result));
            }
        }
    }
    
    @Override
    public void removeItems(IItemStack[] items) {
        remove(items);
    }
    
    @Override
    public boolean contains(IItemStack item) {
        for(ItemStack itemStack : OreDictionary.getOres(getName())) {
            if(CraftTweakerMC.matches(item, itemStack)) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public void mirror(IOreDictEntry other) {
        if(other instanceof MCOreDictEntry) {
            CraftTweakerAPI.apply(new ActionOreDictMirror(id, ((MCOreDictEntry) other).id));
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
        return items.toArray(new IItemStack[0]);
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
    public IIngredient transformNew(IItemTransformerNew transformer) {
        return new IngredientOreDict(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformerNew[]{transformer}, ArrayUtil.EMPTY_TRANSFORMERS_NEW);
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientOreDict(this, null, new IItemCondition[]{condition}, ArrayUtil.EMPTY_TRANSFORMERS, ArrayUtil.EMPTY_TRANSFORMERS_NEW);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientOreDict(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS, ArrayUtil.EMPTY_TRANSFORMERS_NEW);
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
        if(ingredient == null)
            return false;
        
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
    public IItemStack applyNewTransform(IItemStack item) {
        return item;
    }
    
    @Override
    public boolean hasNewTransformers() {
        return false;
    }
    
    @Override
    public boolean hasTransformers() {
        return false;
    }
    
    @Override
    public IIngredient transform(IItemTransformer transformer) {
        return new IngredientOreDict(this, null, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS, new IItemTransformer[]{transformer});
    }
    
    @Override
    public Object getInternal() {
        return id;
    }
    
    @Override
    public String toCommandString() {
        return toString();
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
    
}
