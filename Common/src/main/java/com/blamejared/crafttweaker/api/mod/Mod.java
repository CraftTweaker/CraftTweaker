package com.blamejared.crafttweaker.api.mod;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a mod in the game.
 *
 * <p>You can get a mod by using {@code loadedMods.botania} or {@code loadedMods["botania"]}</p>
 *
 * @docParam this loadedMods.botania
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.mod.Mod")
@Document("vanilla/api/mod/Mod")
public final class Mod {
    
    private final String id;
    private final String displayName;
    private final String version;
    
    public Mod(String id, String displayName, String version) {
        
        this.id = id;
        this.displayName = displayName;
        this.version = version;
    }
    
    
    private <T> Collection<T> getRegistryObjects(Registry<T> registry) {
        
        return registry.keySet()
                .stream()
                .filter(resourceLocation -> resourceLocation.getNamespace().equals(id()))
                .map(resourceLocation -> registry.getOptional(resourceLocation)
                        .orElseThrow(() -> new IllegalArgumentException("Cannot get registry object from name: '" + resourceLocation + "'! This should never happen!")))
                .toList();
    }
    
    /**
     * Gets the items that are registered under this mod's ID.
     *
     * @return A list of items that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("items")
    public Collection<Item> getItems() {
        
        return getRegistryObjects(Registry.ITEM);
    }
    
    
    /**
     * Gets the ItemStacks that are registered under this mod's ID.
     *
     * @return A list of ItemStacks that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemStacks")
    public Collection<IItemStack> getItemStacks() {
        
        return getItems()
                .stream()
                .map(Item::getDefaultInstance)
                .map(IItemStack::of)
                .toList();
    }
    
    /**
     * Gets the potions that are registered under this mod's ID.
     *
     * @return A list of potions that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("potions")
    public Collection<Potion> getPotions() {
        
        return getRegistryObjects(Registry.POTION);
    }
    
    /**
     * Gets the attributes that are registered under this mod's ID.
     *
     * @return A list of attributes that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("attributes")
    public Collection<Attribute> getAttributes() {
        
        return getRegistryObjects(Registry.ATTRIBUTE);
    }
    
    /**
     * Gets the fluids that are registered under this mod's ID.
     *
     * @return A list of fluids that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("fluids")
    public Collection<Fluid> getFluids() {
        
        return getRegistryObjects(Registry.FLUID);
    }
    
    /**
     * Gets the enchantments that are registered under this mod's ID.
     *
     * @return A list of enchantments that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantments")
    public Collection<Enchantment> getEnchantments() {
        
        return getRegistryObjects(Registry.ENCHANTMENT);
    }
    
    /**
     * Gets the blocks that are registered under this mod's ID.
     *
     * @return A list of blocks that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("blocks")
    public Collection<Block> getBlocks() {
        
        return getRegistryObjects(Registry.BLOCK);
    }
    
    /**
     * Gets the mob effects that are registered under this mod's ID.
     *
     * @return A list of mob effects that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("mobEffects")
    public Collection<MobEffect> getMobEffects() {
        
        return getRegistryObjects(Registry.MOB_EFFECT);
    }
    
    /**
     * Gets the villager professions that are registered under this mod's ID.
     *
     * @return A list of villager professions that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("villagerProfessions")
    public Collection<VillagerProfession> getVillagerProfessions() {
        
        return getRegistryObjects(Registry.VILLAGER_PROFESSION);
    }
    
    /**
     * Gets the sound events that are registered under this mod's ID.
     *
     * @return A list of sound events that were registered under this mod's ID.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("soundEvents")
    public Collection<SoundEvent> getSoundEvents() {
        
        return getRegistryObjects(Registry.SOUND_EVENT);
    }
    
    /**
     * Gets the id (namespace) of this mod.
     *
     * @return The id (namespace) of this mod.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public String id() {
        
        return id;
    }
    
    /**
     * Gets the display name of this mod.
     *
     * @return The display name of this mod.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("displayName")
    public String displayName() {
        
        return displayName;
    }
    
    /**
     * Gets the version of this mod.
     *
     * @return The version of this mod.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("version")
    public String version() {
        
        return version;
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if(obj == this) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Mod that = (Mod) obj;
        return Objects.equals(this.id, that.id) && Objects.equals(this.displayName, that.displayName) && Objects.equals(this.version, that.version);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(id, displayName, version);
    }
    
    @Override
    public String toString() {
        
        return "Mod[" + "id=" + id + ", " + "displayName=" + displayName + ", " + "version=" + version + ']';
    }
    
    
}
