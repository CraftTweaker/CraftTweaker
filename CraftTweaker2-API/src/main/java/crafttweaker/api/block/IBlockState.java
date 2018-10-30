package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;
import java.util.Map;

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

    @ZenMethod
    List<String> getPropertyNames();

    @ZenMethod
    String getPropertyValue(String name);

    @ZenMethod
    List<String> getAllowedValuesForProperty(String name);

    @ZenMethod
    Map<String, String> getProperties();
}
