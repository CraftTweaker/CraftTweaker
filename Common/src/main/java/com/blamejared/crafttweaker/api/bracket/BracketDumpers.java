package com.blamejared.crafttweaker.api.bracket;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.material.ExpandMaterial;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityType;
import com.blamejared.crafttweaker.natives.entity.attribute.ExpandAttribute;
import com.blamejared.crafttweaker.natives.entity.effect.ExpandMobEffect;
import com.blamejared.crafttweaker.natives.item.alchemy.ExpandPotion;
import com.blamejared.crafttweaker.natives.item.enchantment.ExpandEnchantment;
import com.blamejared.crafttweaker.natives.sound.ExpandSoundEvent;
import com.blamejared.crafttweaker.natives.villager.ExpandVillagerProfession;
import com.blamejared.crafttweaker.natives.world.damage.ExpandDamageSource;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketDumpers")
@Document("vanilla/api/BracketDumpers")
public class BracketDumpers {
    
    @ZenCodeType.Method
    @BracketDumper("attribute")
    public static Collection<String> getAttributeDump() {
        
        return BuiltInRegistries.ATTRIBUTE
                .stream()
                .map(ExpandAttribute::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("block")
    public static Collection<String> getBlockDump() {
        
        return BuiltInRegistries.BLOCK
                .stream()
                .map(ExpandBlock::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.StaticExpansionMethod
    @BracketDumper("fluid")
    public static Collection<String> getFluidStackDump() {
        
        return BuiltInRegistries.FLUID.stream()
                .map(fluid -> IFluidStack.of(fluid, 1).getCommandString())
                .collect(Collectors.toList());
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
        
        return BuiltInRegistries.MOB_EFFECT
                .stream()
                .map(ExpandMobEffect::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("enchantment")
    public static Collection<String> getEnchantmentDump() {
        
        return BuiltInRegistries.ENCHANTMENT
                .stream()
                .map(ExpandEnchantment::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("entitytype")
    public static Collection<String> getEntityTypeDump() {
        
        return BuiltInRegistries.ENTITY_TYPE
                .stream()
                .map(ExpandEntityType::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("item")
    public static Collection<String> getItemBracketDump() {
        
        return BuiltInRegistries.ITEM
                .stream()
                .map(Item::getDefaultInstance)
                .map(ItemStackUtil::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("potion")
    public static Collection<String> getPotionTypeDump() {
        
        return BuiltInRegistries.POTION
                .stream()
                .map(ExpandPotion::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("profession")
    public static Collection<String> getProfessionDump() {
        
        return BuiltInRegistries.VILLAGER_PROFESSION
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
    @BracketDumper("soundevent")
    public static Collection<String> getSoundEventDump() {
        
        return BuiltInRegistries.SOUND_EVENT
                .stream()
                .map(ExpandSoundEvent::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("targetingstrategy")
    public static Collection<String> getTargetingStrategyDump() {
        
        return CraftTweakerAPI.getRegistry()
                .getReplacerRegistry()
                .allStrategyNames()
                .stream()
                .map("<targetingstrategy:%s>"::formatted)
                .collect(Collectors.toList());
    }
    
}
