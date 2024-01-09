package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

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
    
    public static <T> T getRegistry(String tokens, Registry<T> registry) {
        
        return getRegistry(tokens, registry, false);
    }
    
    public static <T> T getRegistry(String tokens, Registry<T> registry, boolean includeTypeNamespace) {
        
        String type = includeTypeNamespace ? registry.key().location().toString() : registry.key().location().getPath();
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CommonLoggers.zenCode().warn("{} bracket <{}:{}> is not lowercase!", type, type, tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            throw new IllegalArgumentException("Could not get " + type + " with name: <" + type + ":" + tokens + ">! Syntax is <" + type + ":modid:name>");
        }
        ResourceLocation key = new ResourceLocation(split[0], split[1]);
        
        return registry.getOptional(key)
                .orElseThrow(() -> new IllegalArgumentException("Could not get " + type + " with name: <" + type + ":" + tokens + ">! " + type + " does not exist!"));
    }
    
    @ZenCodeType.Method
    @BracketResolver("attribute")
    public static Attribute getAttribute(String tokens) {
        
        return getRegistry(tokens, BuiltInRegistries.ATTRIBUTE);
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
        
        return getRegistry(tokens, BuiltInRegistries.BLOCK);
    }
    
    
    /**
     * Gets the fluid Stack based on registry name. Throws an error if it can't find the fluid.
     *
     * @param tokens The Fluid's resource location
     *
     * @return A stack of the liquid with amount == 1mb
     *
     * @docParam tokens "minecraft:water"
     */
    @ZenCodeType.Method
    @BracketResolver("fluid")
    public static IFluidStack getFluidStack(String tokens) {
        
        return IFluidStack.of(getRegistry(tokens, BuiltInRegistries.FLUID), 1);
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
            CommonLoggers.zenCode().warn("BlockState bracket <blockstate:{}> is not lower-cased!", tokens);
        }
        String[] split = tokens.split(":", 4);
        
        if(split.length > 1) {
            String blockName = split[0] + ":" + split[1];
            String properties = split.length > 2 ? split[2] : "";
            
            Optional<Block> found = BuiltInRegistries.BLOCK.getOptional(new ResourceLocation(blockName));
            if(found.isEmpty()) {
                final Throwable t = new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">! The block does not exist!");
                CommonLoggers.zenCode().error("Error creating BlockState!", t);
            } else {
                return getBlockState(found.get(), blockName, properties);
            }
        }
        CommonLoggers.zenCode()
                .error("Error creating BlockState!", new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">!"));
        return null;
    }
    
    public static BlockState getBlockState(String name, String properties) {
        
        return getBlockState(BuiltInRegistries.BLOCK.get(new ResourceLocation(name)), name, properties);
    }
    
    public static BlockState getBlockState(Block block, String name, String properties) {
        
        BlockState blockState = block.defaultBlockState();
        if(properties != null && !properties.isEmpty()) {
            for(String propertyPair : properties.split(",")) {
                String[] splitPair = propertyPair.split("=");
                if(splitPair.length != 2) {
                    CommonLoggers.zenCode()
                            .warn("Invalid blockstate property format '{}'. Using default property value.", propertyPair);
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
        
        return getRegistry(tokens, BuiltInRegistries.MOB_EFFECT);
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
        
        return getRegistry(tokens, BuiltInRegistries.ENCHANTMENT);
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
    public static EntityType<Entity> getEntityType(String tokens) {
        
        return GenericUtil.uncheck(getRegistry(tokens, BuiltInRegistries.ENTITY_TYPE));
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
        
        return IItemStack.of(new ItemStack(getRegistry(tokens, BuiltInRegistries.ITEM)));
    }
    
    
    @BracketResolver("potion")
    @ZenCodeType.Method
    public static Potion getPotion(String tokens) {
        
        return getRegistry(tokens, BuiltInRegistries.POTION);
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
            CommonLoggers.zenCode().warn("RecipeType bracket <recipetype:{}> is not lower-cased!", tokens);
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
            throw new IllegalArgumentException("Could not get RecipeType with name: <recipetype:" + tokens + ">! RecipeType does not exist!");
        }
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
        
        return getRegistry(tokens, BuiltInRegistries.VILLAGER_PROFESSION);
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
        
        return getRegistry(tokens, BuiltInRegistries.SOUND_EVENT);
    }
    
    /**
     * Gets an {@link ITargetingStrategy} based on its name.
     *
     * <p>Throws an exception if the strategy doesn't exist.</p>
     *
     * @param tokens The strategy's resource location
     *
     * @return The found targeting strategy
     *
     * @docParam tokens "crafttweaker:default"
     * @since 10.0.0
     */
    @ZenCodeType.Method
    @BracketResolver("targetingstrategy")
    public static ITargetingStrategy getTargetingStrategy(final String tokens) {
        
        if(!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
            CommonLoggers.zenCode()
                    .warn("Targeting strategy bracket <targetingstrategy:{}> is not lower-cased!", tokens);
        }
        
        final String[] split = tokens.split(":");
        if(split.length != 2) {
            throw new IllegalArgumentException("Could not get targeting strategy with <targetingstrategy:" + tokens + ">: syntax is <targetingstrategy:modid:name>");
        }
        
        final ResourceLocation key = new ResourceLocation(split[0], split[1]);
        return ITargetingStrategy.find(key);
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
    @BracketResolver("villagertype")
    public static VillagerType getVillagerType(String tokens) {
        
        return getRegistry(tokens, BuiltInRegistries.VILLAGER_TYPE);
    }
    
}
