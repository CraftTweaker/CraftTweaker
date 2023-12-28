package com.blamejared.crafttweaker.natives.entity.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffectCategory;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/effect/MobEffectCategory")
@NativeTypeRegistration(value = MobEffectCategory.class, zenCodeName = "crafttweaker.api.entity.effect.MobEffectCategory")
@BracketEnum("minecraft:mobeffect/category")
public class ExpandMobEffectCategory {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tooltipFormatting")
    public static ChatFormatting getTooltipFormatting(MobEffectCategory internal) {
        
        return internal.getTooltipFormatting();
    }
    
}
