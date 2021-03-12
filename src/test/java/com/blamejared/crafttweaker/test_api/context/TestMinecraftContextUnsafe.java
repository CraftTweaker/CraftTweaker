package com.blamejared.crafttweaker.test_api.context;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestMinecraftContextUnsafe implements TestMinecraftContext {
    
    public static final Unsafe unsafe;
    
    static {
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch(Exception ex) {
            throw new RuntimeException("Could not get unsafe", ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    private static <T> T allocateInstance(Class<? extends T> clsToAllocate) {
        
        try {
            return ((T) unsafe.allocateInstance(clsToAllocate));
        } catch(InstantiationException e) {
            throw new RuntimeException("Could not allocateInstance", e);
        }
    }
    
    @Override
    public World getWorld() {
        
        
        return allocateInstance(ServerWorld.class);
    }
    
    @Override
    public PlayerEntity getPlayer() {
        
        final ServerPlayerEntity serverPlayerEntity = allocateInstance(ServerPlayerEntity.class);
        serverPlayerEntity.world = getWorld();
        return serverPlayerEntity;
    }
    
}
