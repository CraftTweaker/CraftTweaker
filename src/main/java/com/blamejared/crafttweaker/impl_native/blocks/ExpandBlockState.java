package com.blamejared.crafttweaker.impl_native.blocks;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.actions.blocks.ActionSetBlockProperty;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO breaking: move this to the `.block.` package

/**
 * @docParam this <blockstate:minecraft:grass>
 */
@ZenRegister
@Document("vanilla/api/block/MCBlockState")
@NativeTypeRegistration(value = BlockState.class, zenCodeName = "crafttweaker.api.blocks.MCBlockState")
public class ExpandBlockState {
    
    //TODO getPickBlock - no RayTraceResult
    //TODO canSustainPlant - no IPlantable - not sure hwo to deal with this
    //TODO rotate - no Rotation enum
    //TODO shouldCheckWeakPower - not sure on how it is used.
    //TODO getWeakChanges - not sure on how it is used.
    //TODO getSoundType - ~~do we even need it~~ we don't have a SoundType
    //TODO getBeaconColorMultiplier - ~~do we even need it~~
    //TODO getStateAtViewpoint - not sure on how it is used.
    //TODO catchFire - do we need this?
    //TODO getAiPathNodeType - do we need this?
    //TODO collisionExtendsVertically
    //TODO shouldDisplayFluidOverlay
    //TODO getToolModifiedState
    
    /**
     * Gets the base {@link Block} of this BlockState.
     *
     * The {@link Block} will not contain any of the properties of this BlockState.
     *
     * @return The base {@link Block}
     */
    @ZenCodeType.Getter("block")
    @ZenCodeType.Caster(implicit = true)
    public static Block getBlock(BlockState internal) {
        
        return internal.getBlock();
    }
    
    /**
     * Gets the light level of this BlockState
     *
     * @return The light level of this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("lightLevel")
    public static int getLightLevel(BlockState internal) {
        
        return internal.getLightValue();
    }
    
    /**
     * Checks whether this BlockState can provide Redstone Power
     *
     * @return True if this BlockState can provide Redstone Power. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canProvidePower")
    public static boolean canProvidePower(BlockState internal) {
        
        return internal.canProvidePower();
    }
    
    /**
     * Checks whether this BlockState is solid.
     *
     * @return True if this BlockState is solid. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSolid")
    public static boolean isSolid(BlockState internal) {
        
        return internal.isSolid();
    }
    
    /**
     * Checks whether this BlockState ticks randomly.
     *
     * @return True if this BlockState ticks randomly. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("ticksRandomly")
    public static boolean ticksRandomly(BlockState internal) {
        
        return internal.ticksRandomly();
    }
    
    /**
     * Checks whether this BlockState has a {@link net.minecraft.tileentity.TileEntity}.
     *
     * @return True if this BlockState has a {@link net.minecraft.tileentity.TileEntity}. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasTileEntity")
    public static boolean hasTileEntity(BlockState internal) {
        
        return internal.hasTileEntity();
    }
    
    /**
     * Checks if this BlockState is sticky.
     *
     * This is used to determine if the block should pull or push adjacent blocks when pushed / pulled by a piston.
     * For example, Slime Blocks are sticky blocks.
     *
     * @return True if this BlockState is Sticky. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSticky")
    public static boolean isSticky(BlockState internal) {
        
        return internal.isStickyBlock();
    }
    
    /**
     * Checks if this BlockState is a Slime Block.
     *
     * @return True if this BlockState is Slime. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSlime")
    public static boolean isSlime(BlockState internal) {
        
        return internal.isSlimeBlock();
    }
    
    /**
     * Sets a block property based on it's name.
     *
     * @param name  The name of the property to set.
     * @param value The new value of the property.
     *
     * @return This BlockState with the new property value.
     *
     * @docParam name "snowy"
     * @docParam value "true"
     */
    @ZenCodeType.Method
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static BlockState withProperty(BlockState internal, String name, String value) {
        
        Property property = internal.getBlock().getStateContainer().getProperty(name);
        if(property == null) {
            CraftTweakerAPI.logWarning("Invalid property name");
        } else {
            Optional<Comparable> propValue = property.parseValue(value);
            if(propValue.isPresent()) {
                return internal.with(property, propValue.get());
            }
            CraftTweakerAPI.logWarning("Invalid property value");
        }
        return internal;
    }
    
