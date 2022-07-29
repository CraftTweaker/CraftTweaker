package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.item.ActionSetFood;
import com.blamejared.crafttweaker.api.action.item.ActionSetItemProperty;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.util.AttributeUtil;
import com.blamejared.crafttweaker.api.util.EnchantmentUtil;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessItem;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IItemStack")
@Document("vanilla/api/item/IItemStack")
public interface IItemStack extends IIngredient, IIngredientWithAmount {
    
    @ZenCodeType.Field
    String CRAFTTWEAKER_DATA_KEY = "CraftTweakerData";
    
    @ZenCodeType.Field
    UUID BASE_ATTACK_DAMAGE_UUID = AccessItem.crafttweaker$getBASE_ATTACK_DAMAGE_UUID();
    
    @ZenCodeType.Field
    UUID BASE_ATTACK_SPEED_UUID = AccessItem.crafttweaker$getBASE_ATTACK_SPEED_UUID();
    
    /**
     * Creates a copy
     */
    @ZenCodeType.Method
    IItemStack copy();
    
    /**
     * Gets the registry name for the Item in this IItemStack
     *
     * @return registry name of the Item this IItemStack represents
     */
    @ZenCodeType.Getter("registryName")
    default ResourceLocation getRegistryName() {
        
        return Services.REGISTRY.getRegistryKey(getInternal().getItem());
    }
    
    /**
     * Gets owning mod for the Item in this IItemStack
     *
     * @return owning mod of the Item this IItemStack represents
     */
    @ZenCodeType.Getter("owner")
    default String getOwner() {
        
        return getRegistryName().getNamespace();
    }
    
    /**
     * Returns if the ItemStack is empty
     *
     * @return true if empty, false if not
     */
    @Override
    default boolean isEmpty() {
        
        return getInternal().isEmpty();
    }
    
    /**
     * Returns the max stack size of the Item in the ItemStack
     *
     * @return Max stack size of the Item.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxStackSize")
    default int getMaxStackSize() {
        
        return getInternal().getItem().getMaxStackSize();
    }
    
    /**
     * Sets the max stacksize of the Item.
     *
     * @param newMaxStackSize The new max stack size of the Item.
     *
     * @docParam newMaxStackSize 16
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("maxStackSize")
    default void setMaxStackSize(int newMaxStackSize) {
        
        CraftTweakerAPI.apply(new ActionSetItemProperty<>(this, "Max Stack Size", newMaxStackSize, this.getInternal()
                .getItem().getMaxStackSize(), ((AccessItem) this.getInternal()
                .getItem())::crafttweaker$setMaxStackSize));
    }
    
    /**
     * Returns the rarity of the Item in the ItemStack
     *
     * @return Rarity of the Item.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("rarity")
    default Rarity getRarity() {
        
        return getInternal().getRarity();
    }
    
    /**
     * Sets the rarity of the Item.
     *
     * @param newRarity The new rarity of the Item.
     *
     * @docParam newRarity Rarity.UNCOMMON
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("rarity")
    default void setRarity(Rarity newRarity) {
        
        CraftTweakerAPI.apply(new ActionSetItemProperty<>(this, "Rarity", newRarity, this.getInternal()
                .getRarity(), ((AccessItem) this.getInternal()
                .getItem())::crafttweaker$setRarity));
    }
    
    
    /**
     * Sets the lore of the ItemStack
     *
     * @param lore the new Lore of the ItemStack.
     *
     * @docParam lore new crafttweaker.api.text.TextComponent("I am the lore I speak for the trees");
     */
    @ZenCodeType.Method
    default IItemStack withLore(@ZenCodeType.Nullable Component... lore) {
        
        return modify(itemStack -> {
            CompoundTag tag = itemStack.getOrCreateTagElement(ItemStack.TAG_DISPLAY);
            if(lore != null || lore.length == 0) {
                ListTag listtag = new ListTag();
                for(Component component : lore) {
                    listtag.add(StringTag.valueOf(Component.Serializer.toJson(component)));
                }
                tag.put("Lore", listtag);
            } else {
                tag.remove(ItemStack.TAG_LORE);
            }
        });
    }
    
    /**
     * Gets the display name of the ItemStack
     *
     * @return formatted display name of the ItemStack.
     */
    @ZenCodeType.Getter("displayName")
    default Component getDisplayName() {
        
        return getInternal().getDisplayName();
    }
    
