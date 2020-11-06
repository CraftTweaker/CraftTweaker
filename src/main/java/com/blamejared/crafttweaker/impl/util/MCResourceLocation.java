package com.blamejared.crafttweaker.impl.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.impl.util.MCResourceLocation;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.MCResourceLocation")
@Document("vanilla/api/util/MCResourceLocation")
@ZenWrapper(wrappedClass = "net.minecraft.util.ResourceLocation", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCResourceLocation implements CommandStringDisplayable {
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
    public String toString() {
        return (internal.toString());
    }


    @ZenCodeType.Method
    public boolean equals(Object other) {
        return other instanceof MCResourceLocation && internal.equals((((MCResourceLocation) other).getInternal()));
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
