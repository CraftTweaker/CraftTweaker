package com.blamejared.crafttweaker.natives.block;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.block.ActionSetBlockProperty;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.block.CTBlockIngredient;
import com.blamejared.crafttweaker.mixin.common.access.block.AccessBlockStateBase;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @docParam this <blockstate:minecraft:grass>
 */
@ZenRegister
@Document("vanilla/api/block/BlockState")
@NativeTypeRegistration(value = BlockState.class, zenCodeName = "crafttweaker.api.block.BlockState")
public class ExpandBlockState {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("soundType")
    public static SoundType getSoundType(BlockState internal) {
        
        return internal.getSoundType();
    }
    
    @ZenCodeType.Method
    public static BlockState rotate(BlockState internal, Rotation rotation) {
        
        return internal.rotate(rotation);
    }
    
    /**
     * Gets the base {@link Block} of this BlockState.
     *
     * The {@link Block} will not contain any of the properties of this BlockState.
     *
     * @return The base {@link Block}
     */
    @ZenCodeType.Getter("block")
    @ZenCodeType.Caster(implicit = true)
    public static Block getBlock(BlockState internal) {
        
        return internal.getBlock();
    }
    
    /**
     * Gets the light level of this BlockState
     *
     * @return The light level of this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("lightEmission")
    public static int getLightEmission(BlockState internal) {
        
        return internal.getLightEmission();
    }
    
    /**
     * Checks whether this BlockState can provide Redstone Power
     *
     * @return True if this BlockState can provide Redstone Power. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSignalSource")
    public static boolean isSignalSource(BlockState internal) {
        
        return internal.isSignalSource();
    }
    
    /**
     * Checks whether this BlockState is solid.
     *
     * @return True if this BlockState is solid. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canOcclude")
    public static boolean canOcclude(BlockState internal) {
        
        return internal.canOcclude();
    }
    
    /**
     * Checks whether this BlockState ticks randomly.
     *
     * @return True if this BlockState ticks randomly. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isRandomlyTicking")
    public static boolean isRandomlyTicking(BlockState internal) {
        
        return internal.isRandomlyTicking();
    }
    
    /**
     * Checks whether this BlockState has a {@link net.minecraft.world.level.block.entity.BlockEntity}.
     *
     * @return True if this BlockState has a {@link net.minecraft.world.level.block.entity.BlockEntity}. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasBlockEntity")
    public static boolean hasBlockEntity(BlockState internal) {
        
        return internal.hasBlockEntity();
    }
    
    /**
     * Sets a block property based on it's name.
     *
     * @param name  The name of the property to set.
     * @param value The new value of the property.
     *
     * @return This BlockState with the new property value.
     *
     * @docParam name "snowy"
     * @docParam value "true"
     */
    @ZenCodeType.Method
    @SuppressWarnings({"rawtypes"})
    public static BlockState withProperty(BlockState internal, String name, String value) {
        
        Property property = internal.getBlock().getStateDefinition().getProperty(name);
        if(property == null) {
            CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME).warn("Invalid property name");
        } else {
            Optional<Comparable> propValue = property.getValue(value);
            if(propValue.isPresent()) {
                return internal.setValue(property, propValue.get());
            }
            CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME).warn("Invalid property value");
        }
        return internal;
    }
    
    /**
     * Gets the names of the properties of this BlockState.
     *
     * @return the List of the names of the BlockStates's properties.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("propertyNames")
    public static List<String> getPropertyNames(BlockState internal) {
        
        List<String> props = new ArrayList<>();
        for(Property<?> prop : internal.getProperties()) {
            props.add(prop.getName());
        }
        return ImmutableList.copyOf(props);
    }
    
    /**
     * Gets the value of the given property.
     *
     * @param name "snowy"
     *
     * @return The value of the property on this BlockState.
     */
    @ZenCodeType.Method
    public static String getPropertyValue(BlockState internal, String name) {
        
        Property<?> prop = internal.getBlock().getStateDefinition().getProperty(name);
        if(prop != null) {
            return internal.getValue(prop).toString();
        }
        CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME).warn("Invalid property name");
        return "";
    }
    
    /**
     * Gets a list of allowed values for a given property.
     *
     * @param name The name of the property to find the allowed values for.
     *
     * @return a List of allowed values.
     *
     * @docParam name "snowy"
     */
    @ZenCodeType.Method
    public static List<String> getAllowedValuesForProperty(BlockState internal, String name) {
        
        Property<?> prop = internal.getBlock().getStateDefinition().getProperty(name);
        if(prop != null) {
            List<String> values = new ArrayList<>();
            prop.getPossibleValues().forEach(v -> values.add(v.toString()));
            return values;
        }
        CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME).warn("Invalid property name");
        return ImmutableList.of();
    }
    
    /**
     * Gets the properties of this BlockState.
     *
     * @return a Map of the properties on this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("properties")
    public static Map<String, String> getProperties(BlockState internal) {
        
        Map<String, String> props = new HashMap<>();
        for(Property<?> key : internal.getProperties()) {
            props.put(key.getName(), internal.getValue(key).toString());
        }
        return ImmutableMap.copyOf(props);
    }
    
    /**
     * Checks whether this BlockState has the given property.
     *
     * @param name the name of the property to check.
     *
     * @return True if this BlockState has the property. False otherwise.
     *
     * @docParam name "snowy"
     */
    @ZenCodeType.Method
    public static boolean hasProperty(BlockState internal, String name) {
        
        Property<?> prop = internal.getBlock().getStateDefinition().getProperty(name);
        return prop != null;
    }
    
    @ZenCodeType.Method
    public static String asString(BlockState internal) {
        
        return internal.toString();
    }
    
    /**
     * Gets the hardness of this BlockState.
     *
     * @return The hardness of this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("destroySpeed")
    public static float getDestroySpeed(BlockState internal) {
        
        return ((AccessBlockStateBase) internal).crafttweaker$getDestroySpeed();
    }
    
    /**
     * Sets the destroy speed of this BlockState.
     *
     * @param destroySpeed the new destroy speed of this BlockState
     *
     * @docParam destroySpeed 2.4f
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("destroySpeed")
    public static void setDestroySpeed(BlockState internal, float destroySpeed) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Destroy Speed", destroySpeed, ((AccessBlockStateBase) internal).crafttweaker$getDestroySpeed(), value -> ((AccessBlockStateBase) internal).crafttweaker$setDestroySpeed(value)));
    }
    
    /**
     * Gets the blockstate bracket handler syntax for this BlockState.
     *
     * E.G.
     * {@code <blockstate:minecraft:grass:snowy=true>}
     *
     * @return The blockstate bracket handler syntax for this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    @ZenCodeType.Caster
    public static String getCommandString(BlockState internal) {
        
        StringBuilder builder = new StringBuilder("<blockstate:");
        builder.append(ExpandBlock.getRegistryName(getBlock(internal)));
        if(!getProperties(internal).isEmpty()) {
            builder.append(":");
            builder.append(getProperties(internal).entrySet()
                    .stream()
                    .map(kv -> kv.getKey() + "=" + kv.getValue())
                    .collect(Collectors.joining(",")));
        }
        builder.append(">");
        return builder.toString();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static CTBlockIngredient asBlockIngredient(BlockState internal) {
        
        return new CTBlockIngredient.BlockStateIngredient(internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTBlockIngredient asList(BlockState internal, CTBlockIngredient other) {
        
        List<CTBlockIngredient> elements = new ArrayList<>();
        elements.add(asBlockIngredient(internal));
        elements.add(other);
        return new CTBlockIngredient.CompoundBlockIngredient(elements);
    }
    
}
