package com.blamejared.crafttweaker.impl_native.server;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.Objects;

/**
 * @docParam this world.asServerWorld().server
 */
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
     * Runs a command, if silent is true, the output is hidden.
     *
     * Returns The success value of the command, or 0 if an exception occurred.
     * <p>
     * Note: Some commands' success value is 0
     *
     * @docParam command "time set day"
     *  @docParam silent true
     */
    @ZenCodeType.Method
    public static int executeCommand(MinecraftServer internal, String command, @ZenCodeType.OptionalBoolean boolean silent) {
        
        CommandSource source = internal.getCommandSource();
        return executeCommandInternal(internal, command, silent ? source.withFeedbackDisabled() : source);
    }
    
    // Left over for binary compat, doesn't need to be registered
    // TODO remove
    @Deprecated
    public static int executeCommand(MinecraftServer internal, String command) {
        
        return executeCommandInternal(internal, command, internal.getCommandSource());
    }
    
    /**
     * let a player send a command, if silent is true, the output is hidden.
     *
     * Returns The success value of the command, or 0 if an exception occurred.
     * <p>
     * Note: Some commands' success value is 0
     *
     * @docParam command "time set day"
     * @docParam player player
     * @docParam silent true
     */
    @ZenCodeType.Method
    public static int executeCommand(MinecraftServer internal, String command, PlayerEntity player, @ZenCodeType.OptionalBoolean boolean silent) {
        
        CommandSource source = player.getCommandSource();
        return executeCommandInternal(internal, command, silent ? source.withFeedbackDisabled() : source);
    }
    
    // Left over for binary compat, doesn't need to be registered
    // TODO remove
    @Deprecated
    public static int executeCommand(MinecraftServer internal, String command, PlayerEntity player) {
        
        CommandSource source = player.getCommandSource();
        return executeCommandInternal(internal, command, source);
    }
    
    private static int executeCommandInternal(MinecraftServer internal, String command, CommandSource source) {
        
        return internal.getCommandManager().handleCommand(source, command);
    }
    
}
