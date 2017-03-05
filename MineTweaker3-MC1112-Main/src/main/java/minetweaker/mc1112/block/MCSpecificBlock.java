package minetweaker.mc1112.block;

import minetweaker.api.block.*;
import minetweaker.api.data.IData;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.block.Block;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

/**
 * @author Stan
 */
public class MCSpecificBlock implements IBlock {

    private final Block block;
    private final int meta;

    public MCSpecificBlock(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    private static String getBlockId(Block block) {
        return Block.REGISTRY.getNameForObject(block).toString();
    }

    @Override
    public IBlockDefinition getDefinition() {
        return MineTweakerMC.getBlockDefinition(block);
    }

    @Override
    public int getMeta() {
        return meta;
    }

    @Override
    public IData getTileData() {
        return null;
    }

    @Override
    public List<IBlock> getBlocks() {
        return Collections.singletonList(this);
    }

    @Override
    public boolean matches(IBlock block) {
        return block.getDefinition() == getDefinition() && (meta == OreDictionary.WILDCARD_VALUE || block.getMeta() == meta);
    }

    @Override
    public IBlockPattern or(IBlockPattern pattern) {
        return new BlockPatternOr(this, pattern);
    }

    @Override
    public String getDisplayName() {
        return block.getLocalizedName();
    }

    @Override
    public String toString() {
        return "<block:" + getBlockId(block) + ":" + (meta == OreDictionary.WILDCARD_VALUE ? '*' : meta) + ">";
    }
}
