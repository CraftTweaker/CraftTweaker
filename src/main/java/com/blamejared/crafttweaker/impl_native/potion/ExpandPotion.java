package com.blamejared.crafttweaker.impl_native.potion;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/potions/MCPotion")
@NativeTypeRegistration(value = Potion.class, zenCodeName = "crafttweaker.api.potion.MCPotion")
public class ExpandPotion {
    
    @ZenCodeType.Method
    public static String getNamePrefixed(Potion internal, String name) {
        return internal.getNamePrefixed(name);
    }
    
    @ZenCodeType.Getter("effects")
    public static List<EffectInstance> getEffects(Potion internal) {
        return internal.getEffects();
    }
    
    @ZenCodeType.Getter("hasInstantEffect")
    public static boolean hasInstantEffect(Potion internal) {
        return internal.hasInstantEffect();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Potion internal) {
        return "<potion:" + internal.getRegistryName() + ">";
    }
}
