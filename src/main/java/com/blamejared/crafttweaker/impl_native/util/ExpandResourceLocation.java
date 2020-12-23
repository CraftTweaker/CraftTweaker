package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.StringData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/MCResourceLocation")
@NativeTypeRegistration(value = ResourceLocation.class, zenCodeName = ExpandResourceLocation.ZC_CLASS_NAME, constructors = @NativeConstructor({@NativeConstructor.ConstructorParameter(name = "namespace", type = String.class, description = "Usually a ModId"), @NativeConstructor.ConstructorParameter(name = "path", type = String.class, description = "May only contain lower-cased alphanumeric values, as well as / and _")}))
public class ExpandResourceLocation {
    
    public static final String ZC_CLASS_NAME = "crafttweaker.api.util.MCResourceLocation";
    
    @ZenCodeType.Method
    public static int compareTo(ResourceLocation internal, ResourceLocation other) {
        return internal.compareTo(other);
    }
    
    
    @ZenCodeType.Method
    public static int hashCode(ResourceLocation internal) {
        return internal.hashCode();
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("path")
    public static String getPath(ResourceLocation internal) {
        return (internal.getPath());
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static String toString(ResourceLocation internal) {
        return (internal.toString());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IData asData(ResourceLocation internal) {
        return new StringData(toString(internal));
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public static boolean equals(ResourceLocation internal, Object other) {
        return internal.equals(other);
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("namespace")
    public static String getNamespace(ResourceLocation internal) {
        return (internal.getNamespace());
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(ResourceLocation internal) {
        return "<resource:" + internal + ">";
    }
}
