package com.blamejared.crafttweaker.natives.item;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.item.ActionSetItemProperty;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.LockCode;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.AdventureModePredicate;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.DebugStickState;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.component.MapDecorations;
import net.minecraft.world.item.component.MapItemColor;
import net.minecraft.world.item.component.MapPostProcessing;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.saveddata.maps.MapId;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/item/ItemDefinition")
@NativeTypeRegistration(value = Item.class, zenCodeName = "crafttweaker.api.item.ItemDefinition")
@TaggableElement(value = "minecraft:item")
public class ExpandItem {
    
    @ZenCodeType.StaticExpansionMethod
    public static DataComponentMap commonItemComponents() {
        
        return DataComponents.COMMON_ITEM_COMPONENTS;
    }
    
    public static <T> void setComponent(Item internal, DataComponentType<T> type, T value) {
        
        CraftTweakerAPI.apply(new ActionSetItemProperty<>(internal, type, value));
    }
    
    @ZenCodeType.Method
    public static <T> void setComponent(Item internal, Class<T> clazz, DataComponentType<T> type, T value) {
        
        setComponent(internal, type, value);
    }
    
    @ZenCodeType.Method
    public static void setJsonComponent(Item internal, DataComponentType type, @ZenCodeType.Nullable IData value) {
        
        if(value == null) {
            remove(internal, type);
        } else {
            Codec<?> codec = type.codecOrThrow();
            DataResult<? extends Pair<?, IData>> decode = codec.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
            setComponent(internal, type, decode.getOrThrow().getFirst());
        }
    }
    
    @ZenCodeType.Method
    public static void remove(Item internal, DataComponentType type) {
        
        setComponent(internal, type, null);
    }
    
    @ZenCodeType.Setter("customData")
    public static void setCustomData(Item internal, CustomData customData) {
        
        setComponent(internal, DataComponents.CUSTOM_DATA, customData);
    }
    
    @ZenCodeType.Setter("maxStackSize")
    public static void setMaxStackSize(Item internal, int maxStackSize) {
        
        setComponent(internal, DataComponents.MAX_STACK_SIZE, maxStackSize);
    }
    
    @ZenCodeType.Setter("maxDamage")
    public static void setMaxDamage(Item internal, int maxDamage) {
        
        setComponent(internal, DataComponents.MAX_DAMAGE, maxDamage);
    }
    
    @ZenCodeType.Setter("unbreakable")
    public static void setUnbreakable(Item internal, Unbreakable unbreakable) {
        
        setComponent(internal, DataComponents.UNBREAKABLE, unbreakable);
    }
    
    @ZenCodeType.Setter("customName")
    public static void setCustomName(Item internal, Component component) {
        
        setComponent(internal, DataComponents.CUSTOM_NAME, component);
    }
    
    @ZenCodeType.Setter("itemName")
    public static void setItemName(Item internal, Component component) {
        
        setComponent(internal, DataComponents.ITEM_NAME, component);
    }
    
    @ZenCodeType.Setter("lore")
    public static void setLore(Item internal, ItemLore lore) {
        
        setComponent(internal, DataComponents.LORE, lore);
    }
    
    @ZenCodeType.Setter("rarity")
    public static void setRarity(Item internal, Rarity rarity) {
        
        setComponent(internal, DataComponents.RARITY, rarity);
    }
    
    @ZenCodeType.Setter("enchantments")
    public static void setEnchantments(Item internal, ItemEnchantments enchantments) {
        
        setComponent(internal, DataComponents.ENCHANTMENTS, enchantments);
    }
    
