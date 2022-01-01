package crafttweaker.mc1120.item;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.DataString;
import crafttweaker.api.data.IData;
import crafttweaker.api.enchantments.IEnchantment;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.actions.ActionSetBlockHardness;
import crafttweaker.mc1120.actions.ActionSetStackMaxDamage;
import crafttweaker.mc1120.actions.ActionSetStackSize;
import crafttweaker.mc1120.actions.ActionSetStackTranslation;
import crafttweaker.mc1120.block.MCItemBlock;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.enchantments.MCEnchantment;
import crafttweaker.mc1120.game.MCGame;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import crafttweaker.util.ArrayUtil;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Stan
 */
public class MCItemStack implements IItemStack {
    
    public static final IItemStack EMPTY = new MCItemStack();
    
    
    private final ItemStack stack;

    /**
     * ItemStack that not copied, only used in MCMutableItemStack
     */
    protected final ItemStack origin;
    private final List<IItemStack> items;
    protected boolean matchTagExact = true;
    protected IData tag = null;
    private boolean wildcardSize;
    
    private MCItemStack() {
        stack = ItemStack.EMPTY;
        origin = ItemStack.EMPTY;
        items = Collections.singletonList(this);
    }
    
    public MCItemStack(ItemStack itemStack) {
        if(itemStack.isEmpty())
            throw new IllegalArgumentException("stack cannot be null");
        stack = itemStack.copy();
        origin = itemStack;
        items = Collections.singletonList(this);
    }
    
    public MCItemStack(ItemStack itemStack, boolean wildcardSize) {
        this(itemStack);
        
        this.wildcardSize = wildcardSize;
    }
    
    protected MCItemStack(ItemStack itemStack, IData tag) {
        if(itemStack.isEmpty())
            throw new IllegalArgumentException("stack cannot be null");
        
        stack = itemStack;
        origin = itemStack;
        items = Collections.singletonList(this);
        this.tag = tag;
    }
    
    protected MCItemStack(ItemStack itemStack, IData tag, boolean wildcardSize) {
        if(itemStack.isEmpty())
            throw new IllegalArgumentException("stack cannot be null");
        stack = itemStack;
        origin = itemStack;
        items = Collections.singletonList(this);
        this.tag = tag;
        this.wildcardSize = wildcardSize;
    }
    
    /**
     * This is a constructor which creates a itemstack that doesn't copy the internal stack
     * Only use this if you are sure it will never be changed
     *
     * @param unused to prevent constructor conflicts, has no purpose
     */
    private MCItemStack(ItemStack itemStack, int unused) {
        if(itemStack.isEmpty())
            throw new IllegalArgumentException("stack cannot be null");
        stack = itemStack;
        origin = itemStack;
        items = Collections.singletonList(this);
    }
    
    public static MCItemStack createNonCopy(ItemStack itemStack) {
        if(itemStack == null || itemStack.isEmpty())
            return null;
        
        return new MCItemStack(itemStack, -1);
    }
    
    @Override
    public IItemDefinition getDefinition() {
        return new MCItemDefinition(stack.getItem().getRegistryName().toString(), stack.getItem());
    }

