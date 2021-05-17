package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.MCTextFormatting;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.Rarity;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/Rarity")
@NativeTypeRegistration(value = Rarity.class, zenCodeName = "crafttweaker.api.item.Rarity")
public class ExpandRarity {
    
    /**
     * Gets the color of this Rarity
     *
     * @return The color of this Rarity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("color")
    public static MCTextFormatting color(Rarity internal) {
        
        return new MCTextFormatting(internal.color);
    }
    
    /**
     * Creates a new Rarity with the given name and given color.
     *
     * @param name       The name of the new Rarity.
     * @param formatting The color of the rarity.
     *
     * @return A new Rarity with the given name and color
     *
     * @docParam name "insanely epic"
     * @docParam formatting <formatting:obfucated>
     */
    @ZenCodeType.StaticExpansionMethod
    public static Rarity create(String name, MCTextFormatting formatting) {
        
        return Rarity.create(name, formatting.getInternal());
    }
    
}
