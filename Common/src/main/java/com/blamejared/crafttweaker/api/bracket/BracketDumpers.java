package com.blamejared.crafttweaker.api.bracket;


import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.annotation.BracketDumper;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistry;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.material.ExpandMaterial;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityType;
import com.blamejared.crafttweaker.natives.entity.attribute.ExpandAttribute;
import com.blamejared.crafttweaker.natives.entity.effect.ExpandMobEffect;
import com.blamejared.crafttweaker.natives.item.ExpandCreativeModeTab;
import com.blamejared.crafttweaker.natives.item.alchemy.ExpandPotion;
import com.blamejared.crafttweaker.natives.item.enchantment.ExpandEnchantment;
import com.blamejared.crafttweaker.natives.villager.ExpandVillagerProfession;
import com.blamejared.crafttweaker.natives.world.damage.ExpandDamageSource;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.CreativeModeTab;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketDumpers")
@Document("vanilla/api/BracketDumpers")
public class BracketDumpers {
    
    @ZenCodeType.Method
    @BracketDumper("attribute")
    public static Collection<String> getAttributeDump() {
        
        return Services.REGISTRY.attributes().stream()
                .map(ExpandAttribute::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("block")
    public static Collection<String> getBlockDump() {
        
        return Services.REGISTRY.blocks()
                .stream()
                .map(ExpandBlock::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("blockmaterial")
    public static Collection<String> getBlockMaterialDump() {
        
        return ExpandMaterial.VANILLA_MATERIALS.values()
                .stream()
                .map(ExpandMaterial::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("constant")
    public static Collection<String> getConstantDump() {
        
        return CraftTweakerRegistry.collectBracketEnums();
    }
    
    @ZenCodeType.Method
    @BracketDumper("mobeffect")
    public static Collection<String> getEffectDump() {
        
        return Services.REGISTRY.mobEffects()
                .stream()
                .map(ExpandMobEffect::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("enchantment")
    public static Collection<String> getEnchantmentDump() {
        
        return Services.REGISTRY.enchantments()
                .stream()
                .map(ExpandEnchantment::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("entityType")
    public static Collection<String> getEntityTypeDump() {
        
        return Services.REGISTRY.entityTypes()
                .stream()
                .map(ExpandEntityType::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("item")
    public static Collection<String> getItemBracketDump() {
        
        return Services.REGISTRY.items()
                .keyStream()
                .map("<item:%s>"::formatted)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("potion")
    public static Collection<String> getPotionTypeDump() {
        
        return Services.REGISTRY.potions()
                .stream()
                .map(ExpandPotion::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("recipeType")
    public static Collection<String> getRecipeTypeDump() {
        
        return Services.REGISTRY.recipeTypes()
                .keyStream()
                .filter(rl -> !rl.toString().equals("crafttweaker:scripts"))
                .map(rl -> String.format(Locale.ENGLISH, "<recipetype:%s>", rl))
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("tag")
    public static Collection<String> getTagDump() {
        
        return CrTTagRegistry.INSTANCE.getAllManagers()
                .stream()
                .flatMap(tagManager -> tagManager.getAllTags().stream())
                .map(MCTag::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @ZenCodeType.Method
    @BracketDumper("profession")
    public static Collection<String> getProfessionDump() {
        
        return Services.REGISTRY.villagerProfessions()
                .stream()
                .map(ExpandVillagerProfession::getCommandString)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("damageSource")
    public static Collection<String> getDamageSourceDump() {
        
        return ExpandDamageSource.PRE_REGISTERED_DAMAGE_SOURCES.keySet()
                .stream()
                .map(name -> "<damagesource:" + name + ">")
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @BracketDumper("creativemodetab")
    public static Collection<String> getItemGroupBracketDump() {
        
        return Arrays.stream(CreativeModeTab.TABS)
                .map(ExpandCreativeModeTab::getCommandString)
                .collect(Collectors.toList());
    }
    
}
