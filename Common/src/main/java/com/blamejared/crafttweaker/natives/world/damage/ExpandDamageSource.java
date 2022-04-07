package com.blamejared.crafttweaker.natives.world.damage;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
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

import java.util.HashMap;
import java.util.Map;

@ZenRegister
@Document("vanilla/api/world/DamageSource")
@NativeTypeRegistration(value = DamageSource.class, zenCodeName = "crafttweaker.api.world.DamageSource")
public class ExpandDamageSource {
    
    public static Map<String, DamageSource> PRE_REGISTERED_DAMAGE_SOURCES = Util.make(() -> {
        Map<String, DamageSource> temp = new HashMap<>();
        temp.put("inFire", DamageSource.IN_FIRE);
        temp.put("lightningBolt", DamageSource.LIGHTNING_BOLT);
        temp.put("onFire", DamageSource.ON_FIRE);
        temp.put("lava", DamageSource.LAVA);
        temp.put("hotFloor", DamageSource.HOT_FLOOR);
        temp.put("inWall", DamageSource.IN_WALL);
        temp.put("cramming", DamageSource.CRAMMING);
        temp.put("drown", DamageSource.DROWN);
        temp.put("starve", DamageSource.STARVE);
        temp.put("cactus", DamageSource.CACTUS);
        temp.put("fall", DamageSource.FALL);
        temp.put("flyIntoWall", DamageSource.FLY_INTO_WALL);
        temp.put("outOfWorld", DamageSource.OUT_OF_WORLD);
        temp.put("generic", DamageSource.GENERIC);
        temp.put("magic", DamageSource.MAGIC);
        temp.put("wither", DamageSource.WITHER);
        temp.put("anvil", DamageSource.ANVIL);
        temp.put("fallingBlock", DamageSource.FALLING_BLOCK);
        temp.put("dragonBreath", DamageSource.DRAGON_BREATH);
        temp.put("dryout", DamageSource.DRY_OUT);
        temp.put("sweetBerryBush", DamageSource.SWEET_BERRY_BUSH);
        temp.put("freeze", DamageSource.FREEZE);
        temp.put("fallingStalactite", DamageSource.FALLING_STALACTITE);
        temp.put("stalagmite", DamageSource.STALAGMITE);
        return temp;
    });
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isProjectile")
    public static boolean isProjectile(DamageSource internal) {
        
        return internal.isProjectile();
    }
    
    @ZenCodeType.Method
    public static DamageSource setProjectile(DamageSource internal) {
        
        return internal.setProjectile();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isExplosion")
    public static boolean isExplosion(DamageSource internal) {
        
        return internal.isExplosion();
    }
    
    @ZenCodeType.Method
    public static DamageSource setExplosion(DamageSource internal) {
        
        return internal.setExplosion();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("doesBypassArmor")
    public static boolean isBypassArmor(DamageSource internal) {
        
        return internal.isBypassArmor();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("doesDamageHelmet")
    public static boolean isDamageHelmet(DamageSource internal) {
        
        return internal.isDamageHelmet();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("foodExhaustion")
    public static float getFoodExhaustion(DamageSource internal) {
        
        return internal.getFoodExhaustion();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("doesBypassInvul")
    public static boolean isBypassInvul(DamageSource internal) {
        
        return internal.isBypassInvul();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("doesBypassMagic")
    public static boolean isBypassMagic(DamageSource internal) {
        
        return internal.isBypassMagic();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("directEntity")
    public static Entity getDirectEntity(DamageSource internal) {
        
        return internal.getDirectEntity();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("entity")
    public static Entity getEntity(DamageSource internal) {
        
        return internal.getEntity();
    }
    
    @ZenCodeType.Method
    public static DamageSource setNoAggro(DamageSource internal) {
        
        return internal.setNoAggro();
    }
    
    @ZenCodeType.Method
    public static Component getLocalizedDeathMessage(DamageSource internal, LivingEntity entity) {
        
        return internal.getLocalizedDeathMessage(entity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFire")
    public static boolean isFire(DamageSource internal) {
        
        return internal.isFire();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isNoAggro")
    public static boolean isNoAggro(DamageSource internal) {
        
        return internal.isNoAggro();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("msgId")
    public static String getMsgId(DamageSource internal) {
        
        return internal.getMsgId();
    }
    
    @ZenCodeType.Method
    public static DamageSource setScalesWithDifficulty(DamageSource internal) {
        
        return internal.setScalesWithDifficulty();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("scalesWithDifficulty")
    public static boolean scalesWithDifficulty(DamageSource internal) {
        
        return internal.scalesWithDifficulty();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isMagic")
    public static boolean isMagic(DamageSource internal) {
        
        return internal.isMagic();
    }
    
    @ZenCodeType.Method
    public static DamageSource setMagic(DamageSource internal) {
        
        return internal.setMagic();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFall")
    public static boolean isFall(DamageSource internal) {
        
        return internal.isFall();
    }
    
    @ZenCodeType.Method
    public static DamageSource setIsFall(DamageSource internal) {
        
        return internal.setIsFall();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCreativePlayer")
    public static boolean isCreativePlayer(DamageSource internal) {
        
        return internal.isCreativePlayer();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("source")
    public static Vec3 getSourcePosition(DamageSource internal) {
        
        return internal.getSourcePosition();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource string(LivingEntity living) {
        
        return DamageSource.sting(living);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource mobAttack(LivingEntity living) {
        
        return DamageSource.mobAttack(living);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource indirectMobAttack(Entity entity, @ZenCodeType.Nullable LivingEntity owner) {
        
        return DamageSource.indirectMobAttack(entity, owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource playerAttack(Player player) {
        
        return DamageSource.playerAttack(player);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource arrow(AbstractArrow arrow, @ZenCodeType.Nullable Entity owner) {
        
        return DamageSource.arrow(arrow, owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource trident(Entity entity, @ZenCodeType.Nullable Entity owner) {
        
        return DamageSource.trident(entity, owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource fireworks(FireworkRocketEntity entity, @ZenCodeType.Nullable Entity owner) {
        
        return DamageSource.fireworks(entity, owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource fireball(Fireball fireball, @ZenCodeType.Nullable Entity owner) {
        
        return DamageSource.fireball(fireball, owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource witherSkull(WitherSkull witherSkull, @ZenCodeType.Nullable Entity owner) {
        
        return DamageSource.witherSkull(witherSkull, owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource thrown(Entity entity, @ZenCodeType.Nullable Entity owner) {
        
        return DamageSource.thrown(entity, owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource indirectMagic(Entity entity, @ZenCodeType.Nullable Entity owner) {
        
        return DamageSource.indirectMagic(entity, owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource thorns(Entity entity) {
        
        return DamageSource.thorns(entity);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource explosion(@ZenCodeType.Nullable Explosion explosion) {
        
        return DamageSource.explosion(explosion);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource explosion(@ZenCodeType.Optional @ZenCodeType.Nullable LivingEntity owner) {
        
        return DamageSource.explosion(owner);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static DamageSource badRespawnPointExplosion() {
        
        return DamageSource.badRespawnPointExplosion();
    }
    
}
