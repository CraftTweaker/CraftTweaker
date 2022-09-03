package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.NbtPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/NbtPredicate")
@NativeTypeRegistration(value = NbtPredicate.class, zenCodeName = "crafttweaker.api.predicate.NbtPredicate")
public final class ExpandNbtPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static NbtPredicate any() {
        
        return NbtPredicate.ANY;
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static NbtPredicate create(final MapData data) {
        
        return new NbtPredicate(data.getInternal());
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static NbtPredicate create(final IData data) {
        
        return create(new MapData(data.asMap()));
    }
    
}
