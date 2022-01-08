package com.blamejared.crafttweaker.natives.entity.type.projectile;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/projectile/Projectile")
@NativeTypeRegistration(value = Projectile.class, zenCodeName = "crafttweaker.api.entity.type.projectile.Projectile")
public class ExpandProjectile {
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("owner")
    public static void setOwner(Projectile internal, @ZenCodeType.Nullable Entity entity) {
        
        internal.setOwner(entity);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("owner")
    public static Entity getOwner(Projectile internal) {
        
        return internal.getOwner();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("effectSource")
    public static Entity getEffectSource(Projectile internal) {
        
        return internal.getEffectSource();
    }
    
    public static void shoot(Projectile internal, double x, double y, double z, float velocity, float innacuracy) {
        
        internal.shoot(x, y, z, velocity, innacuracy);
    }
    
    public static void shootFromRotation(Projectile internal, Entity projectile, float x, float y, float z, float velocity, float innacuracy) {
        
        internal.shootFromRotation(projectile, x, y, z, velocity, innacuracy);
    }
    
}