    /**
     * Sets the display name of the ItemStack
     *
     * @param name New name of the stack.
     *
     * @docParam name "totally not dirt"
     */
    @ZenCodeType.Method
    default IItemStack withDisplayName(Component name) {
        
        return modify(itemStack -> itemStack.setHoverName(name));
    }
    
    /**
     * Gets the hover name of the ItemStack.
     *
     * <p>This will give the raw name without the formatting that 'displayName' applies. </p>
     *
     * @return The hover name of the ItemStack.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("hoverName")
    default Component getHoverName() {
        
        return getInternal().getHoverName();
    }
    
    /**
     * Clears any custom name set for this ItemStack
     */
    @ZenCodeType.Method
    default void resetHoverName() {
        
        getInternal().resetHoverName();
    }
    
    /**
     * Returns true if the ItemStack has a display name.
     *
     * @return true if a display name is present on the ItemStack.
     */
    @ZenCodeType.Getter("hasCustomHoverName")
    default boolean hasDisplayName() {
        
        return getInternal().hasCustomHoverName();
    }
    
    /**
     * Returns true if this ItemStack has a foil effect.
     *
     * Foil is the glint / effect that is added to enchanted ItemStacks (and other items).
     *
     * @return true if this ItemStack has a foil effect.
     */
    @ZenCodeType.Getter("hasFoil")
    default boolean hasFoil() {
        
        return getInternal().hasFoil();
    }
    
    /**
     * Can this ItemStack be enchanted?
     *
     * @return true if this ItemStack can be enchanted.
     */
    @ZenCodeType.Getter("isEnchantable")
    default boolean isEnchantable() {
        
        return getInternal().isEnchantable();
    }
    
    /**
     * Is this ItemStack enchanted?
     *
     * @return true if this ItemStack is enchanted.
     */
    @ZenCodeType.Getter("isEnchanted")
    default boolean isEnchanted() {
        
        return getInternal().isEnchanted();
    }
    
    /**
     * Gets the base repair cost of the ItemStack, or 0 if no repair is defined.
     *
     * @return ItemStack repair cost or 0 if no repair is set.
     */
    @ZenCodeType.Getter("baseRepairCost")
    default int getBaseRepairCost() {
        
        return getInternal().getBaseRepairCost();
    }
    
    /**
     * Gets the amount of Items in the ItemStack
     *
     * @return ItemStack's amount
     */
    @ZenCodeType.Getter("amount")
    default int getAmount() {
        
        return getInternal().getCount();
    }
    
    /**
     * Sets the amount of the ItemStack
     *
     * @param amount new amount
     *
     * @docParam amount 3
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    default IItemStack setAmount(int amount) {
        
        return modify(itemStack -> itemStack.setCount(amount));
    }
    
    /**
     * Grows this IItemStack's stack size by the given amount, or 1 if no amount is given.
     *
     * @param amount The amount to grow by.
     *
     * @return This IItemStack if mutable, a new one with the new amount otherwise.
     *
     * @docParam amount 2
     */
    @ZenCodeType.Method
    default IItemStack grow(@ZenCodeType.OptionalInt(1) int amount) {
        
        return setAmount(getAmount() + amount);
    }
    
    /**
     * Shrinks this IItemStack's stack size by the given amount, or 1 if no amount is given.
     *
     * @param amount The amount to shrink by.
     *
     * @return This IItemStack if mutable, a new one with the new amount otherwise.
     *
     * @docParam amount 2
     */
    @ZenCodeType.Method
    default IItemStack shrink(@ZenCodeType.OptionalInt(1) int amount) {
        
        return setAmount(getAmount() - amount);
    }
    
    /**
     * Returns if the ItemStack can have an amount greater than 1
     * I.E Swords and tools are not stackable, sticks are.
     *
     * @return true if this ItemStack can contain more than one item.
     */
    @ZenCodeType.Getter("stackable")
    default boolean isStackable() {
        
        return getInternal().isStackable();
    }
    
    /**
     * Sets the damage of the ItemStack
     *
     * @param damage the new damage value
     *
     * @docParam damage 10
     */
    @ZenCodeType.Method
    default IItemStack withDamage(int damage) {
        
        return modify(itemStack -> itemStack.setDamageValue(damage));
    }
    
