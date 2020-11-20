package com.blamejared.crafttweaker.impl.item;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

@ZenRegister
@Document("vanilla/api/item/MCItemDefinition")
@ZenCodeType.Name("crafttweaker.api.item.MCItemDefinition")
@ZenWrapper(wrappedClass = "net.minecraft.item.Item", displayStringFormat = "%s.getCommandString()")
public class MCItemDefinition implements CommandStringDisplayable {
    
    private final Item internal;
    
    public MCItemDefinition(Item internal) {
        this.internal = internal;
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultInstance")
    @ZenCodeType.Caster(implicit = true)
    public IItemStack getDefaultInstance() {
        return new MCItemStack(internal.getDefaultInstance());
    }
    
    
    @Override
    public String getCommandString() {
        return "<item:" + internal.getRegistryName() + ">.definition";
    }
    
    public Item getInternal() {
        return internal;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        MCItemDefinition that = (MCItemDefinition) o;
    
        return internal.equals(that.internal);
    }
    
    @Override
    public int hashCode() {
        return internal.hashCode();
    }
}
