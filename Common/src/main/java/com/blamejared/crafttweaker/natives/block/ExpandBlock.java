package com.blamejared.crafttweaker.natives.block;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.block.ActionSetBlockProperty;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.block.CTBlockIngredient;
import com.blamejared.crafttweaker.mixin.common.access.block.AccessBlockBehaviour;
import com.blamejared.crafttweaker.natives.block.material.ExpandMaterial;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @docParam this <block:minecraft:grass_block>
 */
@ZenRegister
@Document("vanilla/api/block/Block")
@NativeTypeRegistration(value = Block.class, zenCodeName = "crafttweaker.api.block.Block")
@TaggableElement("minecraft:block")
public class ExpandBlock {
    
    /**
     * Gets the registry name of this block.
     *
     * @return A ResourceLocation of the registry name of this block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(Block internal) {
        
        return BuiltInRegistries.BLOCK.getKey(internal);
    }
    
    /**
     * Gets the default {@link BlockState} of this Block.
     *
     * @return The default {@link BlockState} of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultState")
    public static BlockState getDefaultState(Block internal) {
        
        return internal.defaultBlockState();
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
    @ZenCodeType.Getter("isPossibleToRespawnInThis")
    public static boolean isPossibleToRespawnInThis(Block internal) {
        
        return internal.isPossibleToRespawnInThis();
    }
    
    /**
     * Gets the translation key that is used to localize this Block.
     *
     * @return The unlocalized name of this block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("descriptionId")
    public static String getDescriptionId(Block internal) {
        
        return internal.getDescriptionId();
    }
    
    /**
     * Gets the name of this Block
     *
     * @return The name of this block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static MutableComponent getName(Block internal) {
        
        return internal.getName();
    }
    
    /**
     * Gets the block bracket handler syntax for this Block.
     *
     * E.G.
     * {@code <block:minecraft:dirt>}
     *
     * @return The block bracket handler syntax for this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Block internal) {
        
        return "<block:" + getRegistryName(internal) + ">";
    }
    
    /**
     * Gets a list of valid {@link BlockState}s for this Block.
     *
     * @return A list of valid {@link BlockState}s for this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("possibleStates")
    public static List<BlockState> getPossibleStates(Block internal) {
        
        return internal.getStateDefinition().getPossibleStates();
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
    public static boolean matches(Block internal, Block other) {
        
        return internal == other;
    }
    
    /**
     * Gets the friction of this Block.
     *
     * @return The friction of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("friction")
    public static float getFriction(Block internal) {
        
        return ((AccessBlockBehaviour) internal).crafttweaker$getFriction();
    }
    
    /**
     * Sets the friction of this Block.
     *
     * @param friction The new friction of this Block.
     *
     * @docParam friction 2
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("friction")
    public static void setFriction(Block internal, float friction) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Friction",
                friction, ((AccessBlockBehaviour) internal).crafttweaker$getFriction(), ((AccessBlockBehaviour) internal)::crafttweaker$setFriction));
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
                speedFactor, ((AccessBlockBehaviour) internal).crafttweaker$getSpeedFactor(), ((AccessBlockBehaviour) internal)::crafttweaker$setSpeedFactor));
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
                jumpFactor, ((AccessBlockBehaviour) internal).crafttweaker$getJumpFactor(), ((AccessBlockBehaviour) internal)::crafttweaker$setJumpFactor));
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
    @ZenCodeType.Getter("dynamicShape")
    public static boolean hasDynamicShape(Block internal) {
        
        return internal.hasDynamicShape();
    }
    
    
    /**
     * Checks if entities can collide with this Block.
     *
     * @return True if entities will collide with this Block. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasCollision")
    public static boolean hasCollision(Block internal) {
        
        return ((AccessBlockBehaviour) internal).crafttweaker$getHasCollision();
    }
    
    /**
     * Sets whether entities can collide with this Block.
     *
     * @param canCollide Can entities collide with this Block.
     *
     * @docParam canCollide true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("hasCollision")
    public static void setHasCollision(Block internal, boolean canCollide) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Has Collision",
                canCollide, ((AccessBlockBehaviour) internal).crafttweaker$getHasCollision(), ((AccessBlockBehaviour) internal)::crafttweaker$setHasCollision));
    }
    
    /**
     * Gets the blast resistance of this Block.
     *
     * @return The blast resistance of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("explosionResistance")
    public static float getExplosionResistance(Block internal) {
        
        return ((AccessBlockBehaviour) internal).crafttweaker$getExplosionResistance();
    }
    
    /**
     * Sets the blast resistance of this Block.
     *
     * @param resistance The new blast resistance of this Block.
     *
     * @docParam resistance 2
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("explosionResistance")
    public static void setExplosionResistance(Block internal, float resistance) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Explosion Resistance",
                resistance, ((AccessBlockBehaviour) internal).crafttweaker$getExplosionResistance(), ((AccessBlockBehaviour) internal)::crafttweaker$setExplosionResistance));
    }
    
    /**
     * Gets the material of this Block.
     *
     * @return The material of this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("material")
    public static Material getMaterial(Block internal) {
        
        return ((AccessBlockBehaviour) internal).crafttweaker$getMaterial();
    }
    
    /**
     * Sets the material of this Block.
     *
     * @param material The new material of this Block.
     *
     * @docParam material <material:earth>
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("material")
    public static void setMaterial(Block internal, Material material) {
        
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal,
                "Material", material, ((AccessBlockBehaviour) internal).crafttweaker$getMaterial(),
                ((AccessBlockBehaviour) internal)::crafttweaker$setMaterial, ExpandMaterial::getCommandString));
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static CTBlockIngredient asBlockIngredient(Block internal) {
        
        return new CTBlockIngredient.BlockIngredient(internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTBlockIngredient asList(Block internal, CTBlockIngredient other) {
        
        List<CTBlockIngredient> elements = new ArrayList<>();
        elements.add(asBlockIngredient(internal));
        elements.add(other);
        return new CTBlockIngredient.CompoundBlockIngredient(elements);
    }
    
}