    /**
     * Adds an AttributeModifier to this IItemStack using a specific UUID.
     *
     * The UUID can be used to override an existing attribute on an ItemStack with this new modifier.
     * You can use `/ct hand attributes` to get the UUID of the attributes on an ItemStack.
     *
     * Attributes added with this method will only appear on this specific IItemStack.
     *
     * By defaults, adding a modifier will remove the default Attribute Modifiers on the Item, like the Diamond Chestplate's Armor and Toughness values.
     * When `preserveDefaults` is set to true (by default it is false.), the default Attribute Modifiers will be preserved when adding this modifier.
     * This means that if you were adding the `forge:nametag_distance` attribute to an Item, it would keep its default attributes (like Armor and Toughness values).
     *
     * @param uuid             The unique identifier of the modifier to replace.
     * @param attribute        The Attribute of the modifier.
     * @param name             The name of the modifier.
     * @param value            The value of the modifier.
     * @param operation        The operation of the modifier.
     * @param slotTypes        What slots the modifier is valid for.
     * @param preserveDefaults Should the default Item Attribute Modifiers be preserved when adding this modifier.
     *
     * @docParam attribute <attribute:minecraft:generic.attack_damage>
     * @docParam uuid "8c1b5535-9f79-448b-87ae-52d81480aaa3"
     * @docParam name "Extra Power"
     * @docParam value 10
     * @docParam operation AttributeOperation.ADDITION
     * @docParam slotTypes [<constant:minecraft:equipmentslot:chest>]
     * @docParam preserveDefaults true
     */
    @ZenCodeType.Method
    default IItemStack withAttributeModifier(Attribute attribute, String uuid, String name, double value, AttributeModifier.Operation operation, EquipmentSlot[] slotTypes, @ZenCodeType.OptionalBoolean boolean preserveDefaults) {
        
        return withAttributeModifier(attribute, UUID.fromString(uuid), name, value, operation, slotTypes, preserveDefaults);
    }
    
    /**
     * Adds an AttributeModifier to this IItemStack using a specific UUID.
     *
     * The UUID can be used to override an existing attribute on an ItemStack with this new modifier.
     * You can use `/ct hand attributes` to get the UUID of the attributes on an ItemStack.
     *
     * Attributes added with this method will only appear on this specific IItemStack.
     *
     * By defaults, adding a modifier will remove the default Attribute Modifiers on the Item, like the Diamond Chestplate's Armor and Toughness values.
     * When `preserveDefaults` is set to true (by default it is false.), the default Attribute Modifiers will be preserved when adding this modifier.
     * This means that if you were adding the `forge:nametag_distance` attribute to an Item, it would keep its default attributes (like Armor and Toughness values).
     *
     * @param uuid             The unique identifier of the modifier to replace.
     * @param attribute        The Attribute of the modifier.
     * @param name             The name of the modifier.
     * @param value            The value of the modifier.
     * @param operation        The operation of the modifier.
     * @param slotTypes        What slots the modifier is valid for.
     * @param preserveDefaults Should the default Item Attribute Modifiers be preserved when adding this modifier.
     *
     * @docParam attribute <attribute:minecraft:generic.attack_damage>
     * @docParam uuid "8c1b5535-9f79-448b-87ae-52d81480aaa3"
     * @docParam name "Extra Power"
     * @docParam value 10
     * @docParam operation AttributeOperation.ADDITION
     * @docParam slotTypes [<constant:minecraft:equipmentslot:chest>]
     * @docParam preserveDefaults true
     */
    @ZenCodeType.Method
    default IItemStack withAttributeModifier(Attribute attribute, UUID uuid, String name, double value, AttributeModifier.Operation operation, EquipmentSlot[] slotTypes, @ZenCodeType.OptionalBoolean boolean preserveDefaults) {
        
        return modify(itemStack -> {
            for(EquipmentSlot slotType : slotTypes) {
                if(preserveDefaults) {
                    AttributeUtil.addAttributeModifier(itemStack, attribute, new AttributeModifier(uuid, name, value, operation), slotType);
                } else {
                    itemStack.addAttributeModifier(attribute, new AttributeModifier(uuid, name, value, operation), slotType);
                }
            }
        });
    }
    
