package crafttweaker.api.item;

import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.data.IData;
import crafttweaker.api.enchantments.IEnchantment;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;

import java.util.List;

public final class ItemStackUnknown implements IItemStack {

    public static final ItemStackUnknown INSTANCE = new ItemStackUnknown();

    private ItemStackUnknown() {}

    @Override
    public IItemDefinition getDefinition() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isItemBlock() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public String getName() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public String getDisplayName() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public void setDisplayName(String name) {
throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public int getMaxStackSize() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public void setMaxStackSize(int size) {
throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public float getBlockHardness() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public void setBlockHardness(float hardness) {
throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public int getDamage() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public int getMaxDamage() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public void setMaxDamage(int damage) {
throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IData getTag() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public ILiquidStack getLiquid() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public String getMark() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public int getAmount() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public List<IItemStack> getItems() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack[] getItemArray() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public List<ILiquidStack> getLiquids() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack amount(int amount) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IIngredient or(IIngredient ingredient) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IIngredient transformNew(IItemTransformerNew transformer) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IIngredient only(IItemCondition condition) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IIngredient marked(String mark) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean matches(IItemStack item) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean matchesExact(IItemStack item) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean matches(ILiquidStack liquid) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean contains(IIngredient ingredient) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack applyNewTransform(IItemStack item) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean hasNewTransformers() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean hasTransformers() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IIngredient transform(IItemTransformer transformer) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public Object getInternal() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public String toCommandString() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public WeightedItemStack percent(float p) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public WeightedItemStack weight(float p) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IIngredient anyDamage() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack withDamage(int damage) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack withAmount(int amount) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack anyAmount() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack withTag(IData tag) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack withEmptyTag() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack removeTag(String tag) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack updateTag(IData tagUpdate) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IBlock asBlock() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public List<IOreDictEntry> getOres() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack withDisplayName(String name) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack withLore(String[] lore) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public List<String> getToolClasses() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public int getItemEnchantability() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack getContainerItem() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isBeaconPayment() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean canPlaceOn(IBlockDefinition block) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean canDestroy(IBlockDefinition block) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean canHarvestBlock(IBlockState block) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public int getRepairCost() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public void setRepairCost(int repairCost) {
throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean canEditBlocks() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isOnItemFrame() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isItemEnchanted() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isItemDamaged() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isDamageable() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isStackable() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public void addEnchantment(IEnchantment enchantment) {
throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean canApplyAtEnchantingTable(IEnchantmentDefinition enchantment) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public List<IEnchantment> getEnchantments() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isItemEnchantable() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean hasEffect() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean hasDisplayName() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public void clearCustomName() {
throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean hasTag() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public void damageItem(int amount, IEntity entity) {
throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public int getMetadata() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean getHasSubtypes() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public float getStrengthAgainstBlock(IBlockState blockState) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IItemStack splitStack(int amount) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean isEmpty() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public int getItemBurnTime() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean showsDurabilityBar() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean hasCustomEntity() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public boolean hasContainerItem() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IEntityItem createEntityItem(IWorld world, int x, int y, int z) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IEntityItem createEntityItem(IWorld world, IBlockPos pos) {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }

    @Override
    public IMutableItemStack mutable() {
        throw new IllegalStateException("ItemStackUnknown is only supposed to be used internally!");
    }
}
