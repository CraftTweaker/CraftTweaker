package com.blamejared.crafttweaker.impl_native.world;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Map;

@ZenRegister
@Document("vanilla/api/world/Explosion")
@NativeTypeRegistration(value = Explosion.class, zenCodeName = "crafttweaker.api.world.Explosion")
public class ExpandExplosion {
    
    // This is missing @Nullable ExplosionContext context because I don't even know how I would implement it.
    @ZenCodeType.StaticExpansionMethod
    public static Explosion create(World world, double x, double y, double z, float size, boolean causesFire, Explosion.Mode mode, @ZenCodeType.Optional Entity exploder, @ZenCodeType.Optional DamageSource source) {
        
        return new Explosion(world, exploder, source, null, x, y, z, size, causesFire, mode);
    }
    
    /**
     * Performs the first part of the explosion which is destroying the blocks.
     */
    @ZenCodeType.Method
    public static void doExplosionA(Explosion internal) {
        
        internal.doExplosionA();
    }
    
    /**
     * Performs the second part of the explosion which is the sound, drops and if enabled the particles.
     *
     * @param spawnParticles Should particles be spawned.
     *
     * @docParam spawnParticles true
     */
    @ZenCodeType.Method
    public static void doExplosionB(Explosion internal, boolean spawnParticles) {
        
        internal.doExplosionB(spawnParticles);
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
     * This map is only populated in {@link Explosion#doExplosionA()} so calling it before will return nothing.
     *
     * This map is used to calculate the vectors that players around the explosion will be pushed back by.
     *
     * @return A Map of PlayerEntity to Vector3d depicting knockback vectors.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("playerKnockbackMap")
    public static Map<PlayerEntity, Vector3d> getPlayerKnockbackMap(Explosion internal) {
        
        return internal.getPlayerKnockbackMap();
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
    @ZenCodeType.Getter("explosivePlacedBy")
    @ZenCodeType.Nullable
    public static LivingEntity getExplosivePlacedBy(Explosion internal) {
        
        return internal.getExplosivePlacedBy();
    }
    
    /**
     * Clears the affected block positions of this Explosion.
     */
    @ZenCodeType.Method
    public static void clearAffectedBlockPositions(Explosion internal) {
        
        internal.clearAffectedBlockPositions();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("affectedBlockPositions")
    public static List<BlockPos> getAffectedBlockPositions(Explosion internal) {
        
        return internal.getAffectedBlockPositions();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("position")
    public static Vector3d getPosition(Explosion internal) {
        
        return internal.getPosition();
    }
    
    /**
     * Gets the actual Entity that caused this Explosion to occur.
     *
     * Examples:
     * TNT will return itself.
     * Creeper will return itself.
     * A Ghast fireball will return itself.
     *
     * You may need to cast the returned Entity to not be nullable.
     *
     * @return The Entity that caused this Explosion. If the Explosion wasn't caused by any Entity, it will return null.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter
    @ZenCodeType.Nullable
    public static Entity getExploder(Explosion internal) {
        
        return internal.getExploder();
    }
    
}
