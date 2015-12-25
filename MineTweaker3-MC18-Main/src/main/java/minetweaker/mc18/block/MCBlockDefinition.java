/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18.block;

import minetweaker.api.block.IBlockDefinition;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author Stan
 */
public class MCBlockDefinition implements IBlockDefinition {
	private final Block block;

	public MCBlockDefinition(Block block) {
		this.block = block;
	}

	public Block getInternalBlock() {
		return block;
	}

	@Override
	public String getId() {
		ResourceLocation res = Block.blockRegistry.getNameForObject(block);
		return res.getResourceDomain()+ ":"+res.getResourcePath();
	}

	@Override
	public String getDisplayName() {
		return block.getLocalizedName();
	}
}
