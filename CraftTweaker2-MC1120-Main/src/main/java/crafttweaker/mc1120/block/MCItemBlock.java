package crafttweaker.mc1120.block;

import crafttweaker.api.block.*;
import crafttweaker.api.data.IData;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
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
        return CraftTweakerMC.getBlockDefinition(Block.getBlockFromItem(item.getItem()));
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
        
        return CraftTweakerMC.getIData(item.getTagCompound());
    }
    
    @Override
    public ILiquidDefinition getFluid() {
        return CraftTweakerMC.getILiquidDefinition(FluidRegistry.lookupFluidForBlock(Block.getBlockFromItem(item.getItem())));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCItemBlock that = (MCItemBlock) o;
        return new MCItemStack(item).matches(new MCItemStack(that.item));
    }

    @Override
    public int hashCode() {
        return Objects.hash(item.getItem(), item.getItemDamage(), item.getCount(), item.getTagCompound());
    }
}
