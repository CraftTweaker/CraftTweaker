package crafttweaker.api.block;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

/**
 * Blocks definitions provide additional information about blocks.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.block.IBlockDefinition")
@ZenRegister
public interface IBlockDefinition {
    
    @ZenGetter("id")
    String getId();
    
    @ZenGetter("displayName")
    String getDisplayName();
    
    Object getInternal();
    
    @ZenSetter("lightOpacity")
    void setLightOpacity(int lightOpacity);

    @ZenGetter("lightOpacity")
    @ZenMethod
    default float getLightOpacity() {
        CraftTweakerAPI.logError(
                "Class " + this.getClass().getCanonicalName() + " doesn't override IBlockDefinition::getLightOpacity()");
        return 0;
    }

    @ZenMethod
    default float getLightOpacity(IBlockState state) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName()
                + " doesn't override IBlockDefinition::getLightOpacity(state)");
        return 0;
    }

    @ZenMethod
    default float getLightOpacity(IBlockState state, IWorld world, IBlockPos pos) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName()
                + " doesn't override IBlockDefinition::getLightOpacity(state, world, pos)");
        return 0;
    }
    
    @ZenSetter("lightLevel")
    void setLightLevel(float lightLevel);

    @ZenGetter("lightLevel")
    @ZenMethod
    default float getLightLevel() {
        CraftTweakerAPI.logError(
                "Class " + this.getClass().getCanonicalName() + " doesn't override IBlockDefinition::getLightLevel()");
        return 0;
    }

    @ZenMethod
    default float getLightLevel(IBlockState state) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName()
                + " doesn't override IBlockDefinition::getLightLevel(state)");
        return 0;
    }

    @ZenMethod
    default float getLightLevel(IBlockState state, IWorld world, IBlockPos pos) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName()
                + " doesn't override IBlockDefinition::getLightLevel(state, world, pos)");
        return 0;
    }
    
    @ZenSetter("resistance")
    void setResistance(float resistance);

    @ZenGetter("resistance")
    @ZenMethod
    default float getResistance() {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName() + " doesn't override IBlockDefinition::getResistance()");
        return 0;
    }

    @ZenMethod
    default float getResistance(IWorld world, IBlockPos pos, IEntity entity, IExplosion explosion) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName() + " doesn't override IBlockDefinition::getResistance(world, pos, entity, explosion)");
        return 0;
    }
    
    @ZenSetter("hardness")
    void setHardness(float hardness);
    
    @ZenGetter("hardness")
    float getHardness();
    
    @ZenMethod
    void setUnbreakable();
    
    @ZenGetter("tickRandomly")
    boolean getTickRandomly();
    
    @ZenSetter("tickRandomly")
    void setTickRandomly(boolean tickRandomly);

    @Deprecated
    void setHarvestLevel(String toolClass, int level);
    
    @ZenMethod
    default void setHarvestLevel(String toolClass, int level, @Optional IBlockState state) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName() + " doesn't override IBlockDefinition::setHarvestLevel!");
    }
    
    @ZenGetter("harvestLevel")
    @ZenMethod
    int getHarvestLevel();

    @ZenMethod
    default int getHarvestLevel(IBlockState state) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName() + "doesn't override IBlockDefinition::setHarvestLevel! It is a bug!");
        return 0;
    }
    
    @ZenGetter("harvestTool")
    @ZenMethod
    String getHarvestTool();
    
    @ZenMethod
    default String getHarvestTool(IBlockState state) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName() + "doesn't override IBlockDefinition::setHarvestTool! It is a bug!");
        return "";
    }
    
    @ZenMethod
    int tickRate(IWorld world);
    
    @ZenMethod
    boolean canPlaceBlockOnSide(IWorld world, IBlockPos pos, IFacing facing);
    
    @ZenMethod
    boolean canPlaceBlockAt(IWorld world, IBlockPos pos);
    
    @ZenGetter
    boolean canSpawnInBlock();
    
    @ZenGetter("unlocalizedName")
    String getUnlocalizedName();
    
    @ZenGetter("creativeTab")
    ICreativeTab getCreativeTabToDisplayOn();
    
    @ZenSetter("creativeTab")
    void setCreativeTab(ICreativeTab creativeTab);
    
    @ZenGetter("defaultState")
    IBlockState getDefaultState();
    
    //@ZenGetter("soundType")
    //ISoundType getSoundType();
    
    @ZenMethod
    float getSlipperiness(IBlockState state, IBlockAccess access, IBlockPos pos, @Optional IEntity entity);
    
    @ZenSetter("defaultSlipperiness")
    void setDefaultSlipperiness(float defaultSlipperiness);

    @ZenMethod
    default IBlockState getStateFromMeta(int meta) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName() + " doesn't override IBlockDefinition::getStateFromMeta!");
        return getDefaultState();
    }
    
    @ZenMethod
    default boolean isToolEffective(String type, IBlockState state) {
        CraftTweakerAPI.logError("Class " + this.getClass().getCanonicalName() + "doesn't override IBlockDefinition::isToolEffective! It is a bug!");
        return false;
    }
}
