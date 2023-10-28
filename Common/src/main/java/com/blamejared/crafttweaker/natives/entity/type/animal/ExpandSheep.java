package com.blamejared.crafttweaker.natives.entity.type.animal;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/animal/Sheep")
@NativeTypeRegistration(value = Sheep.class, zenCodeName = "crafttweaker.api.entity.type.animal.Sheep")
public class ExpandSheep {
    
    
    @ZenCodeType.Method
    public static float getHeadEatPositionScale(Sheep internal, float partialTick) {
        
        return internal.getHeadEatPositionScale(partialTick);
    }
    
    @ZenCodeType.Method
    public static float getHeadEatAngleScale(Sheep internal, float partialTick) {
        
        return internal.getHeadEatAngleScale(partialTick);
    }
    
    @ZenCodeType.Getter("color")
    public static DyeColor getColor(Sheep internal) {
        
        return internal.getColor();
    }
    
    @ZenCodeType.Setter("color")
    public static void setColor(Sheep internal, DyeColor color) {
        
        internal.setColor(color);
    }
    
    @ZenCodeType.Getter("sheared")
    public static boolean isSheared(Sheep internal) {
        
        return internal.isSheared();
    }
    
    @ZenCodeType.Setter("sheared")
    public static void setSheared(Sheep internal, boolean sheared) {
        
        internal.setSheared(sheared);
    }
    
}
