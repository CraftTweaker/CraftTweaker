package com.blamejared.crafttweaker.natives.item.alchemy;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/item/alchemy/Potion")
@NativeTypeRegistration(value = Potion.class, zenCodeName = "crafttweaker.api.item.alchemy.Potion")
@TaggableElement("minecraft:potion")
public class ExpandPotion {
    
    @ZenCodeType.Method
    public static String getName(Potion internal, String prefix) {
        
        return internal.getName(prefix);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("effects")
    public static List<MobEffectInstance> getEffects(Potion internal) {
        
        return internal.getEffects();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasInstantEffects")
    public static boolean hasInstantEffects(Potion internal) {
        
        return internal.hasInstantEffects();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Potion internal) {
        
        return "<potion:" + Registry.POTION.getKey(internal) + ">";
    }
    
}
