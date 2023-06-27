package com.blamejared.crafttweaker.natives.world.damage;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/damage/DamageSources")
@NativeTypeRegistration(value = DamageSources.class, zenCodeName = "crafttweaker.api.world.damage.DamageSources")
public class ExpandDamageSources {
    
    @ZenCodeType.Getter("inFire")
    public static DamageSource inFire(DamageSources internal) {
        
        return internal.inFire();
    }
    
    @ZenCodeType.Getter("lightningBolt")
    public static DamageSource lightningBolt(DamageSources internal) {
        
        return internal.lightningBolt();
    }
    
    @ZenCodeType.Getter("onFire")
    public static DamageSource onFire(DamageSources internal) {
        
        return internal.onFire();
    }
    
    @ZenCodeType.Getter("lava")
    public static DamageSource lava(DamageSources internal) {
        
        return internal.lava();
    }
    
    @ZenCodeType.Getter("hotFloor")
    public static DamageSource hotFloor(DamageSources internal) {
        
        return internal.hotFloor();
    }
    
    @ZenCodeType.Getter("inWall")
    public static DamageSource inWall(DamageSources internal) {
        
        return internal.inWall();
    }
    
    @ZenCodeType.Getter("cramming")
    public static DamageSource cramming(DamageSources internal) {
        
        return internal.cramming();
    }
    
    @ZenCodeType.Getter("drown")
    public static DamageSource drown(DamageSources internal) {
        
        return internal.drown();
    }
    
    @ZenCodeType.Getter("starve")
    public static DamageSource starve(DamageSources internal) {
        
        return internal.starve();
    }
    
    @ZenCodeType.Getter("cactus")
    public static DamageSource cactus(DamageSources internal) {
        
        return internal.cactus();
    }
    
    @ZenCodeType.Getter("fall")
    public static DamageSource fall(DamageSources internal) {
        
        return internal.fall();
    }
    
    @ZenCodeType.Getter("flyIntoWall")
    public static DamageSource flyIntoWall(DamageSources internal) {
        
        return internal.flyIntoWall();
    }
    
    @ZenCodeType.Getter("fellOutOfWorld")
    public static DamageSource fellOutOfWorld(DamageSources internal) {
        
        return internal.fellOutOfWorld();
    }
    
    @ZenCodeType.Getter("generic")
    public static DamageSource generic(DamageSources internal) {
        
        return internal.generic();
    }
    
    @ZenCodeType.Getter("magic")
    public static DamageSource magic(DamageSources internal) {
        
        return internal.magic();
    }
    
    @ZenCodeType.Getter("wither")
    public static DamageSource wither(DamageSources internal) {
        
        return internal.wither();
    }
    
    @ZenCodeType.Getter("dragonBreath")
    public static DamageSource dragonBreath(DamageSources internal) {
        
        return internal.dragonBreath();
    }
    
    @ZenCodeType.Getter("dryOut")
    public static DamageSource dryOut(DamageSources internal) {
        
        return internal.dryOut();
    }
    
    @ZenCodeType.Getter("sweetBerryBush")
    public static DamageSource sweetBerryBush(DamageSources internal) {
        
        return internal.sweetBerryBush();
    }
    
    @ZenCodeType.Getter("freeze")
    public static DamageSource freeze(DamageSources internal) {
        
        return internal.freeze();
    }
    
    @ZenCodeType.Getter("stalagmite")
    public static DamageSource stalagmite(DamageSources internal) {
        
        return internal.stalagmite();
    }
    
    @ZenCodeType.Method
    public static DamageSource fallingBlock(DamageSources internal, Entity entity) {
        
        return internal.fallingBlock(entity);
    }
    
    @ZenCodeType.Method
    public static DamageSource anvil(DamageSources internal, Entity entity) {
        
        return internal.anvil(entity);
    }
    
    @ZenCodeType.Method
    public static DamageSource fallingStalactite(DamageSources internal, Entity entity) {
        
        return internal.fallingStalactite(entity);
    }
    
    @ZenCodeType.Method
    public static DamageSource sting(DamageSources internal, LivingEntity entity) {
        
        return internal.sting(entity);
    }
    
    @ZenCodeType.Method
    public static DamageSource mobAttack(DamageSources internal, LivingEntity entity) {
        
        return internal.mobAttack(entity);
    }
    
    @ZenCodeType.Method
    public static DamageSource noAggroMobAttack(DamageSources internal, LivingEntity entity) {
        
        return internal.noAggroMobAttack(entity);
    }
    
    @ZenCodeType.Method
    public static DamageSource playerAttack(DamageSources internal, Player player) {
        
        return internal.playerAttack(player);
    }
    
    @ZenCodeType.Method
    public static DamageSource arrow(DamageSources internal, AbstractArrow arrow, @ZenCodeType.Optional Entity cause) {
        
        return internal.arrow(arrow, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource trident(DamageSources internal, Entity entity, @ZenCodeType.Optional Entity cause) {
        
        return internal.trident(entity, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource mobProjectile(DamageSources internal, Entity entity, @ZenCodeType.Optional LivingEntity cause) {
        
        return internal.mobProjectile(entity, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource fireworks(DamageSources internal, FireworkRocketEntity entity, @ZenCodeType.Optional Entity cause) {
        
        return internal.fireworks(entity, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource fireball(DamageSources internal, Fireball entity, @ZenCodeType.Optional Entity cause) {
        
        return internal.fireball(entity, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource witherSkull(DamageSources internal, WitherSkull entity, Entity cause) {
        
        return internal.witherSkull(entity, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource thrown(DamageSources internal, Entity entity, @ZenCodeType.Optional Entity cause) {
        
        return internal.thrown(entity, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource indirectMagic(DamageSources internal, Entity entity, @ZenCodeType.Optional Entity cause) {
        
        return internal.indirectMagic(entity, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource thorns(DamageSources internal, Entity entity) {
        
        return internal.thorns(entity);
    }
    
    @ZenCodeType.Method
    public static DamageSource explosion(DamageSources internal, @ZenCodeType.Optional Explosion explosion) {
        
        return internal.explosion(explosion);
    }
    
    @ZenCodeType.Method
    public static DamageSource explosion(DamageSources internal, @ZenCodeType.Optional Entity entity, @ZenCodeType.Optional Entity cause) {
        
        return internal.explosion(entity, cause);
    }
    
    @ZenCodeType.Method
    public static DamageSource sonicBoom(DamageSources internal, Entity entity) {
        
        return internal.sonicBoom(entity);
    }
    
    @ZenCodeType.Method
    public static DamageSource badRespawnPointExplosion(DamageSources internal, Vec3 position) {
        
        return internal.badRespawnPointExplosion(position);
    }
    
    @ZenCodeType.Getter
    public static DamageSource outOfBorder(DamageSources internal) {
        
        return internal.outOfBorder();
    }
    
    @ZenCodeType.Getter
    public static DamageSource genericKill(DamageSources internal) {
        
        return internal.genericKill();
    }
    
}
