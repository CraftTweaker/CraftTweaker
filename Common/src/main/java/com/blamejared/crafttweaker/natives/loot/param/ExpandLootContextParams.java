package com.blamejared.crafttweaker.natives.loot.param;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;

/**
 * These are pre-registered params that are used by vanilla.
 *
 * <p>You can also create your own using {@code getOrCreate}</p>
 */
@ZenRegister
@Document("vanilla/api/loot/param/LootContextParams")
@NativeTypeRegistration(value = LootContextParams.class, zenCodeName = "crafttweaker.api.loot.param.LootContextParams")
public final class ExpandLootContextParams {
    
    private static final Map<ResourceLocation, LootContextParam<?>> PRE_REGISTERED_PARAMS = Util.make(new HashMap<>(), (map) -> {
        map.put(new ResourceLocation("this_entity"), LootContextParams.THIS_ENTITY);
        map.put(new ResourceLocation("last_damage_player"), LootContextParams.LAST_DAMAGE_PLAYER);
        map.put(new ResourceLocation("damage_source"), LootContextParams.DAMAGE_SOURCE);
        map.put(new ResourceLocation("killer_entity"), LootContextParams.KILLER_ENTITY);
        map.put(new ResourceLocation("direct_killer_entity"), LootContextParams.DIRECT_KILLER_ENTITY);
        map.put(new ResourceLocation("origin"), LootContextParams.ORIGIN);
        map.put(new ResourceLocation("block_state"), LootContextParams.BLOCK_STATE);
        map.put(new ResourceLocation("block_entity"), LootContextParams.BLOCK_ENTITY);
        map.put(new ResourceLocation("tool"), LootContextParams.TOOL);
        map.put(new ResourceLocation("explosion_radius"), LootContextParams.EXPLOSION_RADIUS);
    });
    
    /**
     * Gets the 'this_entity' parameter.
     *
     * @return The 'this_entity' parameter.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<Entity> thisEntity() {
        
        return LootContextParams.THIS_ENTITY;
    }
    
    /**
     * Gets the 'last_damage_player' parameter.
     *
     * @return The 'last_damage_player' parameter.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<Player> lastDamagePlayer() {
        
        return LootContextParams.LAST_DAMAGE_PLAYER;
    }
    
    /**
     * Gets the 'damage_source' param.
     *
     * @return The 'damage_source' param.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<DamageSource> damageSource() {
        
        return LootContextParams.DAMAGE_SOURCE;
    }
    
    /**
     * Gets the 'killer_entity' param.
     *
     * @return The 'killer_entity' param.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<Entity> killerEntity() {
        
        return LootContextParams.KILLER_ENTITY;
    }
    
    /**
     * Gets the 'direct_killer_entity' param.
     *
     * @return The 'direct_killer_entity' param.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<Entity> directKillerEntity() {
        
        return LootContextParams.DIRECT_KILLER_ENTITY;
    }
    
    /**
     * Gets the 'origin' param.
     *
     * @return The 'origin' param.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<Vec3> origin() {
        
        return LootContextParams.ORIGIN;
    }
    
    /**
     * Gets the 'block_state' param.
     *
     * @return The 'block_state' param.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<BlockState> blockState() {
        
        return LootContextParams.BLOCK_STATE;
    }
    
    /**
     * Gets the 'block_entity' param.
     *
     * @return The 'block_entity' param.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<BlockEntity> blockEntity() {
        
        return LootContextParams.BLOCK_ENTITY;
    }
    
    /**
     * Gets the 'tool' param.
     *
     * @return The 'tool' param.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<ItemStack> tool() {
        
        return LootContextParams.TOOL;
    }
    
    /**
     * Gets the 'explosion_radius' param.
     *
     * @return The 'explosion_radius' param.
     */
    @ZenCodeType.StaticExpansionMethod
    public static LootContextParam<Float> explosionRadius() {
        
        return LootContextParams.EXPLOSION_RADIUS;
    }
    
    /**
     * Gets or creates a new parameter with the given name, using the given type.
     *
     * @param name The name of the parameter.
     * @param <T>  The type that the parameter acts on.
     *
     * @return The parameter with the given name or a new parameter if there are no registered parameters with the name.
     *
     * @docParam name "height"
     */
    @ZenCodeType.StaticExpansionMethod
    public static <T> LootContextParam<T> getOrCreate(ResourceLocation name, Class<T> tClass) {
        
        return GenericUtil.uncheck(PRE_REGISTERED_PARAMS.computeIfAbsent(name, LootContextParam::new));
    }
    
}
