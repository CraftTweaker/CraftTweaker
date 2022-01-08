package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;

@ZenRegister
@Document("vanilla/api/world/Explosion")
@NativeTypeRegistration(value = Explosion.class, zenCodeName = "crafttweaker.api.world.Explosion")
public class ExpandExplosion {
    
    // This is missing @Nullable ExplosionContext context because I don't even know how I would implement it.
    @ZenCodeType.StaticExpansionMethod
    public static Explosion create(Level world, double x, double y, double z, float size, boolean causesFire, Explosion.BlockInteraction mode, @ZenCodeType.Optional Entity exploder, @ZenCodeType.Optional DamageSource source) {
        
        return new Explosion(world, exploder, source, null, x, y, z, size, causesFire, mode);
    }
    
    /**
     * Performs the first part of the explosion which is destroying the blocks.
     */
    @ZenCodeType.Method
    public static void explode(Explosion internal) {
        
        internal.explode();
    }
    
    /**
     * Performs the second part of the explosion which is the sound, drops and if enabled the particles.
     *
     * @param spawnParticles Should particles be spawned.
     *
     * @docParam spawnParticles true
     */
    @ZenCodeType.Method
    public static void finalizeExplosion(Explosion internal, boolean spawnParticles) {
        
        internal.finalizeExplosion(spawnParticles);
    }
    
    /**
     * Gets the damage source of this Explosion.
     *
     * @return The damage source of this Explosion.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("damageSource")
    public static DamageSource getDamageSource(Explosion internal) {
        
        return internal.getDamageSource();
    }
    
    /**
     * Gets the player knockback map for this Explosion.
     *
     * This map is only populated in {@link Explosion#explode()} so calling it before will return nothing.
     *
     * This map is used to calculate the vectors that players around the explosion will be pushed back by.
     *
     * @return A Map of PlayerEntity to Vector3d depicting knockback vectors.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("playerKnockbackMap")
    public static Map<Player, Vec3> getHitPlayers(Explosion internal) {
        
        return internal.getHitPlayers();
    }
    
    /**
     * Gets the LivingEntity that caused this Explosion.
     *
     * For example:
     * If the Explosion was caused by TNT, it will return the PlayerEntity that placed it.
     * If the Explosion was caused by a Creeper or another Entity directly, it will return that Entity.
     * If the Explosion was caused by a Ghast fireball, it will return the Ghast.
     *
     * If no Entity caused this Explosion (for example, if the Explosion was caused by TNT in a Desert Temple that
     * generated in the world), then `null` is returned.
     *
     * You may need to cast the returned LivingEntity to not be nullable.
     *
     * @return The LivingEntity that caused this Explosion. `null` if no LivingEntity caused it.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("sourceMob")
    @ZenCodeType.Nullable
    public static LivingEntity getSourceMob(Explosion internal) {
        
        return internal.getSourceMob();
    }
    
    /**
     * Clears the affected block positions of this Explosion.
     */
    @ZenCodeType.Method
    public static void clearToBlow(Explosion internal) {
        
        internal.clearToBlow();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("toBlow")
    public static List<BlockPos> getToBlow(Explosion internal) {
        
        return internal.getToBlow();
    }
    
}
