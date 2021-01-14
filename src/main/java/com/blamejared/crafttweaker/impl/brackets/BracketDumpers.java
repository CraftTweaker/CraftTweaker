package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.annotations.BracketDumper;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.registry.CrTTagRegistry;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlock;
import com.blamejared.crafttweaker.impl_native.potion.ExpandEffect;
import com.blamejared.crafttweaker.impl_native.potion.ExpandPotion;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketDumpers")
public class BracketDumpers {
    
    @BracketDumper("block")
    public static Collection<String> getBlockDump() {
        return ForgeRegistries.BLOCKS.getValues()
                .stream()
                .map(ExpandBlock::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @BracketDumper("directionAxis")
    public static Collection<String> getDirectionAxisDump() {
        return Arrays.stream(Direction.Axis.values())
                .map(key -> "<directionaxis:" + key + ">")
                .collect(Collectors.toList());
    }
    
    @BracketDumper("effect")
    public static Collection<String> getEffectDump() {
        return ForgeRegistries.POTIONS.getValues()
                .stream()
                .map(ExpandEffect::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @BracketDumper("entityType")
    public static Collection<String> getEntityTypeDump() {
        return ForgeRegistries.ENTITIES.getKeys()
                .stream()
                .map(key -> "<entitytype:" + key + ">")
                .collect(Collectors.toList());
    }
    
    @BracketDumper("fluid")
    public static Collection<String> getFluidStackDump() {
        return ForgeRegistries.FLUIDS.getValues()
                .stream()
                .map(fluid -> new MCFluidStack(new FluidStack(fluid, 1)).getCommandString())
                .collect(Collectors.toList());
    }
    
    @BracketDumper("entityClassification")
    public static Collection<String> getEntityClassificationDump() {
        return Arrays.stream(EntityClassification.values())
                .map(key -> "<entityclassification:" + key.name().toLowerCase() + ">")
                .collect(Collectors.toList());
    }
    
    @BracketDumper("formatting")
    public static Collection<String> getTextFormattingDump() {
        return Arrays.stream(TextFormatting.values())
                .map(key -> "<formatting:" + key.getFriendlyName() + ">")
                .collect(Collectors.toList());
    }
    
    @BracketDumper("item")
    public static Collection<String> getItemBracketDump() {
        final HashSet<String> result = new HashSet<>();
        for(ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            result.add(String.format(Locale.ENGLISH, "<item:%s>", key));
        }
        return result;
    }
    
    @BracketDumper("potion")
    public static Collection<String> getPotionTypeDump() {
        return ForgeRegistries.POTION_TYPES.getValues()
                .stream()
                .map(ExpandPotion::getCommandString)
                .collect(Collectors.toList());
    }
    
    @BracketDumper("recipeType")
    public static Collection<String> getRecipeTypeDump() {
        return Registry.RECIPE_TYPE.keySet()
                .stream()
                .filter(rl -> !rl.toString().equals("crafttweaker:scripts"))
                .map(rl -> String.format(Locale.ENGLISH, "<recipetype:%s>", rl))
                .collect(Collectors.toList());
    }
    
    @BracketDumper("tag")
    public static Collection<String> getTagDump() {
        return CrTTagRegistry.instance.getAllManagers()
                .stream()
                .flatMap(tagManager -> tagManager.getAllTags().stream())
                .map(MCTag::getCommandString)
                .collect(Collectors.toSet());
    }
    
    @BracketDumper("profession")
    public static Collection<String> getProfessionDump() {
        return ForgeRegistries.PROFESSIONS.getValues()
                .stream()
                .map(VillagerProfession::toString)
                .collect(Collectors.toList());
    }
    
}