    /**
     * Gets the names of the properties of this BlockState.
     *
     * @return the List of the names of the BlockStates's properties.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("propertyNames")
    public static List<String> getPropertyNames(BlockState internal) {
        
        List<String> props = new ArrayList<>();
        for(Property<?> prop : internal.getProperties()) {
            props.add(prop.getName());
        }
        return ImmutableList.copyOf(props);
    }
    
    /**
     * Gets the value of the given property.
     *
     * @param name "snowy"
     *
     * @return The value of the property on this BlockState.
     */
    @ZenCodeType.Method
    public static String getPropertyValue(BlockState internal, String name) {
        
        Property<?> prop = internal.getBlock().getStateContainer().getProperty(name);
        if(prop != null) {
            return internal.get(prop).toString();
        }
        CraftTweakerAPI.logWarning("Invalid property name");
        return "";
    }
    
    /**
     * Gets a list of allowed values for a given property.
     *
     * @param name The name of the property to find the allowed values for.
     *
     * @return a List of allowed values.
     *
     * @docParam name "snowy"
     */
    @ZenCodeType.Method
    public static List<String> getAllowedValuesForProperty(BlockState internal, String name) {
        
        Property<?> prop = internal.getBlock().getStateContainer().getProperty(name);
        if(prop != null) {
            List<String> values = new ArrayList<>();
            prop.getAllowedValues().forEach(v -> values.add(v.toString()));
            return values;
        }
        CraftTweakerAPI.logWarning("Invalid property name");
        return ImmutableList.of();
    }
    
    /**
     * Gets the properties of this BlockState.
     *
     * @return a Map of the properties on this BlockState.
     */
    @ZenCodeType.Method
    public static Map<String, String> getProperties(BlockState internal) {
        
        Map<String, String> props = new HashMap<>();
        for(Property<?> key : internal.getProperties()) {
            props.put(key.getName(), internal.get(key).toString());
        }
        return ImmutableMap.copyOf(props);
    }
    
    /**
     * Checks whether this BlockState has the given property.
     *
     * @param name the name of the property to check.
     *
     * @return True if this BlockState has the property. False otherwise.
     *
     * @docParam name "snowy"
     */
    @ZenCodeType.Method
    public static boolean hasProperty(BlockState internal, String name) {
        
        Property<?> prop = internal.getBlock().getStateContainer().getProperty(name);
        return prop != null;
    }
    
    /**
     * Gets the {@link ToolType} of this BlockState.
     *
     * @return The {@link ToolType} of this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("harvestTool")
    @ZenCodeType.Nullable
    public static ToolType getHarvestTool(BlockState internal) {
        
        return internal.getHarvestTool();
    }
    
    /**
     * Gets the harvest level of this BlockState.
     *
     * @return The harvest level of this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("harvestLevel")
    public static int getHarvestLevel(BlockState internal) {
        
        return internal.getHarvestLevel();
    }
    
    /**
     * Gets the slipperiness of the BlockState at the given location for the given entity (if one is given)
     *
     * @param world  A world object.
     * @param pos    The position to check at.
     * @param entity The entity to work with.
     *
     * @return The slipperiness of the BlockState at the given BlockPos for the Entity.
     *
     * @docParam world world
     * @docParam pos new Blockpos(0,0,0);
     * @docParam entity entity
     */
    @ZenCodeType.Method
    public static float getSlipperiness(BlockState internal, World world, BlockPos pos, @ZenCodeType.Optional Entity entity) {
        
        return internal.getSlipperiness(world, pos, entity);
    }
    
    /**
     * Gets the light value of the BlockState at the given position.
     *
     * @param world A world Object.
     * @param pos   The position to check the light value of.
     *
     * @return The light value of the BlockState at the position.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static int getLightValue(BlockState internal, World world, BlockPos pos) {
        
        return internal.getLightValue(world, pos);
    }
    
    /**
     * Checks if a Living Entity can use this block to climb like a ladder.
     *
     * @param world  A world object.
     * @param pos    The position to check at.
     * @param entity The entity that wants to climb the block.
     *
     * @return True if the entity can climb the block. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam entity entity
     */
    @ZenCodeType.Method
    public static boolean isLadder(BlockState internal, World world, BlockPos pos, LivingEntity entity) {
        
        return internal.isLadder(world, pos, entity);
    }
    
    
    /**
     * Checks whether the player can harvest the BlockState.
     *
     * @param world  A world object.
     * @param pos    The position to check at.
     * @param player The player that is trying to harvest the block.
     *
     * @return True if the player can harvest the block. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam player player
     */
    @ZenCodeType.Method
    public static boolean canHarvestBlock(BlockState internal, World world, BlockPos pos, PlayerEntity player) {
        
        return internal.canHarvestBlock(world, pos, player);
    }
    