    @ZenCodeType.Method
    public static void addEnchantment(Item internal, Enchantment enchantment, int level) {
        
        ItemEnchantments enchantments = internal.components()
                .getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchantments);
        mutable.set(Services.REGISTRY.holderOrThrow(Registries.ENCHANTMENT, enchantment), level);
        setComponent(internal, DataComponents.ENCHANTMENTS, mutable.toImmutable());
    }
    
    @ZenCodeType.Setter("canBreak")
    public static void setCanBreak(Item internal, AdventureModePredicate predicate) {
        
        setComponent(internal, DataComponents.CAN_BREAK, predicate);
    }
    
    @ZenCodeType.Setter("canPlaceOn")
    public static void setCanPlaceOn(Item internal, AdventureModePredicate predicate) {
        
        setComponent(internal, DataComponents.CAN_PLACE_ON, predicate);
    }
    
    @ZenCodeType.Setter("attributeModifiers")
    public static void setAttributeModifiers(Item internal, ItemAttributeModifiers modifiers) {
        
        setComponent(internal, DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
    }
    
    @ZenCodeType.Method
    public static void addAttributeModifier(Item internal, Attribute attribute, AttributeModifier modifier, EquipmentSlotGroup slot) {
        
        ItemAttributeModifiers modifiers = internal.components()
                .getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, internal.getDefaultAttributeModifiers());
        
        setComponent(internal, DataComponents.ATTRIBUTE_MODIFIERS, modifiers.withModifierAdded(Services.REGISTRY.holderOrThrow(Registries.ATTRIBUTE, attribute), modifier, slot));
    }
    
    @ZenCodeType.Setter("customModelData")
    public static void setCustomModelData(Item internal, CustomModelData modelData) {
        
        setComponent(internal, DataComponents.CUSTOM_MODEL_DATA, modelData);
    }
    
    @ZenCodeType.Setter("hideAdditionalTooltip")
    public static void setHideAdditionalTooltip(Item internal, boolean hideAdditionalTooltip) {
        
        if(hideAdditionalTooltip) {
            setComponent(internal, DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        } else {
            remove(internal, DataComponents.HIDE_ADDITIONAL_TOOLTIP);
        }
    }
    
    @ZenCodeType.Setter("hideTooltip")
    public static void setHideTooltip(Item internal, boolean hideTooltip) {
        
        if(hideTooltip) {
            setComponent(internal, DataComponents.HIDE_TOOLTIP, Unit.INSTANCE);
        } else {
            remove(internal, DataComponents.HIDE_TOOLTIP);
        }
    }
    
    @ZenCodeType.Setter("repairCost")
    public static void setRepairCost(Item internal, int cost) {
        
        setComponent(internal, DataComponents.REPAIR_COST, cost);
    }
    
    @ZenCodeType.Setter("creativeSlotLock")
    public static void setCreativeSlotLock(Item internal, boolean creativeSlotLock) {
        
        if(creativeSlotLock) {
            setComponent(internal, DataComponents.CREATIVE_SLOT_LOCK, Unit.INSTANCE);
        } else {
            remove(internal, DataComponents.CREATIVE_SLOT_LOCK);
        }
    }
    
    @ZenCodeType.Setter("enchantmentGlintOverride")
    public static void setEnchantmentGlintOverride(Item internal, boolean value) {
        
        setComponent(internal, DataComponents.ENCHANTMENT_GLINT_OVERRIDE, value);
    }
    
    @ZenCodeType.Setter("intangibleProjectile")
    public static void setIntangibleProjectile(Item internal, boolean intangibleProjectile) {
        
        if(intangibleProjectile) {
            setComponent(internal, DataComponents.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
        } else {
            remove(internal, DataComponents.INTANGIBLE_PROJECTILE);
        }
    }
    
    @ZenCodeType.Setter("food")
    public static void setFood(Item internal, FoodProperties food) {
        
        setComponent(internal, DataComponents.FOOD, food);
    }
    
    @ZenCodeType.Setter("fireResistant")
    public static void setFireResistant(Item internal, boolean intangibleProjectile) {
        
        if(intangibleProjectile) {
            setComponent(internal, DataComponents.FIRE_RESISTANT, Unit.INSTANCE);
        } else {
            remove(internal, DataComponents.FIRE_RESISTANT);
        }
    }
    
    @ZenCodeType.Setter("tool")
    public static void setTool(Item internal, Tool tool) {
        
        setComponent(internal, DataComponents.TOOL, tool);
    }
    
    @ZenCodeType.Setter("storedEnchantments")
    public static void setStoredEnchantments(Item internal, ItemEnchantments storedEnchantments) {
        
        setComponent(internal, DataComponents.STORED_ENCHANTMENTS, storedEnchantments);
    }
    
    @ZenCodeType.Setter("dyedColor")
    public static void setDyedColor(Item internal, DyedItemColor color) {
        
        setComponent(internal, DataComponents.DYED_COLOR, color);
    }
    
    @ZenCodeType.Setter("mapColor")
    public static void setMapColor(Item internal, MapItemColor mapColor) {
        
        setComponent(internal, DataComponents.MAP_COLOR, mapColor);
    }
    
    @ZenCodeType.Setter("mapId")
    public static void setMapId(Item internal, MapId id) {
        
        setComponent(internal, DataComponents.MAP_ID, id);
    }
    
    @ZenCodeType.Setter("mapDecorations")
    public static void setMapDecorations(Item internal, MapDecorations mapDecorations) {
        
        setComponent(internal, DataComponents.MAP_DECORATIONS, mapDecorations);
    }
    
    @ZenCodeType.Setter("mapPostProcessing")
    public static void setMapPostProcessing(Item internal, MapPostProcessing mapPostProcessing) {
        
        setComponent(internal, DataComponents.MAP_POST_PROCESSING, mapPostProcessing);
    }
    
    @ZenCodeType.Setter("chargedProjectiles")
    public static void setChargedProjectiles(Item internal, ChargedProjectiles chargedProjectiles) {
        
        setComponent(internal, DataComponents.CHARGED_PROJECTILES, chargedProjectiles);
    }
    
    @ZenCodeType.Setter("bundleContents")
    public static void setBundleContents(Item internal, BundleContents bundleContents) {
        
        setComponent(internal, DataComponents.BUNDLE_CONTENTS, bundleContents);
    }
    
    @ZenCodeType.Setter("potionContents")
    public static void setPotionContents(Item internal, PotionContents potionContents) {
        
        setComponent(internal, DataComponents.POTION_CONTENTS, potionContents);
    }
    
    @ZenCodeType.Setter("suspiciousStewEffects")
    public static void setSuspiciousStewEffects(Item internal, SuspiciousStewEffects suspiciousStewEffects) {
        
        setComponent(internal, DataComponents.SUSPICIOUS_STEW_EFFECTS, suspiciousStewEffects);
    }
    
    @ZenCodeType.Setter("writableBookContent")
    public static void setWritableBookContent(Item internal, WritableBookContent writableBookContent) {
        
        setComponent(internal, DataComponents.WRITABLE_BOOK_CONTENT, writableBookContent);
    }
    
    @ZenCodeType.Setter("writtenBookContent")
    public static void setWrittenBookContent(Item internal, WrittenBookContent writtenBookContent) {
        
        setComponent(internal, DataComponents.WRITTEN_BOOK_CONTENT, writtenBookContent);
    }
    
    @ZenCodeType.Setter("trim")
    public static void setTrim(Item internal, ArmorTrim trim) {
        
        setComponent(internal, DataComponents.TRIM, trim);
    }
    
    @ZenCodeType.Setter("debugStickState")
    public static void setDebugStickState(Item internal, DebugStickState debugStickState) {
        
        setComponent(internal, DataComponents.DEBUG_STICK_STATE, debugStickState);
    }
    
    @ZenCodeType.Setter("entityData")
    public static void setEntityData(Item internal, CustomData data) {
        
        setComponent(internal, DataComponents.ENTITY_DATA, data);
    }
    
    @ZenCodeType.Setter("bucketEntityData")
    public static void setBucketEntityData(Item internal, CustomData data) {
        
        setComponent(internal, DataComponents.BUCKET_ENTITY_DATA, data);
    }
    
    @ZenCodeType.Setter("blockEntityData")
    public static void setBlockEntityData(Item internal, CustomData data) {
        
        setComponent(internal, DataComponents.BLOCK_ENTITY_DATA, data);
    }
    
    @ZenCodeType.Setter("instrument")
    public static void setInstrument(Item internal, Instrument instrument) {
        
        setComponent(internal, DataComponents.INSTRUMENT, Services.REGISTRY.holderOrThrow(Registries.INSTRUMENT, instrument));
    }
    
    @ZenCodeType.Setter("ominousBottleAmplifier")
    public static void setOminousBottleAmplifier(Item internal, int amplifier) {
        
        setComponent(internal, DataComponents.OMINOUS_BOTTLE_AMPLIFIER, amplifier);
    }
    
    @ZenCodeType.Setter("recipes")
    public static void setRecipes(Item internal, List<ResourceLocation> recipes) {
        
        setComponent(internal, DataComponents.RECIPES, recipes);
    }
    
    @ZenCodeType.Setter("lodestoneTracker")
    public static void setLodestoneTracker(Item internal, LodestoneTracker tracker) {
        
        setComponent(internal, DataComponents.LODESTONE_TRACKER, tracker);
    }
    
    @ZenCodeType.Setter("fireworkExplosion")
    public static void setFireworkExplosion(Item internal, FireworkExplosion explosion) {
        
        setComponent(internal, DataComponents.FIREWORK_EXPLOSION, explosion);
    }
    
    @ZenCodeType.Setter("fireworks")
    public static void setFireworks(Item internal, Fireworks fireworks) {
        
        setComponent(internal, DataComponents.FIREWORKS, fireworks);
    }
    
    @ZenCodeType.Setter("profile")
    public static void setProfile(Item internal, ResolvableProfile profile) {
        
        setComponent(internal, DataComponents.PROFILE, profile);
    }
    
    @ZenCodeType.Setter("noteBlockSound")
    public static void setNoteBlockSound(Item internal, ResourceLocation sound) {
        
        setComponent(internal, DataComponents.NOTE_BLOCK_SOUND, sound);
    }
    
    @ZenCodeType.Setter("bannerPatterns")
    public static void setBannerPatterns(Item internal, BannerPatternLayers patterns) {
        
        setComponent(internal, DataComponents.BANNER_PATTERNS, patterns);
    }
    
    @ZenCodeType.Setter("baseColor")
    public static void setBaseColor(Item internal, DyeColor baseColor) {
        
        setComponent(internal, DataComponents.BASE_COLOR, baseColor);
    }
    
    @ZenCodeType.Setter("potDecorations")
    public static void setPotDecorations(Item internal, PotDecorations decorations) {
        
        setComponent(internal, DataComponents.POT_DECORATIONS, decorations);
    }
    
    @ZenCodeType.Setter("container")
    public static void setContainer(Item internal, ItemContainerContents container) {
        
        setComponent(internal, DataComponents.CONTAINER, container);
    }
    
    @ZenCodeType.Setter("blockState")
    public static void setBlockState(Item internal, BlockItemStateProperties blockState) {
        
        setComponent(internal, DataComponents.BLOCK_STATE, blockState);
    }
    
    @ZenCodeType.Setter("bees")
    public static void setBees(Item internal, List<BeehiveBlockEntity.Occupant> occupants) {
        
        setComponent(internal, DataComponents.BEES, occupants);
    }
    
    @ZenCodeType.Setter("lockCode")
    public static void setLockCode(Item internal, LockCode code) {
        
        setComponent(internal, DataComponents.LOCK, code);
    }
    
    @ZenCodeType.Setter("containerLoot")
    public static void setContainerLoot(Item internal, SeededContainerLoot containerLoot) {
        
        setComponent(internal, DataComponents.CONTAINER_LOOT, containerLoot);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultInstance")
    @ZenCodeType.Caster(implicit = true)
    public static IItemStack getDefaultInstance(Item internal) {
        
        return IItemStack.of(internal.getDefaultInstance());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(Item internal) {
        
        return BuiltInRegistries.ITEM.getKey(internal);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Item internal) {
        
        return "<item:" + BuiltInRegistries.ITEM.getKey(internal) + ">.definition";
    }
    
    
}