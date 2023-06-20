package com.blamejared.crafttweaker.natives.world.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.WritableLevelData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/data/WritableLevelData")
@NativeTypeRegistration(value = WritableLevelData.class, zenCodeName = "crafttweaker.api.world.data.WritableLevelData")
public class ExpandWritableLevelData {
    
    
    @ZenCodeType.Setter("xSpawn")
    public static void setXSpawn(WritableLevelData internal, int xSpawn) {
        
        internal.setXSpawn(xSpawn);
    }
    
    @ZenCodeType.Setter("ySpawn")
    public static void setYSpawn(WritableLevelData internal, int ySpawn) {
        
        internal.setYSpawn(ySpawn);
    }
    
    @ZenCodeType.Setter("zSpawn")
    public static void setZSpawn(WritableLevelData internal, int zSpawn) {
        
        internal.setZSpawn(zSpawn);
    }
    
    @ZenCodeType.Setter("spawnAngle")
    public static void setSpawnAngle(WritableLevelData internal, float spawnAngle) {
        
        internal.setSpawnAngle(spawnAngle);
    }
    
    @ZenCodeType.Method
    public static void setSpawn(WritableLevelData internal, BlockPos pos, float spawnAngle) {
        
        internal.setSpawn(pos, spawnAngle);
    }
    
}