    /**
     * Adds an AttributeModifier to this IItemStack.
     *
     * The UUID can be used to override an existing attribute on an ItemStack with this new modifier.
     * You can use `/ct hand attributes` to get the UUID of the attributes on an ItemStack.
     *
     * Attributes added with this method will only appear on this specific IItemStack.
     *
     * By defaults, adding a modifier will remove the default Attribute Modifiers on the Item, like the Diamond Chestplate's Armor and Toughness values.
     * When `preserveDefaults` is set to true (by default it is false.), the default Attribute Modifiers will be preserved when adding this modifier.
     * This means that if you were adding the `forge:nametag_distance` attribute to an Item, it would keep its default attributes (like Armor and Toughness values).
     *
     * @param attribute        The Attribute of the modifier.
     * @param name             The name of the modifier.
     * @param value            The value of the modifier.
     * @param operation        The operation of the modifier.
     * @param slotTypes        What slots the modifier is valid for.
     * @param preserveDefaults Should the default Item Attribute Modifiers be preserved when adding this modifier.
     *
     * @docParam attribute <attribute:minecraft:generic.attack_damage>
     * @docParam name "Extra Power"
     * @docParam value 10
     * @docParam operation AttributeOperation.ADDITION
     * @docParam slotTypes [<constant:minecraft:equipmentslot:chest>]
     * @docParam preserveDefaults true
     */
    @ZenCodeType.Method
    default IItemStack withAttributeModifier(Attribute attribute, String name, double value, AttributeModifier.Operation operation, EquipmentSlot[] slotTypes, @ZenCodeType.OptionalBoolean boolean preserveDefaults) {
        
        return modify(itemStack -> {
            for(EquipmentSlot slotType : slotTypes) {
                if(preserveDefaults) {
                    AttributeUtil.addAttributeModifier(itemStack, attribute, new AttributeModifier(name, value, operation), slotType);
                } else {
                    itemStack.addAttributeModifier(attribute, new AttributeModifier(name, value, operation), slotType);
                }
            }
        });
    }
    
