/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.block;

import java.util.ArrayList;
import java.util.List;
import minetweaker.util.ArrayUtil;

/**
 *
 * @author Stan
 */
public class BlockPatternOr implements IBlockPattern {
	private final IBlockPattern[] elements;

	public BlockPatternOr(IBlockPattern[] elements) {
		this.elements = elements;
	}

	public BlockPatternOr(IBlockPattern a, IBlockPattern b) {
		this.elements = new IBlockPattern[] { a, b };
	}

	@Override
	public List<IBlock> getBlocks() {
		List<IBlock> blocks = new ArrayList<IBlock>();
		for (IBlockPattern pattern : elements) {
			blocks.addAll(pattern.getBlocks());
		}
		return blocks;
	}

	@Override
	public boolean matches(IBlock block) {
		for (IBlockPattern pattern : elements) {
			if (pattern.matches(block))
				return true;
		}

		return false;
	}

	@Override
	public String getDisplayName() {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (IBlockPattern pattern : elements) {
			if (first) {
				first = false;
			} else {
				result.append(" | ");
			}

			result.append(pattern.getDisplayName());
		}

		return result.toString();
	}

	@Override
	public IBlockPattern or(IBlockPattern pattern) {
		return new BlockPatternOr(ArrayUtil.append(elements, pattern));
	}
}
