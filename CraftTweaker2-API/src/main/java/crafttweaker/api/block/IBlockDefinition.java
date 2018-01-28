package crafttweaker.api.block;

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
    
    @ZenSetter("lightLevel")
    void setLightLevel(float lightLevel);
    
    @ZenSetter("resistance")
    void setResistance(float resistance);
    
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
    
    @ZenMethod
    void setHarvestLevel(String toolClass, int level);
    
    @ZenGetter("harvestLevel")
    int getHarvestLevel();
    
    @ZenGetter("harvestTool")
    String getHarvestTool();
    
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
    
}
