package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/PlacementType")
@NativeTypeRegistration(value = EntitySpawnPlacementRegistry.PlacementType.class, zenCodeName = "crafttweaker.api.entity.PlacementType")
public class ExpandPlacementType {
    
    /**
     * Checks if a specific entity type can spawn in the world at the given position with this PlacementType.
     *
     * @param world      A World object.
     * @param pos        The position to check at.
     * @param entityType The EntityType to check for.
     *
     * @return True if the entity type can spawn. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1,2,3)
     * @docParam entityType <entitytype:minecraft:pig>
     */
    @ZenCodeType.Method
    public static boolean canSpawnAt(EntitySpawnPlacementRegistry.PlacementType internal, World world, BlockPos pos, MCEntityType entityType) {
        
        return internal.canSpawnAt(world, pos, entityType.getInternal());
    }
    
    
}
