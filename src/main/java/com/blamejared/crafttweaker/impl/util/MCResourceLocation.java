package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.impl.util.MCResourceLocation;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name(MCResourceLocation.ZC_CLASS_NAME)
@Document("vanilla/api/util/MCResourceLocation")
@ZenWrapper(wrappedClass = "net.minecraft.util.ResourceLocation", displayStringFormat = "%s.toString()")
public class MCResourceLocation implements CommandStringDisplayable {
    public static final String ZC_CLASS_NAME = "crafttweaker.api.util.MCResourceLocation";
    
    private final ResourceLocation internal;
    
    public MCResourceLocation(ResourceLocation internal){
        this.internal = internal;
    }
    
    public ResourceLocation getInternal() {
        return this.internal;
    }
    
    @ZenCodeType.Constructor
    public MCResourceLocation(String namespace, String path) {
        this(new ResourceLocation(namespace, path));
    }

    @ZenCodeType.Method
    public int compareTo(MCResourceLocation p_compareTo_1_) {
        return internal.compareTo((p_compareTo_1_).getInternal());
    }


    @ZenCodeType.Method
    public int hashCode() {
        return internal.hashCode();
    }


    @ZenCodeType.Method
    @ZenCodeType.Getter("path")
    public String getPath() {
        return (internal.getPath());
    }


    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        return (internal.toString());
    }
    
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(MCResourceLocation other) {
        return internal.equals(other.getInternal());
    }
    
    @ZenCodeType.Method
    public boolean equals(Object other) {
        return other instanceof MCResourceLocation && equals((MCResourceLocation) other);
    }


    @ZenCodeType.Method
    @ZenCodeType.Getter("namespace")
    public String getNamespace() {
        return (internal.getNamespace());
    }
    
    @ZenCodeType.Getter("commandString")
    public String getCommandString() {
        return "<resource:" + internal + ">";
    }
}
