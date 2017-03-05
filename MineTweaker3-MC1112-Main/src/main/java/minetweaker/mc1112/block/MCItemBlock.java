package minetweaker.mc1112.block;

import minetweaker.api.block.*;
import minetweaker.api.data.IData;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

/**
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
        if(!item.isEmpty()) {
            return null;
        }
        if(item.getTagCompound() == null)
            return null;

        return MineTweakerMC.getIData(item.getTagCompound());
    }

    @Override
    public String getDisplayName() {
        return item.getDisplayName();
    }

    @Override
    public List<IBlock> getBlocks() {
        return Collections.singletonList(this);
    }

    @Override
    public boolean matches(IBlock block) {
        return getDefinition() == block.getDefinition() && (getMeta() == OreDictionary.WILDCARD_VALUE || getMeta() == block.getMeta()) && (getTileData() == null || (block.getTileData() != null && block.getTileData().contains(getTileData())));
    }

    @Override
    public IBlockPattern or(IBlockPattern pattern) {
        return new BlockPatternOr(this, pattern);
    }
}
