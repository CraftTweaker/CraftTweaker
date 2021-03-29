package com.blamejared.crafttweaker.impl.brackets;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.BracketResolver;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.managers.RecipeManagerWrapper;
import com.blamejared.crafttweaker.impl.util.text.MCTextFormatting;
import com.blamejared.crafttweaker.impl_native.block.material.ExpandMaterial;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlockState;
import com.blamejared.crafttweaker.impl_native.util.ExpandDamageSource;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * This class contains the "simple" Bracket handlers from CraftTweaker.
 * However, some Bracket handlers, like for recipeTypes, tags, tagManagers, won't be shown here as
 * they use a different internal structure.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.BracketHandlers")
@Document("vanilla/api/BracketHandlers")
public class BracketHandlers {

    /**
     * Gets the give {@link Block}. Throws an Exception if not found
     *
     * @param tokens What you would write in the BEP call.
     * @return The found {@link Block}
     * @docParam tokens "minecraft:dirt"
     */
    @ZenCodeType.Method
    @BracketResolver("block")
    public static Block getBlock(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Block BEP <block:%s> does not seem to be lower-cased!", tokens);

        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get block with name: <block:" + tokens + ">! Syntax is <block:modid:itemname>");
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(!ForgeRegistries.BLOCKS.containsKey(key)) {
            throw new IllegalArgumentException("Could not get block with name: <block:" + tokens + ">! Block does not appear to exist!");
        }

        return ForgeRegistries.BLOCKS.getValue(key);
    }

