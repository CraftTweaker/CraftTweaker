package com.blamejared.crafttweaker.natives.villager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
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
    public static String name(VillagerProfession internal) {
        
        return internal.name();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("requestedItems")
    public static Set<Item> requestedItems(VillagerProfession internal) {
        
        return internal.requestedItems();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("secondaryPoi")
    public static Set<Block> secondaryPoi(VillagerProfession internal) {
        
        return internal.secondaryPoi();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("workSound")
    @ZenCodeType.Nullable
    public static SoundEvent workSound(VillagerProfession internal) {
        
        return internal.workSound();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(VillagerProfession internal) {
        
        return Registry.VILLAGER_PROFESSION.getKey(internal);
    }
    
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(VillagerProfession internal) {
        
        return "<profession:" + Registry.VILLAGER_PROFESSION.getKey(internal) + ">";
    }
    
}
