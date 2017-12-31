package crafttweaker.mc1120.block;

import crafttweaker.api.block.BlockPatternOr;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockPattern;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

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
        return CraftTweakerMC.getBlockDefinition(Block.getBlockFromItem(item.getItem()));
    }

    @Override
    public int getMeta() {
        return item.getItemDamage();
    }

    @Override
    public IData getTileData() {
        if (!item.isEmpty()) {
            return null;
        }
        if (item.getTagCompound() == null)
            return null;

        return CraftTweakerMC.getIData(item.getTagCompound());
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
