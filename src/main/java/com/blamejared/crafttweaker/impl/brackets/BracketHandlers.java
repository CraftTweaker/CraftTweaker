package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.BracketDumper;
import com.blamejared.crafttweaker.api.annotations.BracketResolver;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.blocks.MCBlockState;
import com.blamejared.crafttweaker.impl.entity.MCEntityClassification;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import com.blamejared.crafttweaker.impl.potion.MCEffect;
import com.blamejared.crafttweaker.impl.potion.MCPotion;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketHandlers")
@Document("vanilla/api/BracketHandlers")
public class BracketHandlers {
    
    /**
     * Gets the item based on registry name. Throws an error if it can't find the item.
     * @param tokens The item's resource location
     * @docParam tokens "minecraft:dirt"
     * @return The found item
     */
    @BracketResolver("item")
    @ZenCodeType.Method
    public static IItemStack getItem(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Item BEP <item:%s> does not seem to be lower-cased!", tokens);
        
        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get item with name: <item:" + tokens + ">! Syntax is <item:modid:itemname>");
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(!ForgeRegistries.ITEMS.containsKey(key)) {
            throw new IllegalArgumentException("Could not get item with name: <item:" + tokens + ">! Item does not appear to exist!");
        }
        final ItemStack value = new ItemStack(ForgeRegistries.ITEMS.getValue(key));
        return new MCItemStack(value);
    }
    
    @BracketResolver("potion")
    @ZenCodeType.Method
    public static MCPotion getPotion(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Potion BEP <potion:%s> does not seem to be lower-cased!", tokens);
        
        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get potion with name: <potion:" + tokens + ">! Syntax is <potion:modid:potionname>");
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(!ForgeRegistries.POTIONS.containsKey(key)) {
            throw new IllegalArgumentException("Could not get potion with name: <potion:" + tokens + ">! Potion does not appear to exist!");
        }
        Potion potion = ForgeRegistries.POTION_TYPES.getValue(key);
        return new MCPotion(potion);
    }
    
    /**
     * Gets the effect based on registry name. Throws an error if it can't find the effect.
     * @param tokens The effect's resource location
     * @docParam tokens "minecraft:haste"
     * @return The found effect
     */
    @BracketResolver("effect")
    @ZenCodeType.Method
    public static MCEffect getEffect(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Effect BEP <effect:%s> does not seem to be lower-cased!", tokens);
        
        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get effect with name: <effect:" + tokens + ">! Syntax is <effect:modid:potionname>");
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(!ForgeRegistries.POTIONS.containsKey(key)) {
            throw new IllegalArgumentException("Could not get effect with name: <potion:" + tokens + ">! Effect does not appear to exist!");
        }
        Effect effect = ForgeRegistries.POTIONS.getValue(key);
        return new MCEffect(effect);
    }
    
    
    @BracketDumper("item")
    public static Collection<String> getItemBracketDump() {
        final HashSet<String> result = new HashSet<>();
        for(ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            result.add(String.format(Locale.ENGLISH, "<item:%s>", key));
        }
        return result;
    }
    
    /**
     * Gets the tag based on registry name. Will create an empty Tag if none is found.<br>
     * However, in such a case, you need to register the tag as its appropriate type
     * @param tokens The tag's resource location
     * @docParam tokens "tag:minecraft:wool"
     * @return The found tag, or a newly created one
     */
    @ZenCodeType.Method
    @BracketResolver("tag")
    public static MCTag getTag(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Tag BEP <tag:%s> does not seem to be lower-cased!", tokens);
        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get Tag with name: <tag:" + tokens + ">! Syntax is <tag:modid:tagname>");
        return new MCTag(new ResourceLocation(tokens));
    }
    
    /**
     * Gets the recipeManager based on registry name. Throws an error if it can't find the recipeManager.
     * Throws an expcetion if the given recipeType is not found.
     *
     * This will always return IRecipeManager.<br>
     * There is also a BEP for that but that works differently so it can't be automatically added to the docs here.
     * But the BEP looks the same as the other ones: `<recipetype:minecraft:crafting>`
     *
     * @param tokens The recipeManager's resource location
     * @docParam tokens "minecraft:crafting"
     * @return The found recipeManager
     */
    //@BracketResolver("recipetype")
    @ZenCodeType.Method
    public static IRecipeManager getRecipeManager(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("RecipeType BEP <recipetype:%s> does not seem to be lower-cased!", tokens);
        if(tokens.equalsIgnoreCase("crafttweaker:scripts")) {
            // This is bound to cause issues, like: <recipetype:crafttweaker:scripts.removeAll(); Best to just fix it now
            throw new IllegalArgumentException("Nice try, but there's no reason you need to access the <recipetype:crafttweaker:scripts> recipe manager!");
        }
        Optional<IRecipeType<?>> value = Registry.RECIPE_TYPE.getValue(new ResourceLocation(tokens));
        
        if(value.isPresent()) {
            return new RecipeManagerWrapper(value.get());
        } else {
            throw new IllegalArgumentException("Could not get RecipeType with name: <recipetype:" + tokens + ">! RecipeType does not appear to exist!");
        }
    }
    
    /**
     * Creates a Resource location based on the tokens.
     * Throws an error if the tokens are not a valid location.
     * @param tokens The resource location
     * @docParam tokens "minecraft:dirt"
     * @return The location
     */
    @ZenCodeType.Method
    @BracketResolver("resource")
    public static MCResourceLocation getResourceLocation(String tokens) {
        return new MCResourceLocation(new ResourceLocation(tokens));
    }
    
