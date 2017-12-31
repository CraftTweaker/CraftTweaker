package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenClass("crafttweaker.block.IBlockState")
@ZenRegister
public interface IBlockState {
	@ZenMethod
    @ZenGetter("block")
    IBlock getBlock();

    @ZenMethod
    @ZenGetter("meta")
    int getMeta();

    @ZenMethod
    boolean isReplaceable(IWorld world, IBlockPos blockPos);

    @ZenMethod
    int getLightValue(IWorld world, IBlockPos blockPos);

    @ZenMethod
    @ZenGetter("canProvidePower")
    boolean canProvidePower();

    @ZenMethod
    int getWeakPower(IWorld world, IBlockPos blockPos, String facing);

    @ZenMethod
    int getComparatorInputOverride(IWorld world, IBlockPos blockPos);

    @ZenMethod
    @ZenGetter("mobilityFlag")
    String getMobilityFlag();

    @ZenMethod
    @ZenOperator(OperatorType.COMPARE)
    int compare(IBlockState other);

	Object getInternal();
}
