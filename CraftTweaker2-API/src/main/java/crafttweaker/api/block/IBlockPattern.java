package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.block.IBlockPattern")
@ZenRegister
public interface IBlockPattern {
    
    @ZenMethod("blocks")
    List<IBlock> getBlocks();
    
    @ZenOperator(OperatorType.CONTAINS)
    boolean matches(IBlock block);
    
    @ZenOperator(OperatorType.OR)
    IBlockPattern or(IBlockPattern pattern);
    
    @ZenGetter("displayName")
    String getDisplayName();
    
}
