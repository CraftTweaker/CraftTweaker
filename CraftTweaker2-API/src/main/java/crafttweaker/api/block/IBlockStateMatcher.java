package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

import java.util.Collection;
import java.util.List;

@ZenClass("crafttweaker.block.IBlockStateMatcher")
@ZenRegister
public interface IBlockStateMatcher {
    @ZenMethod
    boolean matches(IBlockState blockState);

    @ZenMethod
    IBlockStateMatcher allowValuesForProperty(String name, String... values);

    @ZenOperator(OperatorType.OR)
    IBlockStateMatcher or(IBlockStateMatcher matcher);

    @ZenMethod
    Collection<IBlockState> getMatchingBlockStates();
}
