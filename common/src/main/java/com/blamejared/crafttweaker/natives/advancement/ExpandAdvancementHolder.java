package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/AdvancementHolder")
@NativeTypeRegistration(value = AdvancementHolder.class, zenCodeName = "crafttweaker.api.advancement.AdvancementHolder")
public class ExpandAdvancementHolder {
    
    @ZenCodeType.Getter("id")
    public static ResourceLocation id(AdvancementHolder internal) {
        
        return internal.id();
    }
    
    @ZenCodeType.Getter("value")
    public static Advancement value(AdvancementHolder internal) {
        
        return internal.value();
    }
    
}
