/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