    @BracketDumper("recipetype")
    public static Collection<String> getRecipeTypeDump() {
        final HashSet<String> result = new HashSet<>();
        Registry.RECIPE_TYPE.keySet().stream().filter(rl -> !rl.toString().equals("crafttweaker:scripts")).forEach(rl -> result.add(String.format(Locale.ENGLISH, "<recipetype:%s>", rl)));
        return result;
    }
    
    /**
     * Creates a Blockstate based on the given inputs.
     * Returns `null` if it cannot find the block, ignored invalid variants
     * @param tokens The block's resource location and variants
     * @docParam tokens "minecraft:acacia_planks"
     * @docParam tokens "minecraft:furnace:facing=north,lit=false"
     * @return The found BlockState
     */
    @ZenCodeType.Method
    @BracketResolver("blockstate")
    public static MCBlockState getBlockState(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("BlockState BEP <blockstate:%s> does not seem to be lower-cased!", tokens);
        String[] split = tokens.split(":", 4);
        
        if(split.length > 1) {
            String blockName = split[0] + ":" + split[1];
            String properties = split.length > 2 ? split[2] : "";
            if(!ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(blockName))) {
                CraftTweakerAPI.logThrowing("Error creating BlockState!", new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">! The block does not appear to exist!"));
            } else {
                return getBlockState(blockName, properties);
            }
        }
        CraftTweakerAPI.logThrowing("Error creating BlockState!", new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">!"));
        return null;
    }
    
    public static MCBlockState getBlockState(String name, String properties) {
        
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
        if(block == null) {
            return null;
        }
        
        MCBlockState blockState = new MCBlockState(block.getDefaultState());
        if(properties != null && !properties.isEmpty()) {
            for(String propertyPair : properties.split(",")) {
                String[] splitPair = propertyPair.split("=");
                if(splitPair.length != 2) {
                    CraftTweakerAPI.logWarning("Invalid blockstate property format '" + propertyPair + "'. Using default property value.");
                    continue;
                }
                blockState = blockState.withProperty(splitPair[0], splitPair[1]);
            }
        }
        
        return blockState;
    }
    
    /**
     * Gets the entityType based on registry name. Logs an error and return `null` if it can't find the entityType.
     * @param tokens The entityType's resource location
     * @docParam tokens "minecraft:pig"
     * @return The found entityType
     */
    @ZenCodeType.Method
    @BracketResolver("entitytype")
    public static MCEntityType getEntityType(String tokens) {
        final int length = tokens.split(":").length;
        if(length == 0 || length > 2) {
            CraftTweakerAPI.logError("Could not get entitytype <entityType:%s>", tokens);
            return null;
        }
        final ResourceLocation resourceLocation = new ResourceLocation(tokens);
        if(!ForgeRegistries.ENTITIES.containsKey(resourceLocation)) {
            CraftTweakerAPI.logError("Could not get entitytype <entityType:%s>", tokens);
            return null;
        }
        
        //Cannot be null since we checked containsKey
        //noinspection ConstantConditions
        return new MCEntityType(ForgeRegistries.ENTITIES.getValue(resourceLocation));
    }
    
    @BracketDumper("entitytype")
    public static Collection<String> getEntityTypeDump() {
        return ForgeRegistries.ENTITIES.getKeys().stream().map(key -> "<entitytype:" + key + ">").collect(Collectors.toList());
    }
    
    /**
     * Gets the entityClassification based on registry name. Logs an error and returns `null` if it can't find the entityClassification.
     * @param tokens The entityClassification's resource location
     * @docParam tokens "monster"
     * @return The found entityClassification
     */
    @ZenCodeType.Method
    @BracketResolver("entityclassification")
    public static MCEntityClassification getEntityClassification(String tokens) {
        final int length = tokens.split(":").length;
        if(length == 0 || length > 1) {
            CraftTweakerAPI.logError("Could not get EntityClassification <entityclassification:%s>", tokens);
            return null;
        }
        if(Arrays.stream(EntityClassification.values()).anyMatch(entityClassification -> entityClassification.name().equalsIgnoreCase(tokens))) {
            CraftTweakerAPI.logError("Could not get EntityClassification <entityclassification:%s>", tokens);
            return null;
        }
        //Cannot be null since we checked containsKey
        return new MCEntityClassification(EntityClassification.valueOf(tokens.toUpperCase()));
    }
    
    @BracketDumper("entityclassification")
    public static Collection<String> getEntityClassificationDump() {
        return Arrays.stream(EntityClassification.values()).map(key -> "<entityclassification:" + key.name().toLowerCase() + ">").collect(Collectors.toList());
    }
    
    /**
     * Gets the direction Axis based on name. Throws an error if it can't find the direction Axis.
     * @param tokens The direction Axis's resource location
     * @docParam tokens "x"
     * @return The found direction Axis
     */
    @ZenCodeType.Method
    @BracketResolver("directionaxis")
    public static MCDirectionAxis getDirectionAxis(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("DirectionAxis BEP <directionaxis:%s> does not seem to be lower-cased!", tokens);
        
        final String[] split = tokens.split(":");
        if(split.length != 1)
            throw new IllegalArgumentException("Could not get axis with name: <directionaxis:" + tokens + ">! Syntax is <directionaxis:axis>");
        
        if(Direction.Axis.byName(split[0]) != null) {
            throw new IllegalArgumentException("Could not get axis with name: <directionaxis:" + tokens + ">! Axis does not appear to exist!");
        }
        return MCDirectionAxis.getAxis(Direction.Axis.byName(split[0]));
    }
    
    @BracketDumper("directionaxis")
    public static Collection<String> getDirectionAxisDump() {
        return Arrays.stream(Direction.Axis.values()).map(key -> "<directionaxis:" + key + ">").collect(Collectors.toList());
    }
    
}
