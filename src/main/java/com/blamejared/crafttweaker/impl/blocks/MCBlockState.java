package com.blamejared.crafttweaker.impl.blocks;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.MCBlockState")
@Document(value = "vanilla/blocks/MCBlockState")
@ZenWrapper(wrappedClass = "net.minecraft.block.BlockState", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.getCommandString()")
public class MCBlockState implements CommandStringDisplayable {
    
    private final BlockState internal;
    
    private final MCBlock internalBlock;
    
    public MCBlockState(BlockState internal) {
        this.internal = internal;
        this.internalBlock = new MCBlock(internal.getBlock());
    }
    
    @ZenCodeType.Getter("block")
    public MCBlock getBlock() {
        return getInternalBlock();
    }
    
    
    @ZenCodeType.Getter("lightLevel")
    public int getLightLevel() {
        return getInternal().getLightValue();
    }
    
    @ZenCodeType.Getter("canProvidePower")
    public boolean canProvidePower() {
        return getInternal().canProvidePower();
    }
    
    @ZenCodeType.Getter("isSolid")
    public boolean isSolid() {
        return getInternal().isSolid();
    }
    
    @ZenCodeType.Getter("ticksRandomly")
    public boolean ticksRandomly() {
        return getInternal().ticksRandomly();
    }
    
    @ZenCodeType.Getter("hasTileEntity")
    public boolean hasTileEntity() {
        return getInternal().hasTileEntity();
    }
    
    @ZenCodeType.Getter("isSticky")
    public boolean isSticky() {
        return getInternal().isStickyBlock();
    }
    
    
    @ZenCodeType.Method
    public MCBlockState withProperty(String name, String value) {
        IProperty property = getInternal().getBlock().getStateContainer().getProperty(name);
        if(property == null) {
            CraftTweakerAPI.logWarning("Invalid property name");
        } else {
            Optional<Comparable> propValue = property.parseValue(value);
            if(propValue.isPresent()) {
                return new MCBlockState(getInternal().with(property, propValue.get()));
            }
            CraftTweakerAPI.logWarning("Invalid property value");
        }
        return this;
    }
    
    @ZenCodeType.Method
    public List<String> getPropertyNames() {
        List<String> props = new ArrayList<>();
        for(IProperty prop : getInternal().getProperties()) {
            props.add(prop.getName());
        }
        return ImmutableList.copyOf(props);
    }
    
    @ZenCodeType.Method
    public String getPropertyValue(String name) {
        IProperty prop = getInternal().getBlock().getStateContainer().getProperty(name);
        if(prop != null) {
            return getInternal().get(prop).toString();
        }
        CraftTweakerAPI.logWarning("Invalid property name");
        return "";
    }
    
    @ZenCodeType.Method
    public List<String> getAllowedValuesForProperty(String name) {
        IProperty prop = getInternal().getBlock().getStateContainer().getProperty(name);
        if(prop != null) {
            List<String> values = new ArrayList<>();
            prop.getAllowedValues().forEach(v -> values.add(v.toString()));
            return values;
        }
        CraftTweakerAPI.logWarning("Invalid property name");
        return ImmutableList.of();
    }
    
    @ZenCodeType.Method
    public Map<String, String> getProperties() {
        Map<String, String> props = new HashMap<>();
        for(IProperty<?> key : getInternal().getProperties()) {
            props.put(key.getName(), getInternal().get(key).toString());
        }
        return ImmutableMap.copyOf(props);
    }
    
    @ZenCodeType.Method
    public boolean hasProperty(String name) {
        IProperty prop = getInternal().getBlock().getStateContainer().getProperty(name);
        return prop != null;
    }
    
    
    @ZenCodeType.Caster(implicit = false)
    public String asString() {
        return internal.toString();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public MCBlock asBlock() {
        return this.getInternalBlock();
    }
    
    @ZenCodeType.Getter("commandString")
    @Override
    public String getCommandString() {
        StringBuilder builder = new StringBuilder("<blockstate:");
        builder.append(getBlock().getInternal().getRegistryName().toString());
        if(!getProperties().isEmpty()) {
            builder.append(":");
            builder.append(getProperties().entrySet().stream().map(kv -> kv.getKey() + "=" + kv.getValue()).collect(Collectors.joining(",")));
        }
        builder.append(">");
        return builder.toString();
    }
    
    public BlockState getInternal() {
        return internal;
    }
    
    public MCBlock getInternalBlock() {
        return internalBlock;
    }
}
