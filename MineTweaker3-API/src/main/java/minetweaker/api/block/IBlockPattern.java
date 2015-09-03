/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.block;

import java.util.List;

import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.block.IBlockPattern")
public interface IBlockPattern {
	@ZenMethod("blocks")
	public List<IBlock> getBlocks();

	@ZenOperator(OperatorType.CONTAINS)
	public boolean matches(IBlock block);

	@ZenOperator(OperatorType.OR)
	public IBlockPattern or(IBlockPattern pattern);

	@ZenGetter("displayName")
	public String getDisplayName();

}
