package crafttweaker.mc1120.item;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.data.*;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.actions.*;
import crafttweaker.mc1120.block.MCItemBlock;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import crafttweaker.util.ArrayUtil;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class MCItemStack implements IItemStack {
    
    private final ItemStack stack;
    private final List<IItemStack> items;
    private IData tag = null;
    private boolean wildcardSize;
    
    public MCItemStack(ItemStack itemStack) {
        if(itemStack.isEmpty())
            throw new IllegalArgumentException("stack cannot be null");
        
        stack = itemStack.copy();
        items = Collections.singletonList(this);
    }
    
    public MCItemStack(ItemStack itemStack, boolean wildcardSize) {
        this(itemStack);
        
        this.wildcardSize = wildcardSize;
    }
    
    private MCItemStack(ItemStack itemStack, IData tag) {
        if(itemStack.isEmpty())
            throw new IllegalArgumentException("stack cannot be null");
        
        stack = itemStack;
        items = Collections.singletonList(this);
        this.tag = tag;
    }
    
    private MCItemStack(ItemStack itemStack, IData tag, boolean wildcardSize) {
        if(itemStack.isEmpty())
            throw new IllegalArgumentException("stack cannot be null");
        stack = itemStack;
        items = Collections.singletonList(this);
        this.tag = tag;
        this.wildcardSize = wildcardSize;
    }
    
    @Override
    public IItemDefinition getDefinition() {
        return new MCItemDefinition(stack.getItem().getRegistryName().toString(), stack.getItem());
    }
    
    @Override
    public String getName() {
        return stack.getUnlocalizedName();
    }
    
    @Override
    public String getDisplayName() {
        return stack.getDisplayName();
    }
    
    @Override
    public void setDisplayName(String name) {
        CraftTweakerAPI.apply(new ActionSetStackTranslation(this, getName() + ".name", name));
    }
    
    @Override
    public int getMaxStackSize() {
        return stack.getMaxStackSize();
    }
    
    @Override
    public void setMaxStackSize(int size) {
        CraftTweakerAPI.apply(new ActionSetStackSize((ItemStack) getInternal(), size));
    }
    
    @Override
    public float getBlockHardness() {
        return ReflectionHelper.getPrivateValue(Block.class, Block.getBlockFromItem(stack.getItem()), "blockHardness");
    }
    
    @Override
    public void setBlockHardness(float hardness) {
        CraftTweakerAPI.apply(new ActionSetBlockHardness(stack, hardness));
    }
    
    @Override
    public int getDamage() {
        return stack.getItemDamage();
    }
    
    @Override
    public IData getTag() {
        if(tag == null) {
            if(stack.getTagCompound() == null) {
                return DataMap.EMPTY;
            }
            tag = NBTConverter.from(stack.getTagCompound(), true);
        }
        return tag;
    }
    
    @Override
    public int getMaxDamage() {
        return stack.getMaxDamage();
    }
    
    @Override
    public void setMaxDamage(int damage) {
        CraftTweakerAPI.apply(new ActionSetStackMaxDamage(stack, damage));
    }
    
    @Override
    public ILiquidStack getLiquid() {
        FluidStack liquid = FluidUtil.getFluidContained(stack);
        return liquid == null ? null : new MCLiquidStack(liquid);
    }
    
    @Override
    public IIngredient anyDamage() {
            ItemStack result = new ItemStack(stack.getItem(), stack.getCount(), OreDictionary.WILDCARD_VALUE);
            result.setTagCompound(stack.getTagCompound());
            return new MCItemStack(result, tag);
    }
    
    @Override
    public IItemStack withDamage(int damage) {
            ItemStack result = new ItemStack(stack.getItem(), stack.getCount(), damage);
            result.setTagCompound(stack.getTagCompound());
            return new MCItemStack(result, tag);
    }
    
    @Override
    public IItemStack anyAmount() {
        ItemStack result = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
        result.setTagCompound(stack.getTagCompound());
        return new MCItemStack(result, tag, true);
    }
    
    @Override
    public IItemStack withAmount(int amount) {
        ItemStack result = new ItemStack(stack.getItem(), amount, stack.getItemDamage());
        result.setTagCompound(stack.getTagCompound());
        return new MCItemStack(result, tag);
    }
    
    @Override
    public IItemStack withTag(IData tag) {
        ItemStack result = new ItemStack(stack.getItem(), stack.getCount(), stack.getItemDamage());
        if(tag == null) {
            result.setTagCompound(null);
        } else {
            result.setTagCompound((NBTTagCompound) NBTConverter.from(tag));
        }
        return new MCItemStack(result, tag);
    }
    
    @Override
    public IItemStack withEmptyTag() {
        ItemStack result = new ItemStack(stack.getItem(), stack.getCount(), stack.getItemDamage());
        result.setTagCompound(new NBTTagCompound());
        return new MCItemStack(result, NBTConverter.from(new NBTTagCompound(), true));
    }
    
    @Override
    public IItemStack updateTag(IData tagUpdate) {
        if(tag == null) {
            if(stack.getTagCompound() == null) {
                return withTag(tagUpdate);
            }
            
            tag = NBTConverter.from(stack.getTagCompound(), true);
        }
        
        IData updated = tag.update(tagUpdate);
        return withTag(updated);
    }
    
    @Override
    public IItemStack removeTag(String tag) {
        ItemStack result = new ItemStack(stack.getItem(), stack.getCount(), stack.getItemDamage());
        
        if(tag == null) {
            result.setTagCompound(null);
        } else {
            result.getTagCompound().removeTag(tag);
        }
        IData dataTag = NBTConverter.from(result.getTagCompound(), false);
        return new MCItemStack(result, dataTag);
    }
    
    @Override
    public String getMark() {
        return null;
    }
    
    @Override
    public int getAmount() {
        return stack.getCount();
    }
    
    @Override
    public List<IItemStack> getItems() {
        return items;
    }
    
    @Override
    public IItemStack[] getItemArray() {
    	return items.toArray(new IItemStack[items.size()]);
    }
    
    @Override
    public List<ILiquidStack> getLiquids() {
        return Collections.emptyList();
    }
    
    @Override
    public IItemStack amount(int amount) {
        return withAmount(amount);
    }
    
    @Override
    public WeightedItemStack percent(float chance) {
        return new WeightedItemStack(this, chance * 0.01f);
    }
    
    @Override
    public WeightedItemStack weight(float chance) {
        return new WeightedItemStack(this, chance);
    }
    
    @Override
    public IIngredient transform(IItemTransformer transformer) {
        return new IngredientItem(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformer[]{transformer});
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientItem(this, null, new IItemCondition[]{condition}, ArrayUtil.EMPTY_TRANSFORMERS);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientItem(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS);
    }
    
    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }
    
    @Override
    public boolean matches(IItemStack item) {
        ItemStack internal = getItemStack(item);
        if(stack.hasTagCompound()) {
            return matchesExact(item);
        }
        return !internal.isEmpty() && !stack.isEmpty() && internal.getItem() == stack.getItem() && (wildcardSize || internal.getCount() >= stack.getCount()) && (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() == internal.getItemDamage() || (!stack.getHasSubtypes() && !stack.getItem().isDamageable()));
    }
    
    @Override
    public boolean matchesExact(IItemStack item) {
        ItemStack internal = getItemStack(item);
        if(internal.getTagCompound() != null && stack.getTagCompound() == null) {
            return false;
        }
        if(internal.getTagCompound() == null && stack.getTagCompound() != null) {
            return false;
        }
        if(internal.getTagCompound() == null && stack.getTagCompound() == null) {
            return stack.getItem() == internal.getItem() && (internal.getMetadata() == 32767 || stack.getMetadata() == internal.getMetadata());
        }
        if(internal.getTagCompound().getKeySet().equals(stack.getTagCompound().getKeySet())) {
            for(String s : internal.getTagCompound().getKeySet()) {
                if(!internal.getTagCompound().getTag(s).equals(stack.getTagCompound().getTag(s))) {
                    return false;
                }
            }
        }
        return stack.getItem() == internal.getItem() && (internal.getMetadata() == 32767 || stack.getMetadata() == internal.getMetadata());
    }
    
    @Override
    public boolean matches(ILiquidStack liquid) {
        return false;
    }
    
    @Override
    public boolean contains(IIngredient ingredient) {
        List<IItemStack> iitems = ingredient.getItems();
        return !(iitems == null || iitems.size() != 1) && matches(iitems.get(0));
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
        return stack;
    }
    
    @Override
    public IBlock asBlock() {
        ResourceLocation name = Block.REGISTRY.getNameForObject(Block.getBlockFromItem(stack.getItem()));
        if(Block.REGISTRY.containsKey(name)) {
            return new MCItemBlock(stack);
        } else {
            throw new ClassCastException("This item is not a block");
        }
    }
    
    @Override
    public List<IOreDictEntry> getOres() {
        List<IOreDictEntry> result = new ArrayList<>();
        
        for(String key : OreDictionary.getOreNames()) {
            for(ItemStack is : OreDictionary.getOres(key)) {
                if(is.getItem() == stack.getItem() && (is.getItemDamage() == OreDictionary.WILDCARD_VALUE || is.getItemDamage() == stack.getItemDamage())) {
                    result.add(CraftTweakerAPI.oreDict.get(key));
                    break;
                }
            }
        }
        
        return result;
    }
    
    @Override
    public IItemStack withDisplayName(String name){
        NBTTagCompound tagComp;
        
        if (!this.stack.hasTagCompound() || this.stack.getTagCompound() == null){
            tagComp = new NBTTagCompound();
        }else {
            tagComp = this.stack.getTagCompound();
        }
    
        NBTTagCompound display;
        if (!tagComp.hasKey("display") || !(tagComp.getTag("display") instanceof NBTTagCompound)){
            display = new NBTTagCompound();
        }else {
            display = (NBTTagCompound) tagComp.getTag("display");
        }
        
        display.setString("Name", name);
        tagComp.setTag("display", display);
        
        ItemStack newStack = stack.copy();
        newStack.setTagCompound(tagComp);
        
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack withLore(String[] lore){
        NBTTagCompound tagComp;
        
        if (!this.stack.hasTagCompound() || this.stack.getTagCompound() == null){
            tagComp = new NBTTagCompound();
        }else {
            tagComp = this.stack.getTagCompound();
        }
        
        NBTTagCompound display;
        if (!tagComp.hasKey("display") || !(tagComp.getTag("display") instanceof NBTTagCompound)){
            display = new NBTTagCompound();
        }else {
            display = (NBTTagCompound) tagComp.getTag("display");
        }
    
        NBTTagList loreList;
        if (!tagComp.hasKey("Lore") || !(tagComp.getTag("Lore") instanceof NBTTagList)){
            loreList = new NBTTagList();
        }else {
            loreList = (NBTTagList) tagComp.getTag("Lore");
        }
    
        for(String s : lore) {
            loreList.appendTag(new NBTTagString(s));
        }
    
        display.setTag("Lore", loreList);
        tagComp.setTag("display", display);
        
        ItemStack newStack = stack.copy();
        newStack.setTagCompound(tagComp);
        
        return new MCItemStack(newStack);
    }
    
    
    // #############################
    // ### Object implementation ###
    // #############################
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + stack.getItem().hashCode();
        hash = 41 * hash + stack.getItemDamage();
        hash = 41 * hash + stack.getCount();
        hash = 41 * hash + (stack.getTagCompound() == null ? 0 : stack.getTagCompound().hashCode());
        hash = 41 * hash + (this.wildcardSize ? 1 : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final MCItemStack other = (MCItemStack) obj;
        return this.stack.getItem() == other.stack.getItem() && this.stack.getItemDamage() == other.stack.getItemDamage() && this.stack.getCount() == other.stack.getCount() && !(this.stack.getTagCompound() != other.stack.getTagCompound() && this.stack.equals(other.stack)) && this.wildcardSize == other.wildcardSize;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('<');
        result.append(Item.REGISTRY.getNameForObject(stack.getItem()));
        
        if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            result.append(":*");
        } else if(stack.getItemDamage() > 0) {
            result.append(':').append(stack.getItemDamage());
        }
        result.append('>');
        
        if(stack.getTagCompound() != null) {
            result.append(".withTag(");
            result.append(NBTConverter.from(stack.getTagCompound(), wildcardSize).toString());
            result.append(")");
        }
        if(!wildcardSize && stack.getCount() > 1) {
            result.append(" * ").append(stack.getCount());
        }
        return result.toString();
    }
    
    
}
