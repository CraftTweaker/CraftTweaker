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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class MCWorldBlock implements IBlock {
	private final IBlockAccess blocks;
	private final int x;
	private final int y;
	private final int z;

	public MCWorldBlock(IBlockAccess blocks, int x, int y, int z) {
		this.blocks = blocks;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public IBlockDefinition getDefinition() {
		return MineTweakerMC.getBlockDefinition(blocks.getBlock(x, y, z));
	}

	@Override
	public int getMeta() {
		return blocks.getBlockMetadata(x, y, z);
	}

	@Override
	public IData getTileData() {
		TileEntity tileEntity = blocks.getTileEntity(x, y, z);

		if (tileEntity == null)
			return null;

		NBTTagCompound nbt = new NBTTagCompound();
		tileEntity.writeToNBT(nbt);
		return MineTweakerMC.getIData(nbt);
	}

	@Override
	public String getDisplayName() {
		Block block = blocks.getBlock(x, y, z);
		Item item = Item.getItemFromBlock(block);
		if (item != null) {
			return (new ItemStack(item, 1, getMeta())).getDisplayName();
		} else {
			return block.getLocalizedName();
		}
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
