package com.blamejared.crafttweaker.impl_native.blocks;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.Collectors;

@ZenRegister
@NativeTypeRegistration(value = BlockState.class, zenCodeName = "crafttweaker.api.blocks.MCBlockState")
public class ExpandBlockState {
    
    @ZenCodeType.Getter("block")
    @ZenCodeType.Caster(implicit = true)
    public static Block getBlock(BlockState internal) {
        return internal.getBlock();
    }
    
    
    @ZenCodeType.Getter("lightLevel")
    public static int getLightLevel(BlockState internal) {
        return internal.getLightValue();
    }
    
    @ZenCodeType.Getter("canProvidePower")
    public static boolean canProvidePower(BlockState internal) {
        return internal.canProvidePower();
    }
    
    @ZenCodeType.Getter("isSolid")
    public static boolean isSolid(BlockState internal) {
        return internal.isSolid();
    }
    
    @ZenCodeType.Getter("ticksRandomly")
    public static boolean ticksRandomly(BlockState internal) {
        return internal.ticksRandomly();
    }
    
    @ZenCodeType.Getter("hasTileEntity")
    public static boolean hasTileEntity(BlockState internal) {
        return internal.hasTileEntity();
    }
    
    @ZenCodeType.Getter("isSticky")
    public static boolean isSticky(BlockState internal) {
        return internal.isStickyBlock();
    }
    
    
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
    
    @ZenCodeType.Method
    public static List<String> getPropertyNames(BlockState internal) {
        List<String> props = new ArrayList<>();
        for(Property<?> prop : internal.getProperties()) {
            props.add(prop.getName());
        }
        return ImmutableList.copyOf(props);
    }
    
    @ZenCodeType.Method
    public static String getPropertyValue(BlockState internal, String name) {
        Property<?> prop = internal.getBlock().getStateContainer().getProperty(name);
        if(prop != null) {
            return internal.get(prop).toString();
        }
        CraftTweakerAPI.logWarning("Invalid property name");
        return "";
    }
    
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
    
    @ZenCodeType.Method
    public static Map<String, String> getProperties(BlockState internal) {
        Map<String, String> props = new HashMap<>();
        for(Property<?> key : internal.getProperties()) {
            props.put(key.getName(), internal.get(key).toString());
        }
        return ImmutableMap.copyOf(props);
    }
    
    @ZenCodeType.Method
    public static boolean hasProperty(BlockState internal, String name) {
        Property<?> prop = internal.getBlock().getStateContainer().getProperty(name);
        return prop != null;
    }
    
    
    @ZenCodeType.Caster
    public static String asString(BlockState internal) {
        return internal.toString();
    }
    
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
