package com.blamejared.crafttweaker.api.bracket;


import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.material.ExpandMaterial;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityType;
import com.blamejared.crafttweaker.natives.entity.attribute.ExpandAttribute;
import com.blamejared.crafttweaker.natives.entity.effect.ExpandMobEffect;
import com.blamejared.crafttweaker.natives.item.ExpandCreativeModeTab;
import com.blamejared.crafttweaker.natives.item.alchemy.ExpandPotion;
import com.blamejared.crafttweaker.natives.item.enchantment.ExpandEnchantment;
import com.blamejared.crafttweaker.natives.sound.ExpandSoundEvent;
import com.blamejared.crafttweaker.natives.villager.ExpandVillagerProfession;
import com.blamejared.crafttweaker.natives.world.damage.ExpandDamageSource;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketDumpers")
@Document("vanilla/api/BracketDumpers")
public class BracketDumpers {
    
    @ZenCodeType.Method
    @BracketDumper("attribute")
    public static Collection<String> getAttributeDump() {
        
        return Registry.ATTRIBUTE
                .stream()
                .map(ExpandAttribute::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("block")
    public static Collection<String> getBlockDump() {
        
        return Registry.BLOCK
                .stream()
                .map(ExpandBlock::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("material")
    public static Collection<String> getMaterialDump() {
        
        return ExpandMaterial.VANILLA_MATERIALS.values()
                .stream()
                .map(ExpandMaterial::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("mobeffect")
    public static Collection<String> getEffectDump() {
        
        return Registry.MOB_EFFECT
                .stream()
                .map(ExpandMobEffect::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("enchantment")
    public static Collection<String> getEnchantmentDump() {
        
        return Registry.ENCHANTMENT
                .stream()
                .map(ExpandEnchantment::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("entitytype")
    public static Collection<String> getEntityTypeDump() {
        
        return Registry.ENTITY_TYPE
                .stream()
                .map(ExpandEntityType::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("item")
    public static Collection<String> getItemBracketDump() {
        
        return Registry.ITEM
                .stream()
                .map(Item::getDefaultInstance)
                .map(ItemStackUtil::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("potion")
    public static Collection<String> getPotionTypeDump() {
        
        return Registry.POTION
                .stream()
                .map(ExpandPotion::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("profession")
    public static Collection<String> getProfessionDump() {
        
        return Registry.VILLAGER_PROFESSION
                .stream()
                .map(ExpandVillagerProfession::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("damagesource")
    public static Collection<String> getDamageSourceDump() {
        
        return ExpandDamageSource.PRE_REGISTERED_DAMAGE_SOURCES.keySet()
                .stream()
                .map("<damagesource:%s>"::formatted)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("creativemodetab")
    public static Collection<String> getCreativeModeTabBracketDump() {
        
        return Arrays.stream(CreativeModeTab.TABS)
                .map(ExpandCreativeModeTab::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("soundevent")
    public static Collection<String> getSoundEventDump() {
        
        return Registry.SOUND_EVENT
                .stream()
                .map(ExpandSoundEvent::getCommandString)
                .collect(Collectors.toList());
    }
    
}