    /**
     * Determines if the block can be used to sleep.
     *
     * @param world   A world object.
     * @param pos     The position to check at.
     * @param sleeper The Living Entity that is trying to sleep.
     *
     * @return True if the block allows sleeping.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam sleeper entity
     */
    @ZenCodeType.Method
    public static boolean isBed(BlockState internal, World world, BlockPos pos, @ZenCodeType.Optional LivingEntity sleeper) {
        
        return internal.isBed(world, pos, sleeper);
    }
    
    /**
     * Checks if a specific entity type can spawn on this BlockState at the position in the world.
     *
     * @param world      A world object.
     * @param pos        The position in the world.
     * @param type       The PlaceMentType to check for.
     * @param entityType The EntityType to check for.
     *
     * @return True if the entity type can spawn. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam type PlacementType.IN_WATER
     * @docParam entityType <entitytype:minecraft:pig>
     */
    @ZenCodeType.Method
    public static boolean canCreatureSpawn(BlockState internal, World world, BlockPos pos, EntitySpawnPlacementRegistry.PlacementType type, MCEntityType entityType) {
        
        return internal.canCreatureSpawn(world, pos, type, entityType.getInternal());
    }
    
    /**
     * Marks the block as "occupied", this is only supported on blocks that have the "occupied" BlockState property.
     *
     * The game ***WILL*** crash if you call this on a blockstate that doesn't have the property, so make sure
     * you check it before calling it!
     *
     * @param world    A world object.
     * @param pos      The position of the BlockState.
     * @param sleeper  The LivingEntity that is occupying the bed.
     * @param occupied If the bed is occupied or not.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam sleeper livingEntity
     * @docParam occupied true
     */
    @ZenCodeType.Method
    public static void setBedOccupied(BlockState internal, World world, BlockPos pos, LivingEntity sleeper, boolean occupied) {
        
        internal.setBedOccupied(world, pos, sleeper, occupied);
    }
    
    /**
     * Gets the direction of the bed. This can be called on any BlockState that has the `HORIZONTAL_FACING` property, not just beds.
     *
     * The game ***WILL*** crash if you call this on a blockstate that doesn't have the property, so make sure
     * you check it before calling it!
     *
     * @param world A world object.
     * @param pos   The position of the BlockState
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static void getBedDirection(BlockState internal, World world, BlockPos pos) {
        
        internal.getBedDirection(world, pos);
    }
    
    /**
     * Checks if this BlockState can be replaced by leaves in tree growth.
     *
     * @param world A world object.
     * @param pos   The position of the BlockState
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static boolean canBeReplacedByLeaves(BlockState internal, World world, BlockPos pos) {
        
        return internal.canBeReplacedByLeaves(world, pos);
    }
    
    /**
     * Checks if this BlockState can be replaced by logs in tree growth.
     *
     * @param world A world object.
     * @param pos   The position of the BlockState
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static boolean canBeReplacedByLogs(BlockState internal, World world, BlockPos pos) {
        
        return internal.canBeReplacedByLogs(world, pos);
    }
    
    /**
     * Gets the explosion resistance of the BlockState in the world for the given Explosion
     *
     * @param world     A world object.
     * @param pos       The position of the BlockState
     * @param explosion The explosion object to check against.
     *
     * @return The amount of the explosion that is absorbed.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam explosion Explosiotn.create(world, 1, 2, 3, 5, true, ExplosionMode.BREAK)
     */
    @ZenCodeType.Method
    public static float getExplosionResistance(BlockState internal, World world, BlockPos pos, Explosion explosion) {
        
        return internal.getExplosionResistance(world, pos, explosion);
    }
    
