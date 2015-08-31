/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.block.IBlock;
import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import minetweaker.api.world.IBlockGroup;
import minetweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerFillBucketEvent")
public class PlayerFillBucketEvent {
	private final IPlayer player;
	private final IBlockGroup blocks;
	private final int x;
	private final int y;
	private final int z;
	private boolean canceled;
	private IItemStack result;

	public PlayerFillBucketEvent(IPlayer player, IBlockGroup blocks, int x, int y, int z) {
		this.player = player;
		this.blocks = blocks;
		this.x = x;
		this.y = y;
		this.z = z;

		canceled = false;
		result = null;
	}

	@ZenMethod
	public void cancel() {
		canceled = true;
	}

	@ZenGetter("canceled")
	public boolean isCanceled() {
		return canceled;
	}

	/**
	 * Sets the resulting item. Automatically processes the event, too.
	 * 
	 * @param result resulting item
	 */
	@ZenSetter("result")
	public void setResult(IItemStack result) {
		this.result = result;
	}

	@ZenGetter("result")
	public IItemStack getResult() {
		return result;
	}

	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}

	@ZenGetter("blocks")
	public IBlockGroup getBlocks() {
		return blocks;
	}

	@ZenGetter("x")
	public int getX() {
		return x;
	}

	@ZenGetter("y")
	public int getY() {
		return y;
	}

	@ZenGetter("z")
	public int getZ() {
		return z;
	}

	@ZenGetter("block")
	public IBlock getBlock() {
		return blocks.getBlock(x, y, z);
	}

	@ZenGetter("dimension")
	public IDimension getDimension() {
		return blocks.getDimension();
	}
}
