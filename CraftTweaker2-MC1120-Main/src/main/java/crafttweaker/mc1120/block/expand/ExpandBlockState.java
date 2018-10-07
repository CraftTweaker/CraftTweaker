package crafttweaker.mc1120.block.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.mc1120.brackets.BracketHandlerBlockState;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.block.IBlockState")
@ZenRegister
public class ExpandBlockState {

    @ZenMethodStatic
    public static IBlockState getBlockState(String blockname, String... properties) {
        return BracketHandlerBlockState.getBlockState(blockname, String.join(":", properties));
    }

}