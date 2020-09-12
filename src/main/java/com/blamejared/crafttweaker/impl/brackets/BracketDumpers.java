package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.fluid.*;
import com.blamejared.crafttweaker.impl.potion.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.registries.*;
import org.openzen.zencode.java.*;

import java.util.*;
import java.util.stream.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketDumpers")
public class BracketDumpers {
    
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
                .map(effect -> new MCEffect(effect).getCommandString())
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
                .map(potionType -> new MCPotion(potionType).getCommandString())
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
}
