package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import com.blamejared.crafttweaker.natives.block.material.ExpandMaterial;
import com.blamejared.crafttweaker.natives.world.damage.ExpandDamageSource;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

/**
 * This class contains the "simple" Bracket handlers from CraftTweaker.
 * However, some Bracket handlers, like for recipeTypes, tags, tagManagers, won't be shown here as
 * they use a different internal structure.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.BracketHandlers")
@Document("vanilla/api/BracketHandlers")
public class BracketHandlers {
    
    @ZenCodeType.Method
    @BracketResolver("attribute")
    public static Attribute getAttribute(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.LOGGER.warn("Attribute BEP <attribute:{}> does not seem to be lower-cased!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            throw new IllegalArgumentException("Could not get attribute with name: <attribute:" + tokens + ">! Syntax is <attribute:modid:name>");
        }
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        
        return Services.REGISTRY.attributes().getOptional(key)
                .orElseThrow(() -> new IllegalArgumentException("Could not get attribute with name: <attribute:" + tokens + ">! Attribute does not appear to exist!"));
    }
    
    /**
     * Gets the give {@link Block}. Throws an Exception if not found
     *
     * @param tokens What you would write in the BEP call.
     *
     * @return The found {@link Block}
     *
     * @docParam tokens "minecraft:dirt"
     */
    @ZenCodeType.Method
    @BracketResolver("block")
    public static Block getBlock(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.LOGGER.warn("Block BEP <block:{}> does not seem to be lower-cased!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            throw new IllegalArgumentException("Could not get block with name: <block:" + tokens + ">! Syntax is <block:modid:itemname>");
        }
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        return Services.REGISTRY.blocks().getOptional(key)
                .orElseThrow(() -> new IllegalArgumentException("Could not get block with name: <block:" + tokens + ">! Block does not appear to exist!"));
    }
    
    /**
     * Gets the given {@link Material}. Throws an Exception if not found.
     *
     * @param tokens What you would write in the BEP call.
     *
     * @return The found {@link Material}
     *
     * @docParam tokens "earth"
     */
    @ZenCodeType.Method
    @BracketResolver("material")
    public static Material getMaterial(String tokens) {
        
        // 1.16 did look at the Material class to see its fields,
        // but we can just add a test to make sure that ExpandMaterial.VANILLA_MATERIALS always contains the most upto date values
        return ExpandMaterial.getOptionalMaterial(tokens)
                .orElseThrow(() -> new IllegalArgumentException("Could not find material <material:" + tokens + ">!"));
    }
    
    /**
     * Creates a Blockstate based on the given inputs.
     * Returns `null` if it cannot find the block, ignored invalid variants
     *
     * @param tokens The block's resource location and variants
     *
     * @return The found BlockState
     *
     * @docParam tokens "minecraft:acacia_planks"
     * @docParam tokens "minecraft:furnace:facing=north,lit=false"
     */
    @ZenCodeType.Method
    @BracketResolver("blockstate")
    public static BlockState getBlockState(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.LOGGER.warn("BlockState BEP <blockstate:{}> does not seem to be lower-cased!", tokens);
        }
        String[] split = tokens.split(":", 4);
        
        if(split.length > 1) {
            String blockName = split[0] + ":" + split[1];
            String properties = split.length > 2 ? split[2] : "";
            
            Optional<Block> found = Services.REGISTRY.blocks().getOptional(new ResourceLocation(blockName));
            if(found.isEmpty()) {
                CraftTweakerAPI.LOGGER.error("Error creating BlockState!", new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">! The block does not appear to exist!"));
            } else {
                return getBlockState(found.get(), blockName, properties);
            }
        }
        CraftTweakerAPI.LOGGER.error("Error creating BlockState!", new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">!"));
        return null;
    }
    
    public static BlockState getBlockState(String name, String properties) {
        
        return getBlockState(Services.REGISTRY.blocks().get(new ResourceLocation(name)), name, properties);
    }
    
    public static BlockState getBlockState(Block block, String name, String properties) {
        
        BlockState blockState = block.defaultBlockState();
        if(properties != null && !properties.isEmpty()) {
            for(String propertyPair : properties.split(",")) {
                String[] splitPair = propertyPair.split("=");
                if(splitPair.length != 2) {
                    CraftTweakerAPI.LOGGER.warn("Invalid blockstate property format '{}'. Using default property value.", propertyPair);
                    continue;
                }
                blockState = ExpandBlockState.withProperty(blockState, splitPair[0], splitPair[1]);
            }
        }
        
        return blockState;
    }
    
    /**
     * Gets the mobeffect based on registry name. Throws an error if it can't find the mobeffect.
     *
     * @param tokens The mobeffect's resource location
     *
     * @return The found mobeffect
     *
     * @docParam tokens "minecraft:haste"
     */
    @BracketResolver("mobeffect")
    @ZenCodeType.Method
    public static MobEffect getMobEffect(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.LOGGER.warn("MobEffect BEP <mobeffect:{}> does not seem to be lower-cased!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            throw new IllegalArgumentException("Could not get effect with name: <mobeffect:" + tokens + ">! Syntax is <effect:modid:mobeffect>");
        }
        return Services.REGISTRY.mobEffects().getOptional(new ResourceLocation(split[0], split[1]))
                .orElseThrow(() -> new IllegalArgumentException("Could not get effect with name: <mobeffect:" + tokens + ">! Effect does not appear to exist!"));
    }
    
    /**
     * Gets the enchantment based on registry name. Throws an error if it can't find the enchantment.
     *
     * @param tokens The enchantment's registry name
     *
     * @return The found enchantment
     *
     * @docParam tokens "minecraft:riptide"
     */
    @ZenCodeType.Method
    @BracketResolver("enchantment")
    public static Enchantment getEnchantment(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.LOGGER.warn("Enchantment BEP <enchantment:{}> does not seem to be lower-case!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            throw new IllegalArgumentException("Could not get enchantment '" + tokens + "': not a valid bracket handler, syntax is <enchantment:modid:name>");
        }
        
        final ResourceLocation key = new ResourceLocation(split[0], split[1]);
        Optional<Enchantment> found = Services.REGISTRY.enchantments().getOptional(key);
        if(found.isEmpty()) {
            throw new IllegalArgumentException("Could not get enchantment '" + tokens + "': the enchantment does not appear to exist");
        }
        
        return found.get();
    }
    
    /**
     * Gets the entityType based on registry name. Throws an exception if it can't find the entityType.
     *
     * @param tokens The entityType's resource location
     *
     * @return The found entityType
     *
     * @docParam tokens "minecraft:pig"
     */
    @ZenCodeType.Method
    @BracketResolver("entitytype")
    public static EntityType getEntityType(String tokens) {
        
        final int length = tokens.split(":").length;
        if(length == 0 || length > 2) {
            throw new IllegalArgumentException("Could not get entitytype <entitytype:" + tokens + ">");
        }
        final ResourceLocation resourceLocation = new ResourceLocation(tokens);
        
        return Services.REGISTRY.entityTypes().getOptional(resourceLocation)
                .orElseThrow(() -> new IllegalArgumentException("Could not get entitytype <entitytype:" + tokens + ">"));
    }
    
    
    /**
     * Gets the item based on registry name. Throws an error if it can't find the item.
     *
     * @param tokens The item's resource location
     *
     * @return The found item
     *
     * @docParam tokens "minecraft:dirt"
     */
    @BracketResolver("item")
    @ZenCodeType.Method
    public static IItemStack getItem(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.LOGGER.warn("Item BEP <item:{}> does not seem to be lower-cased!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            throw new IllegalArgumentException("Could not get item with name: <item:" + tokens + ">! Syntax is <item:modid:itemname>");
        }
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        ItemStack stack = Services.REGISTRY.items().getOptional(key)
                .map(ItemStack::new)
                .orElseThrow(() -> new IllegalArgumentException("Could not get item with name: <item:" + tokens + ">! Item does not appear to exist!"));
        return Services.PLATFORM.createMCItemStack(stack);
    }
    
    
    @BracketResolver("potion")
    @ZenCodeType.Method
    public static Potion getPotion(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.LOGGER.warn("Potion BEP <potion:{}> does not seem to be lower-cased!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            throw new IllegalArgumentException("Could not get potion with name: <potion:" + tokens + ">! Syntax is <potion:modid:potionname>");
        }
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        return Services.REGISTRY.potions().getOptional(key)
                .orElseThrow(() -> new IllegalArgumentException("Could not get potion with name: <potion:" + tokens + ">! Potion does not appear to exist!"));
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
     *
     * @return The found recipeManager
     *
     * @docParam tokens "minecraft:crafting"
     */
    @ZenCodeType.Method
    public static IRecipeManager<?> getRecipeManager(String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CraftTweakerAPI.LOGGER.warn("RecipeType BEP <recipetype:{}> does not seem to be lower-cased!", tokens);
        }
        if(tokens.equalsIgnoreCase("crafttweaker:scripts")) {
            // This is bound to cause issues, like: <recipetype:crafttweaker:scripts>.removeAll(); Best to just fix it now
            throw new IllegalArgumentException("Nice try, but there's no reason you need to access the <recipetype:crafttweaker:scripts> recipe manager!");
        }
        final ResourceLocation key = new ResourceLocation(tokens);
        
        final IRecipeManager<?> result = RecipeTypeBracketHandler.getOrDefault(key);
        
        if(result != null) {
            return result;
        } else {
            throw new IllegalArgumentException("Could not get RecipeType with name: <recipetype:" + tokens + ">! RecipeType does not appear to exist!");
        }
    }
    
    /**
     * Creates a Resource location based on the tokens.
     * Throws an error if the tokens are not a valid location.
     *
     * @param tokens The resource location
     *
     * @return The location
     *
     * @docParam tokens "minecraft:dirt"
     * @deprecated Use {@link ResourceLocationBracketHandler#getResourceLocation(String)} instead.
     */
    @Deprecated(forRemoval = true)
    @ZenCodeType.Method
    public static ResourceLocation getResourceLocation(String tokens) {
        
        return ResourceLocationBracketHandler.getResourceLocation(tokens);
    }
    
    /**
     * Gets the villager profession based on registry name. Throws an exception if it can't find the profession.
     *
     * @param tokens The profession's resource location
     *
     * @return The found profession
     *
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
        
        return Services.REGISTRY.villagerProfessions().getOptional(resourceLocation)
                .orElseThrow(() -> new IllegalArgumentException("Could not get profession with name: <profession:" + tokens + ">! Profession does not appear to exist!"));
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
    
    /**
     * Gets an CreativeModeTab by name.
     * Will throw an error if the tab could not be found!
     *
     * @param tokens The CreativeModeTab's name.
     *
     * @return The found ItemGroup
     *
     * @docParam tokens misc
     */
    @ZenCodeType.Method
    @BracketResolver("creativemodetab")
    public static CreativeModeTab getCreativeModeTab(String tokens) {
        
        return Arrays.stream(CreativeModeTab.TABS)
                .filter(g -> g.getRecipeFolderName().equals(tokens))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Could not find creativemodetab for '<creativemodetab:" + tokens + ">'!"));
    }
    
    /**
     * Gets a sound event based on registry name. Throws an exception if it can't find the sound event.
     *
     * @param tokens The sound event's resource location
     *
     * @return The found sound event
     *
     * @docParam tokens "minecraft:ambient.cave"
     */
    @ZenCodeType.Method
    @BracketResolver("soundevent")
    public static SoundEvent getSoundEvent(String tokens) {
        
        final int length = tokens.split(":").length;
        if(length == 0 || length > 2) {
            throw new IllegalArgumentException("Could not get sound event <soundevent:" + tokens + ">");
        }
        final ResourceLocation resourceLocation = new ResourceLocation(tokens);
        
        return Services.REGISTRY.soundEvents().getOptional(resourceLocation)
                .orElseThrow(() -> new IllegalArgumentException("Could not get sound event with name: <soundevent:" + tokens + ">! Sound event does not appear to exist!"));
    }
    
}
