package minetweaker.api.block;

import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("minetweaker.block.IBlockPattern")
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
