package com.blamejared.crafttweaker.impl_native.server;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.Objects;

@ZenRegister
@Document("vanilla/api/game/MCServer")
@NativeTypeRegistration(value = MinecraftServer.class, zenCodeName = "crafttweaker.api.server.MCServer")
public class ExpandServer {
    
    @ZenCodeType.Getter("worlds")
    public static Collection<ServerWorld> getWorlds(MinecraftServer internal) {
        
        return (Collection<ServerWorld>) internal.getWorlds();
    }
    
    /**
     * Get a server world instance based on resource location
     * Throws an exception if the world location is unknown
     *
     * @docParam location <resource:minecraft:the_end>
     */
    @ZenCodeType.Method
    public static ServerWorld getWorld(MinecraftServer internal, ResourceLocation location) {
        
        return Objects.requireNonNull(internal.getWorld(RegistryKey.getOrCreateKey(Registry.WORLD_KEY, location)),
                () -> "Unknown world location: " + location.toString());
    }
    
    /**
     * Runs a command.
     *
     * Returns The success value of the command, or 0 if an exception occurred.
     * <p>
     * Note: Some commands' success value is 0
     */
    @ZenCodeType.Method
    public static int executeCommand(MinecraftServer internal, String command) {
        
        return internal.getCommandManager().handleCommand(internal.getCommandSource(), command);
    }
    
    /**
     * let a player send a command
     *
     * Returns The success value of the command, or 0 if an exception occurred.
     * <p>
     * Note: Some command's success value is 0
     */
    @ZenCodeType.Method
    public static int executeCommand(MinecraftServer internal, String command, PlayerEntity player) {
        
        return internal.getCommandManager().handleCommand(player.getCommandSource(), command);
    }
    
}
