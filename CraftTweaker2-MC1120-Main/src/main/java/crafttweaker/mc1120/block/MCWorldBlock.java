package crafttweaker.mc1120.block;

import crafttweaker.api.block.BlockPatternOr;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.block.IBlockPattern;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Collections;
import java.util.List;

/**
 * @author Stan
 */
public class MCWorldBlock implements IBlock {

    private final IBlockAccess blocks;
    private final BlockPos pos;

    public MCWorldBlock(IBlockAccess blocks, int x, int y, int z) {
        this.blocks = blocks;
        this.pos = new BlockPos(x, y, z);
    }

    @Override
    public IBlockDefinition getDefinition() {
        return CraftTweakerMC.getBlockDefinition(blocks.getBlockState(pos).getBlock());
    }

    @Override
    public int getMeta() {
        return blocks.getBlockState(pos).getBlock().getMetaFromState(blocks.getBlockState(pos));
    }

    @Override
    public IData getTileData() {
        TileEntity tileEntity = blocks.getTileEntity(pos);

        if (tileEntity == null)
            return null;

        NBTTagCompound nbt = new NBTTagCompound();
        tileEntity.writeToNBT(nbt);
        return CraftTweakerMC.getIData(nbt);
    }

    @Override
    public String getDisplayName() {
        Block block = blocks.getBlockState(pos).getBlock();
        Item item = Item.getItemFromBlock(block);
        return (new ItemStack(item, 1, getMeta())).getDisplayName();
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
