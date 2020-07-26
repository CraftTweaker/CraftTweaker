package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.annotations.BracketDumper;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
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
    
    @BracketDumper("item")
    public static Collection<String> getItemBracketDump() {
        final HashSet<String> result = new HashSet<>();
        for(ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            result.add(String.format(Locale.ENGLISH, "<item:%s>", key));
        }
        return result;
    }
    
    @BracketDumper("recipetype")
    public static Collection<String> getRecipeTypeDump() {
        final HashSet<String> result = new HashSet<>();
        Registry.RECIPE_TYPE.keySet()
                .stream()
                .filter(rl -> !rl.toString().equals("crafttweaker:scripts"))
                .forEach(rl -> result.add(String.format(Locale.ENGLISH, "<recipetype:%s>", rl)));
        return result;
    }
    
    @BracketDumper("entitytype")
    public static Collection<String> getEntityTypeDump() {
        return ForgeRegistries.ENTITIES.getKeys()
                .stream()
                .map(key -> "<entitytype:" + key + ">")
                .collect(Collectors.toList());
    }
    
    @BracketDumper("entityclassification")
    public static Collection<String> getEntityClassificationDump() {
        return Arrays.stream(EntityClassification.values())
                .map(key -> "<entityclassification:" + key.name().toLowerCase() + ">")
                .collect(Collectors.toList());
    }
    
    @BracketDumper("directionaxis")
    public static Collection<String> getDirectionAxisDump() {
        return Arrays.stream(Direction.Axis.values())
                .map(key -> "<directionaxis:" + key + ">")
                .collect(Collectors.toList());
    }
    
    @BracketDumper("formatting")
    public static Collection<String> getTextFormattingDump() {
        return Arrays.stream(TextFormatting.values()).map(key -> "<formatting:" + key.getFriendlyName() + ">").collect(Collectors.toList());
    }
}
