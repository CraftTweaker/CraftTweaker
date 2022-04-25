package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * These are pre-registered param sets that are used by vanilla.
 */
@ZenRegister
@Document("vanilla/api/loot/param/LootContextParamSets")
@NativeTypeRegistration(value = LootContextParamSets.class, zenCodeName = "crafttweaker.api.loot.param.LootContextParamSets")
public final class ExpandLootContextParamSets {
    
    /**
     * Gets the 'empty' param set.
     *
     * @return The 'empty' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet empty() {
        
        return LootContextParamSets.EMPTY;
    }
    
    /**
     * Gets the 'chest' param set.
     *
     * The 'chest' param set requires the following params:
     * <ul>
     * <li>{@code origin}</li>
     * </ul>
     *
     * The 'chest' param set can optionally use:
     * <ul>
     * <li>{@code this_entity}</li>
     * </ul>
     *
     * @return The 'chest' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet chest() {
        
        return LootContextParamSets.CHEST;
    }
    
    /**
     * Gets the 'command' param set.
     *
     * The 'command' param set requires the following params:
     * <ul>
     * <li>{@code origin}</li>
     * </ul>
     *
     * The 'command' param set can optionally use:
     * <ul>
     * <li>{@code this_entity}</li>
     * </ul>
     *
     * @return The 'command' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet command() {
        
        return LootContextParamSets.COMMAND;
    }
    
    
    /**
     * Gets the 'selector' param set.
     *
     * The 'selector' param set requires the following params:
     * <ul>
     * <li>{@code origin}</li>
     * <li>{@code this_entity}</li>
     * </ul>
     *
     * @return The 'selector' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet selector() {
        
        return LootContextParamSets.SELECTOR;
    }
    
    /**
     * Gets the 'fishing' param set.
     *
     * The 'fishing' param set requires the following params:
     * <ul>
     * <li>{@code origin}</li>
     * <li>{@code tool}</li>
     * </ul>
     *
     * The 'fishing' param set can optionally use:
     * <ul>
     * <li>{@code this_entity}</li>
     * </ul>
     *
     * @return The 'fishing' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet fishing() {
        
        return LootContextParamSets.FISHING;
    }
    
    /**
     * Gets the 'entity' param set.
     *
     * The 'entity' param set requires the following params:
     * <ul>
     * <li>{@code this_entity}</li>
     * <li>{@code origin}</li>
     * <li>{@code damage_source}</li>
     * </ul>
     *
     * The 'entity' param set can optionally use:
     * <ul>
     * <li>{@code killer_entity}</li>
     * <li>{@code direct_killer_entity}</li>
     * <li>{@code last_damage_player}</li>
     * </ul>
     *
     * @return The 'entity' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet entity() {
        
        return LootContextParamSets.ENTITY;
    }
    
    /**
     * Gets the 'gift' param set.
     *
     * The 'gift' param set requires the following params:
     * <ul>
     * <li>{@code origin}</li>
     * <li>{@code this_entity}</li>
     * </ul>
     *
     * @return The 'gift' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet gift() {
        
        return LootContextParamSets.GIFT;
    }
    
    /**
     * Gets the 'barter' param set.
     *
     * The 'barter' param set requires the following params:
     * <ul>
     * <li>{@code this_entity}</li>
     * </ul>
     *
     * @return The 'barter' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet piglinBarter() {
        
        return LootContextParamSets.PIGLIN_BARTER;
    }
    
    /**
     * Gets the 'advancement_reward' param set.
     *
     * The 'advancement_reward' param set requires the following params:
     * <ul>
     * <li>{@code this_entity}</li>
     * <li>{@code origin}</li>
     * </ul>
     *
     * @return The 'advancement_reward' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet advancementReward() {
        
        return LootContextParamSets.ADVANCEMENT_REWARD;
    }
    
    /**
     * Gets the 'advancement_entity' param set.
     *
     * The 'advancement_entity' param set requires the following params:
     * <ul>
     * <li>{@code this_entity}</li>
     * <li>{@code origin}</li>
     * </ul>
     *
     * @return The 'advancement_entity' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet advancementEntity() {
        
        return LootContextParamSets.ADVANCEMENT_ENTITY;
    }
    
    /**
     * Gets the 'generic' param set.
     *
     * The 'generic' param set requires the following params:
     * <ul>
     * <li>{@code this_entity}</li>
     * <li>{@code last_damage_player}</li>
     * <li>{@code damage_source}</li>
     * <li>{@code killer_entity}</li>
     * <li>{@code direct_killer_entity}</li>
     * <li>{@code origin}</li>
     * <li>{@code block_state}</li>
     * <li>{@code block_entity}</li>
     * <li>{@code tool}</li>
     * <li>{@code explosion_radius}</li>
     * </ul>
     *
     * @return The 'generic' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet allParams() {
        
        return LootContextParamSets.ALL_PARAMS;
    }
    
    /**
     * Gets the 'block' param set.
     *
     * The 'block' param set requires the following params:
     * <ul>
     * <li>{@code block_state}</li>
     * <li>{@code origin}</li>
     * <li>{@code tool}</li>
     * </ul>
     *
     * The 'block' param set can optionally use:
     * <ul>
     * <li>{@code this_entity}</li>
     * <li>{@code block_entity}</li>
     * <li>{@code explosion_radius}</li>
     * </ul>
     *
     * @return The 'block' param set.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet block() {
        
        return LootContextParamSets.BLOCK;
    }
    
    /**
     * Gets a param set from its name.
     *
     * <p>Will throw an exception if no param set is registered for the given name.
     *
     * @param name The name of the param.
     *
     * @return The found param set or an exception if not registered.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParamSet get(ResourceLocation name) {
        
        return Optional.ofNullable(LootContextParamSets.get(name))
                .orElseThrow(() -> new IllegalArgumentException("No loot context param set registered under the name: " + name));
    }
    
}
