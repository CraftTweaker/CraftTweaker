package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.entity.CTEntityIngredient;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.factory.EntityTypeTagFactory;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@Document("vanilla/api/entity/EntityType")
@NativeTypeRegistration(value = EntityType.class, zenCodeName = "crafttweaker.api.entity.EntityType")
@TaggableElement(value = "minecraft:entity_type", managerFactoryClass = EntityTypeTagFactory.class)
public class ExpandEntityType {
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity spawn(EntityType internal, ServerLevel level, @ZenCodeType.Nullable MapData data, @ZenCodeType.Nullable Component displayName, @ZenCodeType.Nullable Player spawningPlayer, BlockPos pos, MobSpawnType spawnType, boolean alignPosition, boolean invertY) {
        
        return internal.spawn(level, data.getInternal(), displayName, spawningPlayer, pos, spawnType, alignPosition, invertY);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static Entity create(EntityType internal, ServerLevel param0, @ZenCodeType.Nullable MapData param1, @ZenCodeType.Nullable Component param2, @ZenCodeType.Nullable Player param3, BlockPos param4, MobSpawnType param5, boolean alignPosition, boolean invertY) {
        
        return internal.create(param0, param1.getInternal(), param2, param3, param4, param5, alignPosition, invertY);
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
        public static boolean isIn(EntityType internal, MCTag<EntityType<Entity>> tag) {

            return internal.is(tag.getTagKey());
        }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(EntityType internal) {
        
        return "<entitytype:" + Services.REGISTRY.getRegistryKey(internal) + ">";
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
