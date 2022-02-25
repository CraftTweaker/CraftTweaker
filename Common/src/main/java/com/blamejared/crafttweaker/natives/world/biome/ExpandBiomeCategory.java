package com.blamejared.crafttweaker.natives.world.biome;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.biome.Biome;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/biome/BiomeCategory")
@NativeTypeRegistration(value = Biome.BiomeCategory.class, zenCodeName = "crafttweaker.api.world.biome.BiomeCategory")
@BracketEnum("minecraft:world/biome/category")
public class ExpandBiomeCategory {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(Biome.BiomeCategory internal) {
        
        return internal.getName();
    }
    
}
