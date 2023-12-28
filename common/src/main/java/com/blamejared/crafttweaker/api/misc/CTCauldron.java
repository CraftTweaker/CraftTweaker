package com.blamejared.crafttweaker.api.misc;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.misc.ActionSetCauldronInteraction;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.block.AccessAbstractCauldronBlock;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;

/**
 * Lets you add new Cauldron interactions.
 *
 * @docParam this cauldron
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.misc.Cauldron")
@Document("vanilla/api/misc/Cauldron")
public final class CTCauldron {
    
    @ZenCodeGlobals.Global("cauldron")
    public static final CTCauldron INSTANCE = new CTCauldron();
    
    private CTCauldron() {
    
    }
    
    /**
     * Adds an interaction that will fire when an empty Cauldron is interacted with the given item.
     *
     * @param item        The item to interact with.
     * @param interaction What happens when the item interacts with the cauldron.
     *
     * @docParam item <item:minecraft:dirt>
     * @docParam interaction (blockState, level, pos, player, hand, stack) => {
     *
     * if !level.isClientSide {
     * player.give(<item:minecraft:diamond>);
     * }
     * return crafttweaker.api.world.InteractionResult.sidedSuccess(level.isClientSide);
     * }
     */
    @ZenCodeType.Method
    public void addEmptyInteraction(Item item, CTCauldronInteraction interaction) {
        
        this.addInteraction(CauldronInteraction.EMPTY, item, interaction);
    }
    
    /**
     * Removes the interaction for the given item from an empty Cauldron.
     *
     * @param item The item to remove the interaction for.
     */
    @ZenCodeType.Method
    public void removeEmptyInteraction(Item item) {
        
        this.removeInteraction(CauldronInteraction.EMPTY, item);
    }
    
    /**
     * Adds an interaction that will fire when a Cauldron with water inside is interacted with the given item.
     *
     * @param item        The item to interact with.
     * @param interaction What happens when the item interacts with the cauldron.
     *
     * @docParam item <item:minecraft:dirt>
     * @docParam interaction (blockState, level, pos, player, hand, stack) => {
     *
     * if !level.isClientSide {
     * player.give(<item:minecraft:diamond>);
     * }
     * return crafttweaker.api.world.InteractionResult.sidedSuccess(level.isClientSide);
     * }
     */
    @ZenCodeType.Method
    public void addWaterInteraction(Item item, CTCauldronInteraction interaction) {
        
        this.addInteraction(CauldronInteraction.WATER, item, interaction);
    }
    
    /**
     * Removes the interaction for the given item from a water filled Cauldron.
     *
     * @param item The item to remove the interaction for.
     */
    @ZenCodeType.Method
    public void removeWaterInteraction(Item item) {
        
        this.removeInteraction(CauldronInteraction.WATER, item);
    }
    
    /**
     * Adds an interaction that will fire when a Cauldron with lava inside is interacted with the given item.
     *
     * @param item        The item to interact with.
     * @param interaction What happens when the item interacts with the cauldron.
     *
     * @docParam item <item:minecraft:dirt>
     * @docParam interaction (blockState, level, pos, player, hand, stack) => {
     *
     * if !level.isClientSide {
     * player.give(<item:minecraft:diamond>);
     * }
     * return crafttweaker.api.world.InteractionResult.sidedSuccess(level.isClientSide);
     * }
     */
    @ZenCodeType.Method
    public void addLavaInteraction(Item item, CTCauldronInteraction interaction) {
        
        this.addInteraction(CauldronInteraction.LAVA, item, interaction);
    }
    
    /**
     * Removes the interaction for the given item from a lava filled Cauldron.
     *
     * @param item The item to remove the interaction for.
     */
    @ZenCodeType.Method
    public void removeLavaInteraction(Item item) {
        
        this.removeInteraction(CauldronInteraction.LAVA, item);
    }
    
    /**
     * Adds an interaction that will fire when a Cauldron with powdered snow inside is interacted with the given item.
     *
     * @param item        The item to interact with.
     * @param interaction What happens when the item interacts with the cauldron.
     *
     * @docParam item <item:minecraft:dirt>
     * @docParam interaction (blockState, level, pos, player, hand, stack) => {
     *
     * if !level.isClientSide {
     * player.give(<item:minecraft:diamond>);
     * }
     * return crafttweaker.api.world.InteractionResult.sidedSuccess(level.isClientSide);
     * }
     */
    @ZenCodeType.Method
    public void addPowderSnowInteraction(Item item, CTCauldronInteraction interaction) {
        
        this.addInteraction(CauldronInteraction.POWDER_SNOW, item, interaction);
    }
    
    /**
     * Removes the interaction for the given item from a powdered snow filled Cauldron.
     *
     * @param item The item to remove the interaction for.
     */
    @ZenCodeType.Method
    public void removePowderSnowInteraction(Item item) {
        
        this.removeInteraction(CauldronInteraction.POWDER_SNOW, item);
    }
    
    /**
     * Gets an interaction that fills a cauldron with water.
     *
     * @return An interaction that fills a cauldron with water.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("fillWaterInteraction")
    public CTCauldronInteraction getFillWaterInteraction() {
        
        return CauldronInteraction.FILL_WATER::interact;
    }
    
    /**
     * Gets an interaction that fills a Cauldron with lava.
     *
     * @return An interaction that fills a Cauldron with lava.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("fillLavaInteraction")
    public CTCauldronInteraction getFillLavaInteraction() {
        
        return CauldronInteraction.FILL_LAVA::interact;
    }
    
    /**
     * Gets an interaction that fills a Cauldron with lava.
     *
     * @return An interaction that fills a Cauldron with lava.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("fillPowderSnowInteraction")
    public CTCauldronInteraction getFillPowderSnowInteraction() {
        
        return CauldronInteraction.FILL_POWDER_SNOW::interact;
    }
    
    /**
     * Adds an interaction that will fire when the provided cauldron block is interacted with the given item.
     *
     * <p>This method is mainly provided to add support for non-vanilla cauldrons, thus the provided block needs to be a {@link AbstractCauldronBlock}.</p>
     *
     * @param cauldronBlock The cauldron block to add an interaction to.
     * @param item          The item to interact with.
     * @param interaction   What happens when the item interacts with the cauldron.
     *
     * @docParam cauldronBlock <block:minecraft:lava_cauldron>
     * @docParam item <item:minecraft:dirt>
     * @docParam interaction (blockState, level, pos, player, hand, stack) => {
     *
     * if !level.isClientSide {
     * player.give(<item:minecraft:diamond>);
     * }
     * return crafttweaker.api.world.InteractionResult.sidedSuccess(level.isClientSide);
     * }
     */
    @ZenCodeType.Method
    public void addInteraction(Block cauldronBlock, Item item, CTCauldronInteraction interaction) {
        
        if(!(cauldronBlock instanceof AbstractCauldronBlock cauldron)) {
            throw new IllegalStateException("Provided block: '" + cauldronBlock + "' is not an AbstractCauldronBlock!");
        }
        addInteraction(((AccessAbstractCauldronBlock) cauldron).crafttweaker$getInteractions(), item, interaction);
    }
    
    /**
     * Removes the interaction that will fire when the provided cauldron block is interacted with the given item.
     *
     * <p>This method is mainly provided to add support for non-vanilla cauldrons, thus the provided block needs to be a {@link AbstractCauldronBlock}.</p>
     *
     * @param cauldronBlock The cauldron block to add an interaction to.
     * @param item          The item to interact with.
     *
     * @docParam cauldronBlock <block:minecraft:lava_cauldron>
     * @docParam item <item:minecraft:dirt>
     */
    @ZenCodeType.Method
    public void removeInteraction(Block cauldronBlock, Item item) {
        
        if(!(cauldronBlock instanceof AbstractCauldronBlock cauldron)) {
            throw new IllegalStateException("Provided block: '" + cauldronBlock + "' is not an AbstractCauldronBlock!");
        }
        removeInteraction(((AccessAbstractCauldronBlock) cauldron).crafttweaker$getInteractions(), item);
    }
    
    public void addInteraction(Map<Item, CauldronInteraction> map, Item item, CTCauldronInteraction interaction) {
        
        CraftTweakerAPI.apply(new ActionSetCauldronInteraction(map, item, interaction::interact));
    }
    
    public void removeInteraction(Map<Item, CauldronInteraction> map, Item item) {
        
        CraftTweakerAPI.apply(new ActionSetCauldronInteraction(map, item, null));
    }
    
}
