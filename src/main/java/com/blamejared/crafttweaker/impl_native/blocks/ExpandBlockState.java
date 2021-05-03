package com.blamejared.crafttweaker.impl_native.blocks;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/blocks/MCBlockState")
@NativeTypeRegistration(value = BlockState.class, zenCodeName = "crafttweaker.api.blocks.MCBlockState")
public class ExpandBlockState {
    
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
    @ZenCodeType.Getter("lightLevel")
    public static int getLightLevel(BlockState internal) {
        
        return internal.getLightValue();
    }
    
    /**
     * Checks whether this BlockState can provide Redstone Power
     *
     * @return True if this BlockState can provide Redstone Power. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canProvidePower")
    public static boolean canProvidePower(BlockState internal) {
        
        return internal.canProvidePower();
    }
    
    /**
     * Checks whether this BlockState is solid.
     *
     * @return True if this BlockState is solid. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSolid")
    public static boolean isSolid(BlockState internal) {
        
        return internal.isSolid();
    }
    
    /**
     * Checks whether this BlockState ticks randomly.
     *
     * @return True if this BlockState ticks randomly. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("ticksRandomly")
    public static boolean ticksRandomly(BlockState internal) {
        
        return internal.ticksRandomly();
    }
    
    /**
     * Checks whether this BlockState has a {@link net.minecraft.tileentity.TileEntity}.
     *
     * @return True if this BlockState has a {@link net.minecraft.tileentity.TileEntity}. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasTileEntity")
    public static boolean hasTileEntity(BlockState internal) {
        
        return internal.hasTileEntity();
    }
    
    /**
     * Checks if this BlockState is sticky.
     *
     * This is used to determine if the block should pull or push adjacent blocks when pushed / pulled by a piston.
     * For example, Slime Blocks are sticky blocks.
     *
     * @return True if this BlockState is Sticky. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSticky")
    public static boolean isSticky(BlockState internal) {
        
        return internal.isStickyBlock();
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
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static BlockState withProperty(BlockState internal, String name, String value) {
        
        Property property = internal.getBlock().getStateContainer().getProperty(name);
        if(property == null) {
            CraftTweakerAPI.logWarning("Invalid property name");
        } else {
            Optional<Comparable> propValue = property.parseValue(value);
            if(propValue.isPresent()) {
                return internal.with(property, propValue.get());
            }
            CraftTweakerAPI.logWarning("Invalid property value");
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
        
        Property<?> prop = internal.getBlock().getStateContainer().getProperty(name);
        if(prop != null) {
            return internal.get(prop).toString();
        }
        CraftTweakerAPI.logWarning("Invalid property name");
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
        
        Property<?> prop = internal.getBlock().getStateContainer().getProperty(name);
        if(prop != null) {
            List<String> values = new ArrayList<>();
            prop.getAllowedValues().forEach(v -> values.add(v.toString()));
            return values;
        }
        CraftTweakerAPI.logWarning("Invalid property name");
        return ImmutableList.of();
    }
    
    /**
     * Gets the properties of this BlockState.
     *
     * @return a Map of the properties on this BlockState.
     */
    @ZenCodeType.Method
    public static Map<String, String> getProperties(BlockState internal) {
        
        Map<String, String> props = new HashMap<>();
        for(Property<?> key : internal.getProperties()) {
            props.put(key.getName(), internal.get(key).toString());
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
        
        Property<?> prop = internal.getBlock().getStateContainer().getProperty(name);
        return prop != null;
    }
    
    /**
     * Gets the {@link ToolType} of this BlockState.
     *
     * @return The {@link ToolType} of this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("harvestTool")
    public static ToolType getHarvestTool(BlockState internal) {
        
        return internal.getHarvestTool();
    }
    
    /**
     * Gets the harvest level of this BlockState.
     *
     * @return The harvest level of this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("harvestLevel")
    public static int getHarvestLevel(BlockState internal) {
        
        return internal.getHarvestLevel();
    }
    
    @ZenCodeType.Caster
    public static String asString(BlockState internal) {
        
        return internal.toString();
    }
    
    /**
     * Gets the blockstate bracket handler syntax for this BlockState.
     *
     * E.G.
     * <code>
     * <blockstate:minecraft:grass:snowy=true>
     * </code>
     *
     * @return The blockstate bracket handler syntax for this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(BlockState internal) {
        
        StringBuilder builder = new StringBuilder("<blockstate:");
        builder.append(getBlock(internal).getRegistryName());
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
    
}
