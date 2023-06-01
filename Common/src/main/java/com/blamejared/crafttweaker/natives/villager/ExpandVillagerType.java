package com.blamejared.crafttweaker.natives.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/villager/VillagerType")
@NativeTypeRegistration(value = VillagerType.class, zenCodeName = "crafttweaker.api.villager.VillagerType")
@TaggableElement("minecraft:villager_type")
public class ExpandVillagerType {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(VillagerType internal) {
        
        return BuiltInRegistries.VILLAGER_TYPE.getKey(internal);
    }
    
}
