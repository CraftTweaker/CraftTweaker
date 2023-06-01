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
    public static Entity spawn(EntityType internal, ServerLevel level, @ZenCodeType.Nullable IItemStack spawnStack, @ZenCodeType.Nullable Player spawningPlayer, BlockPos position, MobSpawnType spawnType, boolean alignPosition, boolean invertY) {
        
        return internal.spawn(level, spawnStack == null ? null : spawnStack.getInternal(), spawningPlayer, position, spawnType, alignPosition, invertY);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity spawn(EntityType internal, ServerLevel level, BlockPos position, MobSpawnType spawnType) {
        
        return internal.spawn(level, position, spawnType);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity spawn(EntityType internal, ServerLevel level, @ZenCodeType.Nullable MapData data, @ZenCodeType.Nullable Consumer<Entity> onSpawn, BlockPos position, MobSpawnType spawnType, boolean alignPosition, boolean invertY) {
        
        return internal.spawn(level, data == null ? null : data.getInternal(), onSpawn, position, spawnType, alignPosition, invertY);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canSummon")
    public static boolean canSummon(EntityType internal) {
        
        return internal.canSummon();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fireImmune")
    public static boolean fireImmune(EntityType internal) {
        
        return internal.fireImmune();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canSpawnFarFromPlayer")
    public static boolean canSpawnFarFromPlayer(EntityType internal) {
        
        return internal.canSpawnFarFromPlayer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("category")
    public static MobCategory getCategory(EntityType internal) {
        
        return internal.getCategory();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("descriptionId")
    public static String getDescriptionId(EntityType internal) {
        
        return internal.getDescriptionId();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("description")
    public static Component getDescription(EntityType internal) {
        
        return internal.getDescription();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("toShortString")
    public static String toShortString(EntityType internal) {
        
        return internal.toShortString();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultLootTable")
    public static ResourceLocation getDefaultLootTable(EntityType internal) {
        
        return internal.getDefaultLootTable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("width")
    public static float getWidth(EntityType internal) {
        
        return internal.getWidth();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("height")
    public static float getHeight(EntityType internal) {
        
        return internal.getHeight();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity create(EntityType internal, Level level) {
        
        return internal.create(level);
    }
    
    @ZenCodeType.Method
    public static boolean isBlockDangerous(EntityType internal, BlockState state) {
        
        return internal.isBlockDangerous(state);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("dimensions")
    public static EntityDimensions getDimensions(EntityType internal) {
        
        return internal.getDimensions();
    }
    
    @ZenCodeType.Method
    public static boolean isIn(EntityType internal, KnownTag<EntityType<Entity>> tag) {
        
        return internal.is(tag.getTagKey());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(EntityType internal) {
        
        return BuiltInRegistries.ENTITY_TYPE.getKey(internal);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EntityType internal) {
        
        return "<entitytype:" + BuiltInRegistries.ENTITY_TYPE.getKey(internal) + ">";
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static CTEntityIngredient asEntityIngredient(EntityType internal) {
        
        return new CTEntityIngredient.EntityTypeIngredient(internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTEntityIngredient asList(EntityType internal, CTEntityIngredient other) {
        
        List<CTEntityIngredient> elements = new ArrayList<>();
        elements.add(asEntityIngredient(internal));
        elements.add(other);
        return new CTEntityIngredient.CompoundEntityIngredient(elements);
    }
    
}
