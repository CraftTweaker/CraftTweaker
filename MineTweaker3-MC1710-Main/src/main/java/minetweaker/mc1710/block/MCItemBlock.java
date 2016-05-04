/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.block;

import java.util.Collections;
import java.util.List;
import minetweaker.api.block.BlockPatternOr;
import minetweaker.api.block.IBlock;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.block.IBlockPattern;
import minetweaker.api.data.IData;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class MCItemBlock implements IBlock {
	private final ItemStack item;

	public MCItemBlock(ItemStack item) {
		this.item = item;
	}

	@Override
	public IBlockDefinition getDefinition() {
		return MineTweakerMC.getBlockDefinition(Block.getBlockFromItem(item.getItem()));
	}

	@Override
	public int getMeta() {
		return item.getItemDamage();
	}

	@Override
	public IData getTileData() {
		if (item.stackTagCompound == null)
			return null;

		return MineTweakerMC.getIData(item.stackTagCompound);
	}

	@Override
	public String getDisplayName() {
		return item.getDisplayName();
	}

	@Override
	public List<IBlock> getBlocks() {
		return Collections.<IBlock>singletonList(this);
	}

	@Override
	public boolean matches(IBlock block) {
		return getDefinition() == block.getDefinition()
				&& (getMeta() == OreDictionary.WILDCARD_VALUE || getMeta() == block.getMeta())
				&& (getTileData() == null || (block.getTileData() != null && block.getTileData().contains(getTileData())));
	}

	@Override
	public IBlockPattern or(IBlockPattern pattern) {
		return new BlockPatternOr(this, pattern);
	}
}
