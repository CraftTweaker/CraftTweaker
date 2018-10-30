package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@ZenClass("crafttweaker.block.IBlockStateMatcher")
@ZenRegister
public interface IBlockStateMatcher {
    @ZenMethod
    @ZenOperator(OperatorType.CONTAINS)
    boolean matches(IBlockState blockState);

    @ZenOperator(OperatorType.OR)
    IBlockStateMatcher or(IBlockStateMatcher matcher);

    @ZenMethod
    Collection<IBlockState> getMatchingBlockStates();

    @Deprecated
    @ZenMethod
    IBlockStateMatcher allowValuesForProperty(String name, String... values);

    @ZenMethod
    IBlockStateMatcher withMatchedValuesForProperty(String name, String... values);

    @ZenMethod
    List<String> getMatchedValuesForProperty(String name);

    @ZenMethod
    Map<String, List<String>> getMatchedProperties();

    @ZenMethod
    boolean isCompound();
}
