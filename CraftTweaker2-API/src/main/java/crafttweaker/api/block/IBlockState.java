package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.block.IBlockState")
@ZenRegister
public interface IBlockState extends IBlockProperties, IBlockStateMatcher {
    
    @ZenMethod
    @ZenGetter("block")
    IBlock getBlock();
    
    @ZenMethod
    @ZenGetter("meta")
    int getMeta();
    
    @ZenMethod
    boolean isReplaceable(IWorld world, IBlockPos blockPos);
    
    @ZenMethod
    @ZenOperator(OperatorType.COMPARE)
    int compare(IBlockState other);

    @ZenMethod
    IBlockState withProperty(String name, String value);
}
