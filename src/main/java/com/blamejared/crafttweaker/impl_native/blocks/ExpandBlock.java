package com.blamejared.crafttweaker.impl_native.blocks;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.actions.blocks.ActionSetBlockProperty;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.manager.TagManagerBlock;
import com.blamejared.crafttweaker.impl_native.block.material.ExpandMaterial;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

//TODO breaking: move this to the `.block.` package

/**
 * @docParam this <block:minecraft:grass_block>
 */
@ZenRegister
@Document("vanilla/api/block/MCBlock")
@NativeTypeRegistration(value = Block.class, zenCodeName = "crafttweaker.api.blocks.MCBlock")
public class ExpandBlock {
    
    
    /**
     * Gets the registry name of this block.
     *
     * @return A ResourceLocation of the registry name of this block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(Block internal) {
        
        return internal.getRegistryName();
    }
    
    /**
     * Gets the default {@link BlockState} of this Block.
     *
     * @return The default {@link BlockState} of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultState")
    public static BlockState getDefaultState(Block internal) {
        
        return internal.getDefaultState();
    }
    
    /**
     * Gets the loot table id for this Block.
     *
     * @return The loot table id for this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("lootTable")
    public static String getLootTable(Block internal) {
        
        return internal.getLootTable().toString();
    }
    
    /**
     * Checks if an entity can be spawned inside this Block.
     *
     * This is used to find valid spawn locations for players.
     *
     * @return True if an entity can be spawned in this Block. False Otherwise.
     */
    @ZenCodeType.Method
    public static boolean canSpawnInBlock(Block internal) {
        
        return internal.canSpawnInBlock();
    }
    
    /**
     * Gets the translation key that is used to localize this Block.
     *
     * @return The unlocalized name of this block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("translationKey")
    public static String getTranslationKey(Block internal) {
        
        return internal.getTranslationKey();
    }
    
    @ZenCodeType.Caster()
    public static String asString(Block internal) {
        
        return internal.toString();
    }
    
    /**
     * Gets the block bracket handler syntax for this Block.
     *
     * E.G.
     * <code>
     * <block:minecraft:dirt>
     * </code>
     *
     * @return The block bracket handler syntax for this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Block internal) {
        
        return "<block:" + internal.getRegistryName() + ">";
    }
    
    /**
     * Gets a list of valid {@link BlockState}s for this Block.
     *
     * @return A list of valid {@link BlockState}s for this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("validStates")
    public static List<BlockState> getValidStates(Block internal) {
        
        return internal.getStateContainer().getValidStates();
    }
    
    /**
     * Checks if this Block is in the given {@link MCTag}.
     *
     * @param tag The {@link MCTag} to check against.
     *
     * @return True if this Block is in the {@link MCTag}. False otherwise.
     */
    @ZenCodeType.Method
    public static boolean isInTag(Block internal, MCTag<Block> tag) {
        
        //noinspection unchecked
        return internal.isIn((ITag<Block>) tag.getInternal());
    }
    
    /**
     * Checks whether this Block matches another Block.
     *
     * @param other The other Block to check if this Block matches.
     *
     * @return True if this Block matches the other Block. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public static boolean matchesBlock(Block internal, Block other) {
        
        return internal.matchesBlock(other);
    }
    
    /**
     * Gets all the {@link MCTag}s that contain this Block.
     *
     * @return a List of {@link MCTag}s that contain this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("tags")
    public static List<MCTag<Block>> getTags(Block internal) {
        
        return internal.getTags()
                .stream()
                //Even though getTag doesn't create the tag, all of these tags should already exist
                .map(TagManagerBlock.INSTANCE::getTag)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the slipperiness of this Block.
     *
     * @return The Slipperiness of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("slipperiness")
    public static float getSlipperiness(Block internal) {
        
        return internal.getSlipperiness();
    }
    
    /**
     * Sets the slipperiness of this Block.
     *
     * @param slipperiness The new slipperiness of this Block.
     *
     * @docParam slipperiness 2
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("slipperiness")
    public static void setSlipperiness(Block internal, float slipperiness) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Slipperiness",
                slipperiness, internal.slipperiness, value -> internal.slipperiness = value));
    }
    
    /**
     * Gets the speed factor of this Block.
     *
     * @return The speed factor of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("speedFactor")
    public static float getSpeedFactor(Block internal) {
        
        return internal.getSpeedFactor();
    }
    
    /**
     * Sets the speed factor of this Block.
     *
     * @param speedFactor The new speed factor of this Block.
     *
     * @docParam speedFactor 2
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("speedFactor")
    public static void setSpeedFactor(Block internal, float speedFactor) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Speed Factor",
                speedFactor, internal.speedFactor, value -> internal.speedFactor = value));
    }
    
    /**
     * Gets the jump factor of this Block.
     *
     * @return The jump factor of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("jumpFactor")
    public static float getJumpFactor(Block internal) {
        
        return internal.getJumpFactor();
    }
    
    /**
     * Sets the jump factor of this Block.
     *
     * @param jumpFactor The new jump factor of this Block.
     *
     * @docParam jumpFactor 2
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("jumpFactor")
    public static void setJumpFactor(Block internal, float jumpFactor) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Jump Factor",
                jumpFactor, internal.jumpFactor, value -> internal.jumpFactor = value));
    }
    
    
    /**
     * Gets the Item representation of this Block.
     *
     * ***NOTE:*** Not all Blocks have Items, for instance, a Lit Redstone Lamp does not have an Item.
     *
     * @return The Item representation of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    public static Item asItem(Block internal) {
        
        return internal.asItem();
    }
    
    /**
     * Checks if the opacity of this block is different in different areas of the Block.
     *
     * @return True if this Block has variable opacity. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("variableOpacity")
    public static boolean isVariableOpacity(Block internal) {
        
        return internal.isVariableOpacity();
    }
    
    
    /**
     * Checks if entities can collide with this Block.
     *
     * @return True if entities will collide with this Block. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canCollide")
    public static boolean canCollide(Block internal) {
        
        return internal.canCollide;
    }
    
    /**
     * Sets whether entities can collide with this Block.
     *
     * @param canCollide Can entities collide with this Block.
     *
     * @docParam canCollide true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("canCollide")
    public static void setCanCollide(Block internal, boolean canCollide) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Can Collide",
                canCollide, internal.canCollide, value -> internal.canCollide = value));
    }
    
    /**
     * Gets the blast resistance of this Block.
     *
     * @return The blast resistance of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("blastResistance")
    public static float getBlastResistance(Block internal) {
        
        return internal.blastResistance;
    }
    
    /**
     * Sets the blast resistance of this Block.
     *
     * @param resistance The new blast resistance of this Block.
     *
     * @docParam resistance 2
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("blastResistance")
    public static void setBlastResistance(Block internal, float resistance) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Blast Resistance",
                resistance, internal.blastResistance, value -> internal.blastResistance = value));
    }
    
    /**
     * Gets the material of this Block.
     *
     * @return The material of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("material")
    public static Material getMaterial(Block internal) {
        
        return internal.material;
    }
    
    /**
     * Sets the material of this Block.
     *
     * @param material The new material of this Block.
     *
     * @docParam material <blockmaterial:earth>
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("material")
    public static void setMaterial(Block internal, Material material) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal,
                "Material", material, internal.material,
                value -> internal.material = material, ExpandMaterial::getCommandString));
    }
    
}
