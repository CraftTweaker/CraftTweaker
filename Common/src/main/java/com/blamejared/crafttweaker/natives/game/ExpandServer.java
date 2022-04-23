package com.blamejared.crafttweaker.natives.game;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.level.CraftTweakerSavedData;
import com.blamejared.crafttweaker.api.level.CraftTweakerSavedDataHolder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.storage.loot.LootTables;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/game/Server")
@NativeTypeRegistration(value = MinecraftServer.class, zenCodeName = "crafttweaker.api.game.Server")
public class ExpandServer {
    
    /**
     * Gets the custom data of the overworld.
     *
     * <p>The overworld is always loaded, so this can be used to store and access data no matter what level a player may be in.</p>
     *
     * @return The overworld's custom data.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("overworldData")
    public static CraftTweakerSavedData getOverworldData(MinecraftServer internal) {
        
        return ((CraftTweakerSavedDataHolder) internal.overworld()).crafttweaker$getSavedData();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultGameTime")
    public static GameType getDefaultGameType(MinecraftServer internal) {
        
        return internal.getDefaultGameType();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isHardcore")
    public static boolean isHardcore(MinecraftServer internal) {
        
        return internal.isHardcore();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("operatorUserPermissionLevel")
    public static int getOperatorUserPermissionLevel(MinecraftServer internal) {
        
        return internal.getOperatorUserPermissionLevel();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isRunning")
    public static boolean isRunning(MinecraftServer internal) {
        
        return internal.isRunning();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isNetherEnabled")
    public static boolean isNetherEnabled(MinecraftServer internal) {
        
        return internal.isNetherEnabled();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isShutdown")
    public static boolean isShutdown(MinecraftServer internal) {
        
        return internal.isShutdown();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("overworld")
    public static ServerLevel overworld(MinecraftServer internal) {
        
        return internal.overworld();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static ServerLevel getLevel(MinecraftServer internal, ResourceLocation location) {
        
        return internal.getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, location));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("levelKeys")
    public static List<ResourceLocation> levelKeys(MinecraftServer internal) {
        
        return internal.levelKeys().stream().map(ResourceKey::location).toList();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("allLevels")
    public static Iterable<ServerLevel> getAllLevels(MinecraftServer internal) {
        
        return internal.getAllLevels();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("serverVersion")
    public static String getServerVersion(MinecraftServer internal) {
        
        return internal.getServerVersion();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("playerCount")
    public static int getPlayerCount(MinecraftServer internal) {
        
        return internal.getPlayerCount();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxPlayer")
    public static int getMaxPlayers(MinecraftServer internal) {
        
        return internal.getMaxPlayers();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("playerNames")
    public static String[] getPlayerNames(MinecraftServer internal) {
        
        return internal.getPlayerNames();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("serverModName")
    public static String getServerModName(MinecraftServer internal) {
        
        return internal.getServerModName();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("moddeStatus")
    public static String getModdedStatus(MinecraftServer internal) {
        
        return internal.getModdedStatus().fullDescription();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSingleplayer")
    public static boolean isSingleplayer(MinecraftServer internal) {
        
        return internal.isSingleplayer();
    }
    
    @ZenCodeType.Method
    public static void setDifficulty(MinecraftServer internal, Difficulty difficulty, boolean force) {
        
        internal.setDifficulty(difficulty, force);
    }
    
    @ZenCodeType.Method
    public static void setDifficultyLocked(MinecraftServer internal, boolean locked) {
        
        internal.setDifficultyLocked(locked);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSpawningMonsters")
    public static boolean isSpawningMonsters(MinecraftServer internal) {
        
        return internal.isSpawningMonsters();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isDedicatedServer")
    public static boolean isDedicatedServer(MinecraftServer internal) {
        
        return internal.isDedicatedServer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSpawningAnimals")
    public static boolean isSpawningAnimals(MinecraftServer internal) {
        
        return internal.isSpawningAnimals();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("areNpcsEnabled")
    public static boolean areNpcsEnabled(MinecraftServer internal) {
        
        return internal.areNpcsEnabled();
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isPvpAllowed")
    public static boolean isPvpAllowed(MinecraftServer internal) {
        
        return internal.isPvpAllowed();
    }
    
    @ZenCodeType.Method
    public static void setPvpAllowed(MinecraftServer internal, boolean pvpAllowed) {
        
        internal.setPvpAllowed(pvpAllowed);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFlightAllowed")
    public static boolean isFlightAllowed(MinecraftServer internal) {
        
        return internal.isFlightAllowed();
    }
    
    @ZenCodeType.Method
    public static void setFlightAllowed(MinecraftServer internal, boolean flightAllowed) {
        
        internal.setFlightAllowed(flightAllowed);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCommandBlockEnabled")
    public static boolean isCommandBlockEnabled(MinecraftServer internal) {
        
        return internal.isCommandBlockEnabled();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("motd")
    public static String getMotd(MinecraftServer internal) {
        
        return internal.getMotd();
    }
    
    @ZenCodeType.Method
    public static void setMotd(MinecraftServer internal, String motd) {
        
        internal.setMotd(motd);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isStopped")
    public static boolean isStopped(MinecraftServer internal) {
        
        return internal.isStopped();
    }
    
    @ZenCodeType.Method
    public static void setDefaultGameType(MinecraftServer internal, GameType gameType) {
        
        internal.setDefaultGameType(gameType);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isReady")
    public static boolean isReady(MinecraftServer internal) {
        
        return internal.isReady();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tickCount")
    public static int getTickCount(MinecraftServer internal) {
        
        return internal.getTickCount();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("spawnProtectionRadius")
    public static int getSpawnProtectionRadius(MinecraftServer internal) {
        
        return internal.getSpawnProtectionRadius();
    }
    
    @ZenCodeType.Method
    public static boolean isUnderSpawnProtection(MinecraftServer internal, ServerLevel level, BlockPos pos, Player player) {
        
        return internal.isUnderSpawnProtection(level, pos, player);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("absoluteMaxWorldSize")
    public static int getAbsoluteMaxWorldSize(MinecraftServer internal) {
        
        return internal.getAbsoluteMaxWorldSize();
    }
    
    @ZenCodeType.Method
    public static int getSpawnRadius(MinecraftServer internal, @ZenCodeType.Nullable ServerLevel level) {
        
        return internal.getSpawnRadius(level);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isEnforceWhitelist")
    public static boolean isEnforceWhitelist(MinecraftServer internal) {
        
        return internal.isEnforceWhitelist();
    }
    
    @ZenCodeType.Method
    public static void setEnforceWhitelist(MinecraftServer internal, boolean enforceWhitelist) {
        
        internal.setEnforceWhitelist(enforceWhitelist);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("averageTickTime")
    public static float getAverageTickTime(MinecraftServer internal) {
        
        return internal.getAverageTickTime();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("forcedGameType")
    public static GameType getForcedGameType(MinecraftServer internal) {
        
        return internal.getForcedGameType();
    }
    
    /**
     * Runs a command, if silent is true, the output is hidden.
     *
     * Returns The success value of the command, or 0 if an exception occurred.
     * <p>
     * Note: Some commands' success value is 0
     *
     * @docParam command "time set day"
     * @docParam silent true
     */
    @ZenCodeType.Method
    public static int executeCommand(MinecraftServer internal, String command, @ZenCodeType.OptionalBoolean boolean silent) {
        
        CommandSourceStack source = internal.createCommandSourceStack();
        return executeCommandInternal(internal, command, silent ? source.withSuppressedOutput() : source);
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
    public static int executeCommand(MinecraftServer internal, String command, Player player, @ZenCodeType.OptionalBoolean boolean silent) {
        
        CommandSourceStack source = player.createCommandSourceStack();
        return executeCommandInternal(internal, command, silent ? source.withSuppressedOutput() : source);
    }
    
    private static int executeCommandInternal(MinecraftServer internal, String command, CommandSourceStack source) {
        
        return internal.getCommands().performCommand(source, command);
    }
    
}