    /**
     * Gets the given {@link Material}. Throws an Exception if not found.
     *
     * @param tokens What you would write in the BEP call.
     * @return The found {@link Material}
     * @docParam tokens "earth"
     */
    @ZenCodeType.Method
    @BracketResolver("blockmaterial")
    public static Material getBlockMaterial(String tokens) {
        final Material material = ExpandMaterial.tryGet(tokens);
        if(material != null) {
            return material;
        }

        try {
            for(Field field : Material.class.getFields()) {
                if(field.getName().equalsIgnoreCase(tokens)) {
                    return (Material) field.get(null);
                }
            }
        } catch(IllegalAccessException e) {
            throw new IllegalArgumentException("Error getting blockmaterial <blockmaterial:" + tokens + ">!", e);
        }

        try {
            final Field field = ObfuscationReflectionHelper.findField(Material.class, tokens.toUpperCase());
            return (Material) field.get(null);
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
    public static BlockState getBlockState(String tokens) {
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

    public static BlockState getBlockState(String name, String properties) {
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
        if(block == null) {
            return null;
        }

        BlockState blockState = block.getDefaultState();
        if(properties != null && !properties.isEmpty()) {
            for(String propertyPair : properties.split(",")) {
                String[] splitPair = propertyPair.split("=");
                if(splitPair.length != 2) {
                    CraftTweakerAPI.logWarning("Invalid blockstate property format '" + propertyPair + "'. Using default property value.");
                    continue;
                }
                blockState = ExpandBlockState.withProperty(blockState, splitPair[0], splitPair[1]);
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
    public static Direction.Axis getDirectionAxis(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("DirectionAxis BEP <directionaxis:%s> does not seem to be lower-cased!", tokens);

        if(tokens.contains(":"))
            throw new IllegalArgumentException("Could not get axis with name: <directionaxis:" + tokens + ">! Syntax is <directionaxis:axis>");

        final Direction.Axis axis = Direction.Axis.byName(tokens);
        if(axis == null) {
            throw new IllegalArgumentException("Could not get axis with name: <directionaxis:" + tokens + ">! Axis does not appear to exist!");
        }
        return axis;
    }

    /**
     * Gets the equipment slot type based on name. Throws an error if it can't find the equipment slot type.
     * @param tokens The equipment slot type's name
     * @return The found equipment slot type
     * @docParam tokens "mainhand"
     */
    @ZenCodeType.Method
    @BracketResolver("equipmentslottype")
    public static EquipmentSlotType getEquipmentSlotType(String tokens) {
        if (!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.logWarning("EquipmentSlotType BEP <equipmentslottype:%s> does not seem to be lower-cased!", tokens);
        }
        return EquipmentSlotType.fromString(tokens);
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
    public static Effect getEffect(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Effect BEP <effect:%s> does not seem to be lower-cased!", tokens);

        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get effect with name: <effect:" + tokens + ">! Syntax is <effect:modid:potionname>");
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(!ForgeRegistries.POTIONS.containsKey(key)) {
            throw new IllegalArgumentException("Could not get effect with name: <potion:" + tokens + ">! Effect does not appear to exist!");
        }
        return ForgeRegistries.POTIONS.getValue(key);
    }

    /**
     * Gets the enchantment based on registry name. Throws an error if it can't find the enchantment.
     *
     * @param tokens The enchantment's registry name
     * @return The found enchantment
     * @docParam tokens "minecraft:riptide"
     */
    @ZenCodeType.Method
    @BracketResolver("enchantment")
    public static Enchantment getEnchantment(String tokens) {
        if (!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.logWarning("Enchantment BEP <enchantment:%s> does not seem to be lower-case!", tokens);
        }

        final String[] split = tokens.split(":");
        if (split.length != 2) {
            throw new IllegalArgumentException("Could not get enchantment '" + tokens + "': not a valid bracket handler, syntax is <enchantment:modid:name>");
        }

        final ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if (!ForgeRegistries.ENCHANTMENTS.containsKey(key)) {
            throw new IllegalArgumentException("Could not get enchantment '" + tokens + "': the enchantment does not appear to exist");
        }

        return ForgeRegistries.ENCHANTMENTS.getValue(key);
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
    public static EntityClassification getEntityClassification(String tokens) {
        final int length = tokens.split(":").length;
        if(length == 0 || length > 1) {
            CraftTweakerAPI.logError("Could not get EntityClassification <entityclassification:%s>", tokens);
            return null;
        }
        if(Arrays.stream(EntityClassification.values())
                .noneMatch(entityClassification -> entityClassification.name()
                        .equalsIgnoreCase(tokens))) {
            CraftTweakerAPI.logError("Could not get EntityClassification <entityclassification:%s>", tokens);
            return null;
        }
        //Cannot be null since we checked containsKey
        return EntityClassification.valueOf(tokens.toUpperCase());
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
            throw new IllegalArgumentException("Could not get entitytype <entitytype:" + tokens + ">");
        }
        final ResourceLocation resourceLocation = new ResourceLocation(tokens);
        if(!ForgeRegistries.ENTITIES.containsKey(resourceLocation)) {
            throw new IllegalArgumentException("Could not get entitytype <entitytype:" + tokens + ">");
        }

        return new MCEntityType(Objects.requireNonNull(ForgeRegistries.ENTITIES.getValue(resourceLocation)));
    }

    /**
     * Gets the fluid Stack based on registry name. Throws an error if it can't find the fluid.
     *
     * @param tokens The Fluid's resource location
     * @return A stack of the liquid with amount == 1mb
     * @docParam tokens "minecraft:water"
     */
    @ZenCodeType.Method
    @BracketResolver("fluid")
    public static IFluidStack getFluidStack(String tokens) {
        final ResourceLocation resourceLocation = ResourceLocation.tryCreate(tokens);
        if(resourceLocation == null) {
            throw new IllegalArgumentException("Could not get fluid for <fluid:" + tokens + ">. Syntax is <fluid:modid:fluidname>");
        }

        if(!ForgeRegistries.FLUIDS.containsKey(resourceLocation)) {
            throw new IllegalArgumentException("Could not get fluid for <fluid:" + tokens + ">. Fluid does not appear to exist!");
        }

        //We know it's not null, because we checked with containsKey
        //noinspection ConstantConditions
        return new MCFluidStack(new FluidStack(ForgeRegistries.FLUIDS.getValue(resourceLocation), 1));
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
    public static Potion getPotion(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Potion BEP <potion:%s> does not seem to be lower-cased!", tokens);

        final String[] split = tokens.split(":");
        if(split.length != 2)
            throw new IllegalArgumentException("Could not get potion with name: <potion:" + tokens + ">! Syntax is <potion:modid:potionname>");
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        if(!ForgeRegistries.POTION_TYPES.containsKey(key)) {
            throw new IllegalArgumentException("Could not get potion with name: <potion:" + tokens + ">! Potion does not appear to exist!");
        }
        return ForgeRegistries.POTION_TYPES.getValue(key);
    }

    /**
     * Gets the recipeManager based on registry name. Throws an error if it can't find the recipeManager.
     * Throws an exception if the given recipeType is not found.
     * <p>
     * This will always return IRecipeManager.<br>
     * There is also a BEP for that but that works differently so it can't be automatically added to the docs here.
     * But the BEP looks the same as the other ones: `<recipetype:minecraft:crafting>`
     *
     * @param tokens The recipeManager's resource location
     * @return The found recipeManager
     * @docParam tokens "minecraft:crafting"
     */
    @ZenCodeType.Method
    public static IRecipeManager getRecipeManager(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("RecipeType BEP <recipetype:%s> does not seem to be lower-cased!", tokens);
        if(tokens.equalsIgnoreCase("crafttweaker:scripts")) {
            // This is bound to cause issues, like: <recipetype:crafttweaker:scripts>.removeAll(); Best to just fix it now
            throw new IllegalArgumentException("Nice try, but there's no reason you need to access the <recipetype:crafttweaker:scripts> recipe manager!");
        }
        final ResourceLocation key = new ResourceLocation(tokens);
        if(RecipeTypeBracketHandler.containsCustomManager(key)) {
            return RecipeTypeBracketHandler.getCustomManager(key);
        }

        IRecipeType<?> value = Registry.RECIPE_TYPE.getOrDefault(key);

        if(value != null) {
            return new RecipeManagerWrapper(value);
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
    public static ResourceLocation getResourceLocation(String tokens) {
        return new ResourceLocation(tokens);
    }

    @ZenCodeType.Method
    @BracketResolver("formatting")
    public static MCTextFormatting getTextFormatting(String tokens) {
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens))
            CraftTweakerAPI.logWarning("Formatting BEP <formatting:%s> does not seem to be lower-cased!", tokens);

        final String[] split = tokens.split(":");
        if(split.length != 1)
            throw new IllegalArgumentException("Could not get format with name: <formatting:" + tokens + ">! Syntax is <formatting:format>");

        if(TextFormatting.getValueByName(split[0]) == null) {
            throw new IllegalArgumentException("Could not get format with name: <formatting:" + tokens + ">! format does not appear to exist!");
        }
        return new MCTextFormatting(TextFormatting.getValueByName(split[0]));
    }

    /**
     * Gets the villager profession based on registry name. Logs an error and return `null` if it can't find the profession.
     *
     * @param tokens The profession's resource location
     * @return The found profession
     * @docParam tokens "minecraft:armorer"
     */
    @ZenCodeType.Method
    @BracketResolver("profession")
    public static VillagerProfession getProfession(String tokens) {
        final int length = tokens.split(":").length;
        if(length == 0 || length > 2) {
            throw new IllegalArgumentException("Could not get profession <profession:" + tokens + ">");
        }
        final ResourceLocation resourceLocation = new ResourceLocation(tokens);
        if(!ForgeRegistries.PROFESSIONS.containsKey(resourceLocation)) {
            throw new IllegalArgumentException("Could not get profession <profession:" + tokens + ">");
        }

        return ForgeRegistries.PROFESSIONS.getValue(resourceLocation);
    }

    /**
     * Gets a Biome based on the tokens.
     * Throws an error if it can't get the biome
     *
     * @param tokens The biome's resource location
     *
     * @return The found biome
     *
     * @docParam tokens "minecraft:plain"
     */
    @ZenCodeType.Method
    @BracketResolver("biome")
    public static Biome getBiome(String tokens) {

        final int length = tokens.split(":").length;
        if(length != 2) {
            throw new IllegalArgumentException("Could not get biome with name: <biome:" + tokens + ">! Syntax is <biome:modid:biomeName>");
        }
        final ResourceLocation resourceLocation = new ResourceLocation(tokens);
        if(!ForgeRegistries.BIOMES.containsKey(resourceLocation)) {
            throw new IllegalArgumentException("Could not get biome <biome:" + tokens + ">");
        }
        return ForgeRegistries.BIOMES.getValue(resourceLocation);
    }

    /**
     * Gets a damage source based on type.
     * If the damage source is not pre-registered, it will create a new one with the given name
     *
     * @param tokens the damage sources' type
     *
     * @return The found pre-registered damage source or a new one
     *
     * @docParam tokens "magic"
     */
    @ZenCodeType.Method
    @BracketResolver("damagesource")
    public static DamageSource getDamageSource(String tokens) {
        return ExpandDamageSource.PRE_REGISTERED_DAMAGE_SOURCES.getOrDefault(tokens, new DamageSource(tokens));
    }
}