    /**
     * Gets the Attributes and the AttributeModifiers on this IItemStack for the given EquipmentSlot
     *
     * @param slotType The slot to get the Attributes for.
     *
     * @return A Map of Attribute to a List of AttributeModifier for the given EquipmentSlot.
     *
     * @docParam slotType <constant:minecraft:equipmentslot:chest>
     */
    @ZenCodeType.Method
    default Map<Attribute, List<AttributeModifier>> getAttributes(EquipmentSlot slotType) {
        
        // I don't think we expose Collection, so just convert it to a list.
        return getInternal().getAttributeModifiers(slotType)
                .asMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, attributeAttributeModifierEntry -> new ArrayList<>(attributeAttributeModifierEntry
                        .getValue())));
    }
    
    /**
     * Returns if the ItemStack is damageable
     * I.E Swords and tools are damageable, sticks are not.
     *
     * @return true if this ItemStack can take damage.
     */
    @ZenCodeType.Getter("damageableItem")
    default boolean isDamageableItem() {
        
        return getInternal().isDamageableItem();
    }
    
    /**
     * Returns if the ItemStack is damaged
     * I.E a Swords that is no at full durability is damaged.
     *
     * @return true if this ItemStack is damaged.
     */
    @ZenCodeType.Getter("damaged")
    default boolean isDamaged() {
        
        return getInternal().isDamaged();
    }
    
    /**
     * Returns the max damage of the ItemStack
     * This is the max durability of the ItemStack.
     *
     * @return The ItemStack's max durability.
     */
    @ZenCodeType.Getter("maxDamage")
    default int getMaxDamage() {
        
        return getInternal().getMaxDamage();
    }
    
    /**
     * Sets the max damage of the ItemStack.
     *
     * Setting the damage to `0` will make the item unbreakable.
     *
     * @param newMaxDamage The new max damage of the ItemStack
     *
     * @docParam maxDamage 5
     */
    @ZenCodeType.Setter("maxDamage")
    default void setMaxDamage(int newMaxDamage) {
        
        CraftTweakerAPI.apply(new ActionSetItemProperty<>(this, "Max Damage", newMaxDamage, this.getInternal()
                .getMaxDamage(), ((AccessItem) this.getInternal()
                .getItem())::crafttweaker$setMaxDamage));
    }
    
    /**
     * Returns the unlocalized Name of the Item in the ItemStack
     *
     * @return the unlocalized name.
     */
    @ZenCodeType.Getter("descriptionId")
    default String getDescriptionId() {
        
        return getInternal().getDescriptionId();
    }
    
    /**
     * Sets the tag for the ItemStack.
     *
     * @param tag The tag to set.
     *
     * @return This itemStack if it is mutable, a new one with the changed property otherwise
     *
     * @docParam tag {Display: {lore: ["Hello"]}}
     */
    @ZenCodeType.Method
    default IItemStack withTag(MapData tag) {
        
        return modify(itemStack -> itemStack.setTag(tag.getInternal()));
    }
    
    /**
     * Removes the tag from this ItemStack.
     *
     * @return This itemStack if it is mutable, a new one with the changed property otherwise
     */
    @ZenCodeType.Method
    default IItemStack withoutTag() {
        
        return modify(itemStack -> itemStack.setTag(null));
    }
    
    
    /**
     * Returns true if this ItemStack has a Tag
     *
     * @return true if tag is present.
     */
    @ZenCodeType.Getter("hasTag")
    default boolean hasTag() {
        
        return getInternal().hasTag();
    }
    
    /**
     * Returns the NBT tag attached to this ItemStack.
     *
     * @return MapData of the ItemStack NBT Tag, null if it doesn't exist.
     */
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("tag")
    default MapData getTag() {
        
        return TagToDataConverter.convertCompound(getInternal().getTag());
    }
    
    /**
     * Returns the NBT tag attached to this ItemStack or makes a new tag.
     *
     * @return MapData of the ItemStack NBT Tag, empty tag if it doesn't exist.
     */
    @ZenCodeType.Method
    default MapData getOrCreateTag() {
        
        if(getInternal().getTag() == null) {
            getInternal().setTag(new CompoundTag());
        }
        return getTag();
    }
    
    @Override
    default boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        ItemStack stack1 = getInternal();
        ItemStack stack2 = stack.getInternal();
        
        if(stack1.isEmpty() != stack2.isEmpty()) {
            return false;
        }
        if(stack1.getItem() != stack2.getItem()) {
            return false;
        }
        if(stack1.getCount() > stack2.getCount()) {
            return false;
        }
        // This is really just an early exit, since damage is NBT based, it is checked again in the NBT contains
        if(!ignoreDamage) {
            if(stack1.getDamageValue() != stack2.getDamageValue()) {
                return false;
            }
        }
        CompoundTag stack1Tag = stack1.getTag();
        CompoundTag stack2Tag = stack2.getTag();
        if(stack1Tag == null && stack2Tag == null) {
            return true;
        }
        
        // Lets just use the partial nbt
        MapData stack2Data = TagToDataConverter.convertCompound(stack2Tag);
        MapData stack1Data = TagToDataConverter.convertCompound(stack1Tag);
        if(stack1Data == null) {
            return true;
        }
        if(ignoreDamage) {
            stack1Data = stack1Data.copyInternal();
            stack1Data.remove("Damage");
            if(stack2Data != null) {
                stack2Data = stack2Data.copyInternal();
                stack2Data.remove("Damage");
            }
        }
        return stack2Data != null && stack2Data.contains(stack1Data);
    }
    
    
    /**
     * Gets the use duration of the ItemStack
     *
     * @return use duration
     */
    @ZenCodeType.Getter("useDuration")
    default int getUseDuration() {
        
        return getInternal().getUseDuration();
    }
    
    /**
     * Returns true if this stack is considered a crossbow item
     *
     * @return true if stack is a crossbow
     */
    @ZenCodeType.Getter("useOnRelease")
    default boolean useOnRelease() {
        
        return getInternal().useOnRelease();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("food")
    @ZenCodeType.Nullable
    default FoodProperties getFood() {
        
        return getInternal().getItem().getFoodProperties();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("food")
    default void setFood(@ZenCodeType.Nullable FoodProperties food) {
        
        CraftTweakerAPI.apply(new ActionSetFood(this, food, this.getInternal()
                .getItem()
                .getFoodProperties()));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isEdible")
    default boolean isEdible() {
        
        return getInternal().isEdible();
    }
    
    @ZenCodeType.Getter("burnTime")
    default int getBurnTime() {
        
        return Services.EVENT.getBurnTime(this);
    }
    
    /**
     * Checks if this IItemStack burns when thrown into fire / lava or damaged by fire.
     *
     * @return True if this IItemStack is immune to fire. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("fireResistant")
    default boolean isFireResistant() {
        
        return getInternal().getItem().isFireResistant();
    }
    
    /**
     * Sets if this IItemStack is immune to fire / lava.
     *
     * If true, the item will not burn when thrown into fire or lava.
     *
     * @param fireResistant Should the item be immune to fire.
     *
     * @docParam immuneToFire true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("fireResistant")
    default void setFireResistant(boolean fireResistant) {
        
        CraftTweakerAPI.apply(new ActionSetItemProperty<>(this, "Fire Resistant", fireResistant, this.getInternal()
                .getItem().isFireResistant(), ((AccessItem) this.getInternal()
                .getItem())::crafttweaker$setFireResistant));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MOD)
    default Percentaged<IItemStack> percent(double percentage) {
        
        return new Percentaged<>(this, percentage / 100.0D, iItemStack -> iItemStack.getCommandString() + " % " + percentage);
    }
    
    //    @ZenCodeType.Method
    //    default WeightedEntry.Wrapper<IItemStack> weight(double weight) {
    //
    //        return new WeightedEntry.Wrapper<>(this, Weight.of(weight));
    //    }
    
    @ZenCodeType.Caster(implicit = true)
    default Percentaged<IItemStack> asWeightedItemStack() {
        
        return percent(100.0D);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("definition")
    @ZenCodeType.Caster(implicit = true)
    default Item getDefinition() {
        
        return getInternal().getItem();
    }
    
    @ZenCodeType.Method
    IItemStack asMutable();
    
    @ZenCodeType.Method
    IItemStack asImmutable();
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isImmutable")
    boolean isImmutable();
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isMutable")
    default boolean isMutable() {
        
        return !isImmutable();
    }
    
    
    @ZenCodeType.Getter("damage")
    default int getDamage() {
        
        return getInternal().getDamageValue();
    }
    
    //    @ZenCodeType.Getter("toolTypes")
    //    default ToolType[] getToolTypes() {
    //
    //        return getInternal().getToolTypes().toArray(new ToolType[0]);
    //    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantments")
    default Map<Enchantment, Integer> getEnchantments() {
        
        return EnchantmentHelper.getEnchantments(getInternal());
    }
    
    /**
     * Sets the enchantments on this IItemStack.
     *
     * @param enchantments The new enchantments
     *
     * @return This itemStack if it is mutable, a new one with the enchantments otherwise
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("enchantments")
    default IItemStack setEnchantments(Map<Enchantment, Integer> enchantments) {
        
        return modify(newStack -> EnchantmentUtil.setEnchantments(enchantments, newStack));
    }
    
    
    /**
     * Gets the level of the given enchantment on the item. Returns 0 if the item doesn't have the given enchantment.
     */
    @ZenCodeType.Method
    default int getEnchantmentLevel(Enchantment enchantment) {
        
        return getEnchantments().getOrDefault(enchantment, 0);
    }
    
    /**
     * Enchants this IItemStack with the given Enchantment.
     *
     * @param enchantment The enchantment to add.
     * @param level       The level of the enchantment
     *
     * @return This itemStack if it is mutable, a new one with the enchantment added otherwise
     *
     * @docParam enchantment <enchantment:minecraft:riptide>
     * @docParam level 2
     */
    @ZenCodeType.Method
    default IItemStack withEnchantment(Enchantment enchantment, @ZenCodeType.OptionalInt(1) int level) {
        
        return modify(itemStack -> {
            Map<Enchantment, Integer> enchantments = getEnchantments();
            enchantments.put(enchantment, level);
            EnchantmentUtil.setEnchantments(enchantments, itemStack);
        });
    }
    
    /**
     * Removes the given enchantment from this IItemStack.
     *
     * @param enchantment The enchantment to remove.
     *
     * @return This itemStack if it is mutable, a new one with the enchantment removed otherwise
     *
     * @docParam enchantment <enchantment:minecraft:riptide>
     */
    @ZenCodeType.Method
    default IItemStack removeEnchantment(Enchantment enchantment) {
        
        return modify(itemStack -> {
            Map<Enchantment, Integer> enchantments = getEnchantments();
            enchantments.remove(enchantment);
            EnchantmentUtil.setEnchantments(enchantments, itemStack);
        });
    }
    
    /**
     * Gets the internal {@link ItemStack} for this IItemStack.
     *
     * @return internal ItemStack
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    ItemStack getInternal();
    
    @Override
    default Ingredient asVanillaIngredient() {
        
        if(getInternal().isEmpty()) {
            return Ingredient.EMPTY;
        }
        
        if(!getInternal().hasTag()) {
            return Ingredient.of(getImmutableInternal());
        }
        return Services.REGISTRY.getIngredientPartialTag(getImmutableInternal());
    }
    
    @ZenCodeType.Method
    default ItemStack getImmutableInternal() {
        
        return getInternal().copy();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    default IIngredientWithAmount asIIngredientWithAmount() {
        
        return this;
    }
    
    @Override
    default IItemStack getIngredient() {
        
        return this;
    }
    
    IItemStack modify(Consumer<ItemStack> stackModifier);
    
}
