package com.blamejared.crafttweaker.natives.world.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.storage.LevelData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/data/LevelData")
@NativeTypeRegistration(value = LevelData.class, zenCodeName = "crafttweaker.api.world.data.LevelData")
public class ExpandLevelData {
    
    @ZenCodeType.Getter("xSpawn")
    public static int getXSpawn(LevelData internal) {
        
        return internal.getXSpawn();
    }
    
    @ZenCodeType.Getter("ySpawn")
    public static int getYSpawn(LevelData internal) {
        
        return internal.getYSpawn();
    }
    
    @ZenCodeType.Getter("zSpawn")
    public static int getZSpawn(LevelData internal) {
        
        return internal.getZSpawn();
    }
    
    @ZenCodeType.Getter("spawnAngle")
    public static float getSpawnAngle(LevelData internal) {
        
        return internal.getSpawnAngle();
    }
    
    @ZenCodeType.Getter("gameTime")
    public static long getGameTime(LevelData internal) {
        
        return internal.getGameTime();
    }
    
    @ZenCodeType.Getter("dayTime")
    public static long getDayTime(LevelData internal) {
        
        return internal.getDayTime();
    }
    
    @ZenCodeType.Getter("thundering")
    public static boolean isThundering(LevelData internal) {
        
        return internal.isThundering();
    }
    
    @ZenCodeType.Getter("raining")
    public static boolean isRaining(LevelData internal) {
        
        return internal.isRaining();
    }
    
    @ZenCodeType.Setter("raining")
    public static void setRaining(LevelData internal, boolean raining) {
        
        internal.setRaining(raining);
    }
    
    @ZenCodeType.Getter("hardcore")
    public static boolean isHardcore(LevelData internal) {
        
        return internal.isHardcore();
    }
    
    @ZenCodeType.Getter("difficulty")
    public static Difficulty getDifficulty(LevelData internal) {
        
        return internal.getDifficulty();
    }
    
    @ZenCodeType.Getter("isDifficultyLocked")
    public static boolean isDifficultyLocked(LevelData internal) {
        
        return internal.isDifficultyLocked();
    }
    
}
