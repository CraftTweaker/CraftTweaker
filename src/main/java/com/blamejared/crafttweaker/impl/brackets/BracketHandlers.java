package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.api.managers.*;
import com.blamejared.crafttweaker.impl.block.material.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.entity.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.managers.*;
import com.blamejared.crafttweaker.impl.potion.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import org.openzen.zencode.java.*;

import java.lang.reflect.*;
import java.util.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketHandlers")
@Document("vanilla/api/BracketHandlers")
public class BracketHandlers {
    
    /**
     * Gets the given {@link MCMaterial}. Throws an Exception if not found.
     *
     * @param tokens What you would write in the BEP call.
     * @return The found {@link MCMaterial}
     * @docParam tokens "earth"
     */
    @ZenCodeType.Method
    @BracketResolver("blockmaterial")
    public static MCMaterial getBlockMaterial(String tokens) {
        try {
            for(Field field : Material.class.getFields()) {
                if(field.getName().equalsIgnoreCase(tokens)) {
                    return new MCMaterial((Material) field.get(null), field.getName());
                }
            }
        } catch(IllegalAccessException e) {
            throw new IllegalArgumentException("Error gettig blockmaterial <blockmaterial:" + tokens + ">!", e);
        }
        
        try {
            CraftTweakerAPI.logInfo("Trying to get blockmaterial '%s' via ObfuscationHelper", tokens);
            final Field field = ObfuscationReflectionHelper.findField(Material.class, tokens.toUpperCase());
            return new MCMaterial((Material) field.get(null), tokens);
        } catch(Exception ignored) {
        }
    
        throw new IllegalArgumentException("Could not find blockmaterial <blockmaterial:" + tokens + ">!");
    }
    
    /**
     * Creates a Blockstate based on the given inputs.
     * Returns `null` if it cannot find the block, ignored invalid variants
     *
     * @param tokens The block's resource location and variants
     * @return The found BlockState
     * @docParam tokens "minecraft:acacia_planks"
     * @docParam tokens "minecraft:furnace:facing=north,lit=false"
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
     * Gets the direction Axis based on name. Throws an error if it can't find the direction Axis.
     *
     * @param tokens The direction Axis's resource location
     * @return The found direction Axis
     * @docParam tokens "x"
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
    
    /**
     * Gets the effect based on registry name. Throws an error if it can't find the effect.
     *
     * @param tokens The effect's resource location
     * @return The found effect
     * @docParam tokens "minecraft:haste"
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
    
    /**
     * Gets the entityType based on registry name. Logs an error and return `null` if it can't find the entityType.
     *
     * @param tokens The entityType's resource location
     * @return The found entityType
     * @docParam tokens "minecraft:pig"
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
    
    /**
     * Gets the item based on registry name. Throws an error if it can't find the item.
     *
     * @param tokens The item's resource location
     * @return The found item
     * @docParam tokens "minecraft:dirt"
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
     * Gets the recipeManager based on registry name. Throws an error if it can't find the recipeManager.
     * Throws an expcetion if the given recipeType is not found.
     * <p>
     * This will always return IRecipeManager.<br>
     * There is also a BEP for that but that works differently so it can't be automatically added to the docs here.
     * But the BEP looks the same as the other ones: `<recipetype:minecraft:crafting>`
     *
     * @param tokens The recipeManager's resource location
     * @return The found recipeManager
     * @docParam tokens "minecraft:crafting"
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
        final ResourceLocation key = new ResourceLocation(tokens);
        if(RecipeTypeBracketHandler.containsCustomManager(key)) {
            return RecipeTypeBracketHandler.getCustomManager(key);
        }
        
        Optional<IRecipeType<?>> value = Registry.RECIPE_TYPE.getValue(key);
        
        if(value.isPresent()) {
            return new RecipeManagerWrapper(value.get());
        } else {
            throw new IllegalArgumentException("Could not get RecipeType with name: <recipetype:" + tokens + ">! RecipeType does not appear to exist!");
        }
    }
    
    /**
     * Creates a Resource location based on the tokens.
     * Throws an error if the tokens are not a valid location.
     *
     * @param tokens The resource location
     * @return The location
     * @docParam tokens "minecraft:dirt"
     */
    @ZenCodeType.Method
    @BracketResolver("resource")
    public static MCResourceLocation getResourceLocation(String tokens) {
        return new MCResourceLocation(new ResourceLocation(tokens));
    }
    
    
    /**
     * Gets the entityClassification based on registry name. Logs an error and returns `null` if it can't find the entityClassification.
     *
     * @param tokens The entityClassification's resource location
     * @return The found entityClassification
     * @docParam tokens "monster"
     */
    @ZenCodeType.Method
    @BracketResolver("entityclassification")
    public static MCEntityClassification getEntityClassification(String tokens) {
        final int length = tokens.split(":").length;
        if(length == 0 || length > 1) {
            CraftTweakerAPI.logError("Could not get EntityClassification <entityclassification:%s>", tokens);
            return null;
        }
        if(Arrays.stream(EntityClassification.values())
                .anyMatch(entityClassification -> entityClassification.name()
                        .equalsIgnoreCase(tokens))) {
            CraftTweakerAPI.logError("Could not get EntityClassification <entityclassification:%s>", tokens);
            return null;
        }
        //Cannot be null since we checked containsKey
        return new MCEntityClassification(EntityClassification.valueOf(tokens.toUpperCase()));
    }
    
    
    /**
     * Gets the tag based on registry name. Will create an empty Tag if none is found.<br>
     * However, in such a case, you need to register the tag as its appropriate type
     *
     * @param tokens The tag's resource location
     * @return The found tag, or a newly created one
     * @docParam tokens "tag:minecraft:wool"
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
}