    @Override
    public boolean isItemBlock() {
        return stack.getItem() instanceof ItemBlock;
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
        ActionSetStackTranslation action = new ActionSetStackTranslation(this, getName() + ".name", name);
        MCGame.TRANSLATION_ACTIONS.add(action);
        CraftTweakerAPI.apply(action);
        
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
        return Block.getBlockFromItem(stack.getItem()).blockHardness;
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
    public IItemStack withTag(IData tag, boolean matchTagExact) {
        ItemStack result = new ItemStack(stack.getItem(), stack.getCount(), stack.getItemDamage());
        if(tag == null) {
            result.setTagCompound(null);
        } else {
            result.setTagCompound((NBTTagCompound) NBTConverter.from(tag));
        }
        MCItemStack out = new MCItemStack(result, tag);
        out.matchTagExact = matchTagExact;
        return out;
    }

    @Override
    public boolean getMatchTagExact() {
        return matchTagExact;
    }

    @Override
    public IItemStack withTag(IData tag) {
        return withTag(tag, true);
    }
    
    @Override
    public IItemStack updateTag(IData tagUpdate) {
        return updateTag(tagUpdate, true);
    }
    
    @Override
    public IItemStack withEmptyTag() {
        ItemStack result = new ItemStack(stack.getItem(), stack.getCount(), stack.getItemDamage());
        result.setTagCompound(new NBTTagCompound());
        return new MCItemStack(result, NBTConverter.from(new NBTTagCompound(), true));
    }
    
    @Override
    public IItemStack updateTag(IData tagUpdate, boolean matchTagExact) {
        if(tag == null) {
            if(stack.getTagCompound() == null) {
                return withTag(tagUpdate, matchTagExact);
            }
            
            tag = NBTConverter.from(stack.getTagCompound(), true);
        }
        
        IData updated = tag.update(tagUpdate);
        return withTag(updated, matchTagExact);
    }
    
    @Override
    public IItemStack removeTag(String tag) {
        ItemStack result = stack.copy();

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
        return getItems().toArray(new IItemStack[0]);
    }
    
    @Override
    public List<ILiquidStack> getLiquids() {
        return items.stream()
                .filter(Objects::nonNull)
                .map(CraftTweakerMC::getItemStack)
                .map(FluidUtil::getFluidContained)
                .filter(Objects::nonNull)
                .map(CraftTweakerMC::getILiquidStack)
                .collect(Collectors.toList());
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
    public IIngredient transformNew(IItemTransformerNew transformer) {
        return new IngredientItem(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformerNew[]{transformer}, ArrayUtil.EMPTY_TRANSFORMERS_NEW);
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientItem(this, null, new IItemCondition[]{condition}, ArrayUtil.EMPTY_TRANSFORMERS, ArrayUtil.EMPTY_TRANSFORMERS_NEW);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientItem(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS, ArrayUtil.EMPTY_TRANSFORMERS_NEW);
    }
    
    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }
    
    @Override
    public boolean matches(IItemStack item) {
        if(item == null)
            return false;
        
        ItemStack internal = (ItemStack) item.getInternal();
        if(stack.hasTagCompound() && matchTagExact) {
            return matchesExact(item);
        }
        return !internal.isEmpty() && !stack.isEmpty() && internal.getItem() == stack.getItem() && (wildcardSize || internal.getCount() >= stack.getCount()) && (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || internal.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() == internal.getItemDamage() || (!stack.getHasSubtypes() && !stack.getItem().isDamageable()));
    }
    
    @Override
    public boolean matchesExact(IItemStack item) {
        if(item == null)
            return false;
        
        ItemStack internal = (ItemStack) item.getInternal();
    
        NBTTagCompound internalTag = internal.getTagCompound();
        NBTTagCompound stackTag = stack.getTagCompound();
        if(internal.hasTagCompound() != stack.hasTagCompound()) {
            return false;
        }
        
        boolean itemMatches = stack.getItem() == internal.getItem() && (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || internal.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getMetadata() == internal.getMetadata());
        
        if(itemMatches) {
    
            if(internalTag == null && stackTag == null) {
                return true;
            }
    
            // Lets just use the partial nbt
            if(!NBTConverter.from(internalTag, true).contains(NBTConverter.from(stackTag, true))) {
        
                return false;
            }
        }
        return itemMatches;
    }
    
    @Override
    public boolean matches(ILiquidStack liquid) {
        return false;
    }
    
    @Override
    public boolean contains(IIngredient ingredient) {
        if(ingredient == null)
            return false;
        List<IItemStack> iitems = ingredient.getItems();
        return iitems != null && iitems.size() == 1 && matches(iitems.get(0));
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
        return new IngredientItem(this, null, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS, new IItemTransformer[]{transformer});
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
    public IItemStack withDisplayName(String name) {
        NBTTagCompound tagComp;
        
        if(!this.stack.hasTagCompound() || this.stack.getTagCompound() == null) {
            tagComp = new NBTTagCompound();
        } else {
            tagComp = this.stack.getTagCompound();
        }
        
        NBTTagCompound display;
        if(!tagComp.hasKey("display") || !(tagComp.getTag("display") instanceof NBTTagCompound)) {
            display = new NBTTagCompound();
        } else {
            display = (NBTTagCompound) tagComp.getTag("display");
        }
        
        display.setString("Name", name);
        tagComp.setTag("display", display);
        
        ItemStack newStack = stack.copy();
        newStack.setTagCompound(tagComp);
        
        return new MCItemStack(newStack);
    }
    
    @Override
    public IItemStack withLore(String[] lore) {
        NBTTagCompound tagComp;
        
        if(!this.stack.hasTagCompound() || this.stack.getTagCompound() == null) {
            tagComp = new NBTTagCompound();
        } else {
            tagComp = this.stack.getTagCompound();
        }
        
        NBTTagCompound display;
        if(!tagComp.hasKey("display") || !(tagComp.getTag("display") instanceof NBTTagCompound)) {
            display = new NBTTagCompound();
        } else {
            display = (NBTTagCompound) tagComp.getTag("display");
        }
        
        NBTTagList loreList;
        if(!display.hasKey("Lore") || !(display.getTag("Lore") instanceof NBTTagList)) {
            loreList = new NBTTagList();
        } else {
            loreList = (NBTTagList) display.getTag("Lore");
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
    
    @Override
    public List<String> getToolClasses() {
        return new ArrayList<>(stack.getItem().getToolClasses(stack));
    }
    
    @Override
    public int getItemEnchantability() {
        return stack.getItem().getItemEnchantability(stack);
    }
    
    @Override
    public IItemStack getContainerItem() {
        return new MCItemStack(stack.getItem().getContainerItem(stack));
    }
    
    @Override
    public boolean hasContainerItem() {
        return stack.getItem().hasContainerItem(stack);
    }
    
    @Override
    public IEntityItem createEntityItem(IWorld world, int x, int y, int z) {
        return CraftTweakerMC.getIEntityItem(new EntityItem(CraftTweakerMC.getWorld(world), x, y, z, stack));
    }
    
    @Override
    public IEntityItem createEntityItem(IWorld world, float x, float y, float z) {
        return CraftTweakerMC.getIEntityItem(new EntityItem(CraftTweakerMC.getWorld(world), x, y, z, stack));
    }
    
    @Override
    public IEntityItem createEntityItem(IWorld world, IBlockPos pos) {
        return createEntityItem(world, pos.getX(), pos.getY(), pos.getZ());
    }
    
    @Override
    public boolean isBeaconPayment() {
        return stack.getItem().isBeaconPayment(stack);
    }
    
    @Override
    public boolean canPlaceOn(IBlockDefinition block) {
        return stack.canPlaceOn((Block) block.getInternal());
    }
    
    @Override
    public boolean canDestroy(IBlockDefinition block) {
        return stack.canDestroy((Block) block.getInternal());
    }
    
    @Override
    public boolean canHarvestBlock(IBlockState block) {
        return stack.canHarvestBlock((net.minecraft.block.state.IBlockState) block.getInternal());
    }
    
    @Override
    public int getRepairCost() {
        return stack.getRepairCost();
    }
    
    @Override
    public void setRepairCost(int repairCost) {
        stack.setRepairCost(repairCost);
    }
    
    @Override
    public boolean canEditBlocks() {
        return false;
    }
    
    @Override
    public boolean isOnItemFrame() {
        return stack.isOnItemFrame();
    }
    
    @Override
    public boolean isItemEnchanted() {
        return stack.isItemEnchanted();
    }
    
    @Override
    public boolean isItemDamaged() {
        return stack.isItemDamaged();
    }
    
    @Override
    public boolean isDamageable() {
        return stack.isItemStackDamageable();
    }
    
    @Override
    public boolean isStackable() {
        return stack.isStackable();
    }
    
    @Override
    public void addEnchantment(IEnchantment enchantment) {
        stack.addEnchantment((Enchantment) enchantment.getDefinition().getInternal(), enchantment.getLevel());
    }
    
    @Override
    public boolean canApplyAtEnchantingTable(IEnchantmentDefinition enchantment) {
        return stack.getItem().canApplyAtEnchantingTable(stack, (Enchantment) enchantment.getInternal());
    }
    
    @Override
    public List<IEnchantment> getEnchantments() {
        ArrayList<IEnchantment> output = new ArrayList<>();
        if(stack.isEmpty() || !stack.hasTagCompound())
            return output;
        IData tag = NBTConverter.from(stack.getTagCompound(), false);
        if(tag.contains(new DataString("ench"))) {
            List<IData> enchantmentList = tag.memberGet("ench").asList();
            enchantmentList.stream().filter(ench -> ench.contains(new DataString("id")) && ench.contains(new DataString("lvl"))).forEach(ench -> output.add(new MCEnchantment(ench.memberGet("id").asInt(), ench.memberGet("lvl").asInt())));
        }
        return output;
    }
    
    @Override
    public boolean isItemEnchantable() {
        return stack.isItemEnchantable();
    }
    
    @Override
    public boolean hasEffect() {
        return stack.hasEffect();
    }
    
    @Override
    public boolean hasDisplayName() {
        return stack.hasDisplayName();
    }
    
    @Override
    public void clearCustomName() {
        stack.clearCustomName();
    }
    
    @Override
    public boolean hasTag() {
        return stack.hasTagCompound();
    }
    
    @Override
    public void damageItem(int amount, IEntity entity) {
        CraftTweakerAPI.logWarning("Cannot damage IItemStack, make it mutable with `mutable` first!");
    }
    
    @Override
    public int getMetadata() {
        return stack.getMetadata();
    }
    
    @Override
    public boolean getHasSubtypes() {
        return stack.getHasSubtypes();
    }
    
    @Override
    public float getStrengthAgainstBlock(IBlockState blockState) {
        return stack.getDestroySpeed((net.minecraft.block.state.IBlockState) blockState.getInternal());
    }
    
    @Override
    public IItemStack splitStack(int amount) {
        return new MCItemStack(stack.splitStack(amount));
    }
    
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    @Override
    public int getItemBurnTime() {
        return TileEntityFurnace.getItemBurnTime(stack);
    }
    
    @Override
    public boolean showsDurabilityBar() {
        return stack.getItem().showDurabilityBar(stack);
    }
    
    @Override
    public boolean hasCustomEntity() {
        return stack.getItem().hasCustomEntity(stack);
    }

    @Override
    public IMutableItemStack mutable() {
        return origin.isEmpty() ? null : new MCMutableItemStack(origin);
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
    
    @Override
    public String toCommandString() {
        return toString();
    }
    
    @Override
    public boolean isFood() {
        return stack.getItem() instanceof ItemFood;
    }
    
    @Override
    public float getSaturation() {
        if(isFood()) {
            ItemFood item = (ItemFood) stack.getItem();
            return item.getSaturationModifier(stack);
        } else {
            return 0;
        }
    }
    
    @Override
    public int getHealAmount() {
        if(isFood()) {
            ItemFood item = (ItemFood) stack.getItem();
            return item.getHealAmount(stack);
        } else {
            return 0;
        }
    }
    
    @Override
    public int getHarvestLevel(String toolClass) {
        return stack.getItem().getHarvestLevel(stack, toolClass, null, null);
    }
    
    @Override
    public int getHarvestLevel(String toolClass, IPlayer player, IBlockState blockState) {
        return stack.getItem().getHarvestLevel(stack, toolClass, CraftTweakerMC.getPlayer(player), CraftTweakerMC.getBlockState(blockState));
    }
}
