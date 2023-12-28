package com.blamejared.crafttweaker.natives.entity.type.projectile;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/FireworkRocketEntity")
@NativeTypeRegistration(value = FireworkRocketEntity.class, zenCodeName = "crafttweaker.api.entity.type.projectile.FireworkRocketEntity",
        constructors = {
                @NativeConstructor(value = {
                        @NativeConstructor.ConstructorParameter(name = "level", type = Level.class, description = "The level the entity is in.", examples = "level"),
                        @NativeConstructor.ConstructorParameter(name = "x", type = double.class, description = "The x position of the entity.", examples = "0"),
                        @NativeConstructor.ConstructorParameter(name = "y", type = double.class, description = "The y position of the entity.", examples = "0"),
                        @NativeConstructor.ConstructorParameter(name = "z", type = double.class, description = "The z position of the entity.", examples = "0"),
                        @NativeConstructor.ConstructorParameter(name = "stack", type = ItemStack.class, description = "The optional firework stack.", examples = "<item:minecraft:air>")
                })
        })
public class ExpandFireworkRocketEntity {
    
    /**
     * Checks whether this firework rocket was shot at an angle or not.
     *
     * @return true if shot at an angle, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isShotAtAngle")
    public static boolean isShotAtAngle(FireworkRocketEntity internal) {
        
        return internal.isShotAtAngle();
    }
    
}
