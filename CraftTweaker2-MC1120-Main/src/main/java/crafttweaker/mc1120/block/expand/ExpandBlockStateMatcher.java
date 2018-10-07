package crafttweaker.mc1120.block.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.block.IBlockStateMatcher;
import crafttweaker.mc1120.block.BlockStateMatcher;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.block.IBlockStateMatcher")
@ZenRegister
public class ExpandBlockStateMatcher {

    @ZenMethodStatic
    public static IBlockStateMatcher create(IBlockState... blockStates) {
        return BlockStateMatcher.create(blockStates);
    }

}