    /**
     * Determines if Redstone can connect to this BlockState at the given position and (optional) side.
     *
     * @param world A world object.
     * @param pos   The position of the BlockState.
     * @param side  The side to check for.
     *
     * @return True if Redstone can connect to this BlockState. False otherwise.
     *
     * @docParam world world
     * @docParam new BlockPos(1, 2, 3)
     * @docParam side Direction.north
     */
    @ZenCodeType.Method
    public static boolean canConnectRedstone(BlockState internal, World world, BlockPos pos, @ZenCodeType.Optional MCDirection side) {
        
        return internal.canConnectRedstone(world, pos, side.getInternal());
    }
    
    
    /**
     * Checks if the BlockState is "fertile" at the given position.
     *
     * This will only ever return `true` if the BlockState is in the `<tag:blocks:minecraft:farmland>` tag and if it has the `moisture` state.
     *
     * The game ***WILL*** crash if you call this on a blockstate that doesn't have the property, so make sure
     * you check it before calling it!
     *
     * @param world A world object.
     * @param pos   The position of the BlockState
     *
     * @return True if the BlockState is Fertile. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static boolean isFertile(BlockState internal, World world, BlockPos pos) {
        
        return internal.isFertile(world, pos);
    }
    
    /**
     * Determines if this BlockState can be used as the frame of a Conduit.
     *
     * @param world           A world object.
     * @param pos             The position of the BlockState.
     * @param conduitPosition The Position of the Conduit.
     *
     * @return True if this BlockState can be used as a Conduit frame.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam conduitPosition new BlockPos(1, 5, 3)
     */
    @ZenCodeType.Method
    public static boolean isConduitFrame(BlockState internal, World world, BlockPos pos, BlockPos conduitPosition) {
        
        return internal.isConduitFrame(world, pos, conduitPosition);
    }
    
    /**
     * Determins if this BlockState can be used as the frame of a Nether portal.
     *
     * @param world A world object.
     * @param pos   The position of the Blockstate.
     *
     * @return True if this BlockState can be used as a Nether portal frame. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static boolean isPortalFrame(BlockState internal, World world, BlockPos pos) {
        
        return internal.isPortalFrame(world, pos);
    }
    
    /**
     * Returns how much Experience this BlockState will drop when broken.
     *
     * @param world     A world object.
     * @param pos       The position of the BlockState.
     * @param fortune   The fortune level of the tool being used to break the block.
     * @param silktouch The silktouch level of the tool being used to break the block. This will most likely either be 0 or 1.
     *
     * @return The amount of Experience that will drop when this BlockState is broken.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam fortune 1
     * @docParam silktouch 0
     */
    @ZenCodeType.Method
    public static int getExpDrop(BlockState internal, World world, BlockPos pos, int fortune, int silktouch) {
        
        return internal.getExpDrop(world, pos, fortune, silktouch);
    }
    
    /**
     * Checks if the given ToolType is effective against this BlockState.
     *
     * @param tool The ToolType to check for.
     *
     * @return True if the ToolType is effective. False otherwise.
     *
     * @docParam ToolType <tooltype:shovel>
     */
    @ZenCodeType.Method
    public static boolean isToolEffective(BlockState internal, ToolType tool) {
        
        return internal.isToolEffective(tool);
    }
    
    /**
     * Checks if this BlockState can stick to the other BlockState when pushed by a piston.
     *
     * @param other The BlockState check if it will stick against.
     *
     * @return True if the BlockStatess stick when pushed by a piston. False otherwise.
     *
     * @docParam other <blockstate:minecraft:grass>
     */
    @ZenCodeType.Method
    public static boolean canStickTo(BlockState internal, BlockState other) {
        
        return internal.canStickTo(other);
    }
    
    /**
     * Gets the chance that fire will spread and consume this BlockState.
     *
     * Values range from 0 - 300.
     *
     * A value of 300 is a 100% chance, and 0 is a 0% chance.
     *
     * @param world A world object.
     * @param pos   The position of the BlockState.
     * @param face  The face that the fire is coming from.
     *
     * @return A number between 0 and 300 dictating how flammable this BlockState is.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam face Direction.south
     */
    @ZenCodeType.Method
    public static int getFlammability(BlockState internal, World world, BlockPos pos, MCDirection face) {
        
        return internal.getFlammability(world, pos, face.getInternal());
    }
    
    /**
     * Checks if this BlockState is flammable at the given position with the fire coming from the given direciton.
     *
     * @param world A world object.
     * @param pos   The position of the BlockState.
     * @param face  The face that the fire is coming from.
     *
     * @return True if the BlockState if flammable. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam face Direction.east
     */
    @ZenCodeType.Method
    public static boolean isFlammable(BlockState internal, World world, BlockPos pos, MCDirection face) {
        
        return internal.isFlammable(world, pos, face.getInternal());
    }
    
    /**
     * Determines how fast fire spreads from this block.
     * The higher the number the faster that fire will spread around the BlockState.
     *
     * @param world A world object.
     * @param pos   The position of the BlockState.
     * @param face  The face that the fire is coming from.
     *
     * @return The spread speed of the BlockState.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam side Direction.east
     */
    @ZenCodeType.Method
    public static int getFireSpreadSpeed(BlockState internal, World world, BlockPos pos, MCDirection face) {
        
        return internal.getFireSpreadSpeed(world, pos, face.getInternal());
    }
    
