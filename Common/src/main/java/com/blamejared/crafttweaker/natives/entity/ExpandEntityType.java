package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.entity.CTEntityIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.function.Consumer;

@ZenRegister
@Document("vanilla/api/entity/EntityType")
@NativeTypeRegistration(value = EntityType.class, zenCodeName = "crafttweaker.api.entity.EntityType")
@TaggableElement(value = "minecraft:entity_type")
public class ExpandEntityType {
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity spawn(EntityType<Entity> internal, ServerLevel level, @ZenCodeType.Nullable IItemStack spawnStack, @ZenCodeType.Nullable Player spawningPlayer, BlockPos position, MobSpawnType spawnType, boolean alignPosition, boolean invertY) {
        
        return internal.spawn(level, spawnStack == null ? null : spawnStack.getInternal(), spawningPlayer, position, spawnType, alignPosition, invertY);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity spawn(EntityType<Entity> internal, ServerLevel level, BlockPos position, MobSpawnType spawnType) {
        
        return internal.spawn(level, position, spawnType);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity spawn(EntityType<Entity> internal, ServerLevel level, @ZenCodeType.Nullable MapData data, @ZenCodeType.Nullable Consumer<Entity> onSpawn, BlockPos position, MobSpawnType spawnType, boolean alignPosition, boolean invertY) {
        
        return internal.spawn(level, data == null ? null : data.getInternal(), onSpawn, position, spawnType, alignPosition, invertY);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canSummon")
    public static boolean canSummon(EntityType<Entity> internal) {
        
        return internal.canSummon();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fireImmune")
    public static boolean fireImmune(EntityType<Entity> internal) {
        
        return internal.fireImmune();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canSpawnFarFromPlayer")
    public static boolean canSpawnFarFromPlayer(EntityType<Entity> internal) {
        
        return internal.canSpawnFarFromPlayer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("category")
    public static MobCategory getCategory(EntityType<Entity> internal) {
        
        return internal.getCategory();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("descriptionId")
    public static String getDescriptionId(EntityType<Entity> internal) {
        
        return internal.getDescriptionId();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("description")
    public static Component getDescription(EntityType<Entity> internal) {
        
        return internal.getDescription();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("toShortString")
    public static String toShortString(EntityType<Entity> internal) {
        
        return internal.toShortString();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultLootTable")
    public static ResourceLocation getDefaultLootTable(EntityType<Entity> internal) {
        
        return internal.getDefaultLootTable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("width")
    public static float getWidth(EntityType<Entity> internal) {
        
        return internal.getWidth();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("height")
    public static float getHeight(EntityType<Entity> internal) {
        
        return internal.getHeight();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity create(EntityType<Entity> internal, Level level) {
        
        return internal.create(level);
    }
    
    @ZenCodeType.Method
    public static boolean isBlockDangerous(EntityType<Entity> internal, BlockState state) {
        
        return internal.isBlockDangerous(state);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("dimensions")
    public static EntityDimensions getDimensions(EntityType<Entity> internal) {
        
        return internal.getDimensions();
    }
    
    @ZenCodeType.Method
    public static boolean isIn(EntityType<Entity> internal, KnownTag<EntityType<Entity>> tag) {
        
        return internal.is(tag.getTagKey());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(EntityType<Entity> internal) {
        
        return BuiltInRegistries.ENTITY_TYPE.getKey(internal);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EntityType<Entity> internal) {
        
        return "<entitytype:" + BuiltInRegistries.ENTITY_TYPE.getKey(internal) + ">";
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static CTEntityIngredient asEntityIngredient(EntityType<Entity> internal) {
        
        return new CTEntityIngredient.EntityTypeIngredient(internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTEntityIngredient asList(EntityType<Entity> internal, CTEntityIngredient other) {
        
        List<CTEntityIngredient> elements = new ArrayList<>();
        elements.add(asEntityIngredient(internal));
        elements.add(other);
        return new CTEntityIngredient.CompoundEntityIngredient(elements);
    }
    
}
