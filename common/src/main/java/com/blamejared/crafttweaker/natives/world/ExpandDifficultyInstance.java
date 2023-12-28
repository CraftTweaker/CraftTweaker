package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.world.*;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/DifficultyInstance")
@NativeTypeRegistration(value = DifficultyInstance.class, zenCodeName = "crafttweaker.api.world.DifficultyInstance")
public class ExpandDifficultyInstance {
    
    @ZenCodeType.Getter("difficulty")
    public static Difficulty getDifficulty(DifficultyInstance internal) {
        
        return internal.getDifficulty();
    }
    
    @ZenCodeType.Getter("effectiveDifficulty")
    public static float getEffectiveDifficulty(DifficultyInstance internal) {
        
        return internal.getEffectiveDifficulty();
    }
    
    @ZenCodeType.Getter("isHard")
    public static boolean isHard(DifficultyInstance internal) {
        
        return internal.isHard();
    }
    
    @ZenCodeType.Method
    public static boolean isHarderThan(DifficultyInstance internal, float difficulty) {
        
        return internal.isHarderThan(difficulty);
    }
    
    @ZenCodeType.Getter("specialMultiplier")
    public static float getSpecialMultiplier(DifficultyInstance internal) {
        
        return internal.getSpecialMultiplier();
    }
    
}