    /**
     * Checks if this BlockState is a fire source at the given position with the fire coming from the given direciton.
     *
     * @param world A world object.
     * @param pos   The position of the BlockState.
     * @param side  The face that the fire is coming form.
     *
     * @return True if this BlockState is a fire source. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam side Direction.east
     */
    @ZenCodeType.Method
    public static boolean isFireSource(BlockState internal, World world, BlockPos pos, MCDirection side) {
        
        return internal.isFireSource(world, pos, side.getInternal());
    }
    
    /**
     * Determines if this BlockState can be destroyed by the Entity at the specific position.
     *
     * @param world  A world object.
     * @param pos    The position of the BlockState.
     * @param entity The Entity that is trying to destroy the BlockState.
     *
     * @return True if the entity can destroy the BlockState. False otherwise.
     *
     * @docParam world world
     * @docParam new BlockPos(1, 2, 3)
     * @docParam entity entity
     */
    @ZenCodeType.Method
    public static boolean canEntityDestroy(BlockState internal, World world, BlockPos pos, Entity entity) {
        
        return internal.canEntityDestroy(world, pos, entity);
    }
    
    /**
     * Checks if the BlockState is burning at the given position.
     *
     * @param world A world object.
     * @param pos   The position of the BlockState.
     *
     * @return True if the BlockState is burning a the given position.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     */
    @ZenCodeType.Method
    public static boolean isBurning(BlockState internal, World world, BlockPos pos) {
        
        return internal.isBurning(world, pos);
    }
    
    
    /**
     * Checks if the BlockState will drop from the given explosion at the given position.
     *
     * @param world     A world object.
     * @param pos       The position of the BlockState.
     * @param explosion The Explosion to check against.
     *
     * @return True if this BlockState will drop. False otherwise.
     *
     * @docParam world world
     * @docParam pos new BlockPos(1, 2, 3)
     * @docParam explosion Explosiotn.create(world, 1, 2, 3, 5, true, ExplosionMode.BREAK)
     */
    @ZenCodeType.Method
    public static boolean canDropFromExplosion(BlockState internal, World world, BlockPos pos, Explosion explosion) {
        
        return internal.canDropFromExplosion(world, pos, explosion);
    }
    
    /**
     * Checks if the entity should handle movement on this BlockState like it handles movement on scaffolding.
     *
     * @param entity The entity that is being checked against.
     *
     * @return True if this BlockState should act like scaffolding. False otherwise.
     *
     * @docParam entity entity
     */
    @ZenCodeType.Method
    public static boolean isScaffolding(BlockState internal, LivingEntity entity) {
        
        return internal.isScaffolding(entity);
    }
    
    @ZenCodeType.Caster
    public static String asString(BlockState internal) {
        
        return internal.toString();
    }

    /**
     * Gets the hardness of this BlockState.
     *
     * @return The hardness of this BlockState.
     */
    @ZenCodeType.Getter("hardness")
    @ZenCodeType.Method
    public static float getHardness(BlockState internal) {

        return internal.hardness;
    }

    /**
     * Sets the hardness of this BlockState.
     *
     * @param hardness the new hardness of this BlockState
     *
     * @docParam hardness 2.4f
     */
    @ZenCodeType.Setter("hardness")
    @ZenCodeType.Method
    public static void setHardness(BlockState internal, float hardness) {
        CraftTweakerAPI.apply(new ActionSetBlockProperty<>(internal, "Hardness", hardness, internal.hardness, value -> internal.hardness = value ));
    }
    
    /**
     * Gets the blockstate bracket handler syntax for this BlockState.
     *
     * E.G.
     * <code>
     * <blockstate:minecraft:grass:snowy=true>
     * </code>
     *
     * @return The blockstate bracket handler syntax for this BlockState.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(BlockState internal) {
        
        StringBuilder builder = new StringBuilder("<blockstate:");
        builder.append(getBlock(internal).getRegistryName());
        if(!getProperties(internal).isEmpty()) {
            builder.append(":");
            builder.append(getProperties(internal).entrySet()
                    .stream()
                    .map(kv -> kv.getKey() + "=" + kv.getValue())
                    .collect(Collectors.joining(",")));
        }
        builder.append(">");
        return builder.toString();
    }
    
}
