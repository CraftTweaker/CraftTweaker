package com.blamejared.crafttweaker.natives.item.property;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/property/Rarity")
@NativeTypeRegistration(value = Rarity.class, zenCodeName = "crafttweaker.api.item.property.Rarity")
@BracketEnum("minecraft:item/rarity")
public class ExpandRarity {
    
    /**
     * Gets the color of this Rarity
     *
     * @return The color of this Rarity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("color")
    public static ChatFormatting color(Rarity internal) {
        
        return internal.color;
    }
    
}
