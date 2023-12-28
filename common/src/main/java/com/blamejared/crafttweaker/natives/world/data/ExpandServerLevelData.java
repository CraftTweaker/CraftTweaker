package com.blamejared.crafttweaker.natives.world.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.storage.ServerLevelData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/data/ServerLevelData")
@NativeTypeRegistration(value = ServerLevelData.class, zenCodeName = "crafttweaker.api.world.data.ServerLevelData")
public class ExpandServerLevelData {
    
    
    @ZenCodeType.Getter("levelName")
    public static String getLevelName(ServerLevelData internal) {
        
        return internal.getLevelName();
    }
    
    @ZenCodeType.Setter("thundering")
    public static void setThundering(ServerLevelData internal, boolean thundering) {
        
        internal.setThundering(thundering);
    }
    
    @ZenCodeType.Getter("rainTime")
    public static int getRainTime(ServerLevelData internal) {
        
        return internal.getRainTime();
    }
    
    @ZenCodeType.Setter("rainTime")
    public static void setRainTime(ServerLevelData internal, int time) {
        
        internal.setRainTime(time);
    }
    
    @ZenCodeType.Setter("thunderTime")
    public static void setThunderTime(ServerLevelData internal, int time) {
        
        internal.setThunderTime(time);
    }
    
    @ZenCodeType.Getter("thunderTime")
    public static int getThunderTime(ServerLevelData internal) {
        
        return internal.getThunderTime();
    }
    
    @ZenCodeType.Getter("clearWeatherTime")
    public static int getClearWeatherTime(ServerLevelData internal) {
        
        return internal.getClearWeatherTime();
    }
    
    @ZenCodeType.Setter("clearWeatherTime")
    public static void setClearWeatherTime(ServerLevelData internal, int time) {
        
        internal.setClearWeatherTime(time);
    }
    
    @ZenCodeType.Getter("wanderingTraderSpawnDelay")
    public static int getWanderingTraderSpawnDelay(ServerLevelData internal) {
        
        return internal.getWanderingTraderSpawnDelay();
    }
    
    @ZenCodeType.Setter("wanderingTraderSpawnDelay")
    public static void setWanderingTraderSpawnDelay(ServerLevelData internal, int delay) {
        
        internal.setWanderingTraderSpawnDelay(delay);
    }
    
    @ZenCodeType.Getter("wanderingTraderSpawnChance")
    public static int getWanderingTraderSpawnChance(ServerLevelData internal) {
        
        return internal.getWanderingTraderSpawnChance();
    }
    
    @ZenCodeType.Setter("wanderingTraderSpawnChance")
    public static void setWanderingTraderSpawnChance(ServerLevelData internal, int chance) {
        
        internal.setWanderingTraderSpawnChance(chance);
    }
    
    @ZenCodeType.Getter("gameType")
    public static GameType getGameType(ServerLevelData internal) {
        
        return internal.getGameType();
    }
    
    @ZenCodeType.Setter("worldBorder")
    public static void setWorldBorder(ServerLevelData internal, WorldBorder.Settings border) {
        
        internal.setWorldBorder(border);
    }
    
    @ZenCodeType.Getter("worldBorder")
    public static WorldBorder.Settings getWorldBorder(ServerLevelData internal) {
        
        return internal.getWorldBorder();
    }
    
    @ZenCodeType.Getter("initialized")
    public static boolean isInitialized(ServerLevelData internal) {
        
        return internal.isInitialized();
    }
    
    @ZenCodeType.Getter("allowCommands")
    public static boolean getAllowCommands(ServerLevelData internal) {
        
        return internal.getAllowCommands();
    }
    
    @ZenCodeType.Setter("gameType")
    public static void setGameType(ServerLevelData internal, GameType gameType) {
        
        internal.setGameType(gameType);
    }
    
    @ZenCodeType.Setter("gameTime")
    public static void setGameTime(ServerLevelData internal, long time) {
        
        internal.setGameTime(time);
    }
    
    @ZenCodeType.Setter("dayTime")
    public static void setDayTime(ServerLevelData internal, long time) {
        
        internal.setDayTime(time);
    }
    
}
