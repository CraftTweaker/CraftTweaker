package com.blamejared.crafttweaker.natives.entity.type.projectile;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/WitherSkull")
@NativeTypeRegistration(value = WitherSkull.class, zenCodeName = "crafttweaker.api.entity.type.projectile.WitherSkull",
        constructors = {
                @NativeConstructor(value = {
                        @NativeConstructor.ConstructorParameter(name = "level", type = Level.class, description = "The level the entity is in.", examples = "level"),
                        @NativeConstructor.ConstructorParameter(name = "owner", type = LivingEntity.class, description = "The owner of the skull, used for position.", examples = "entity"),
                        @NativeConstructor.ConstructorParameter(name = "xPower", type = double.class, description = "The xPower of the entity.", examples = "9"),
                        @NativeConstructor.ConstructorParameter(name = "yPower", type = double.class, description = "The yPower of the entity.", examples = "9"),
                        @NativeConstructor.ConstructorParameter(name = "zPower", type = double.class, description = "The zPower of the entity.", examples = "9"),
                })
        })
public class ExpandWitherSkull {
    
    /**
     * Gets whether this skull is dangerous or not.
     *
     * @return true if dangerous, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isDangerous")
    public static boolean isDangerous(WitherSkull internal) {
        
        return internal.isDangerous();
    }
    
    /**
     * Sets whether this skull is dangerous or not.
     *
     * @docParam dangerous true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("isDangerous")
    public static void setDangerous(WitherSkull internal, boolean dangerous) {
        
        internal.setDangerous(dangerous);
    }
    
}
