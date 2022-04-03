package com.blamejared.crafttweaker.natives.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;

@ZenRegister
@Document("vanilla/api/villager/VillagerProfession")
@NativeTypeRegistration(value = VillagerProfession.class, zenCodeName = "crafttweaker.api.villager.VillagerProfession")
@TaggableElement("minecraft:villager_profession")
public class ExpandVillagerProfession {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(VillagerProfession internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("requestedItems")
    public static Set<Item> getRequestedItems(VillagerProfession internal) {
        
        return internal.getRequestedItems();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("secondaryPoi")
    public static Set<Block> getSecondaryPoi(VillagerProfession internal) {
        
        return internal.getSecondaryPoi();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("workSound")
    @ZenCodeType.Nullable
    public static SoundEvent getWorkSound(VillagerProfession internal) {
        
        return internal.getWorkSound();
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(VillagerProfession internal) {
        
        return "<profession:" + Services.REGISTRY.getRegistryKey(internal) + ">";
    }
    
}
