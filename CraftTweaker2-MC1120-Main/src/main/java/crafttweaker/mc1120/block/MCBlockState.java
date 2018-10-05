package crafttweaker.mc1120.block;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import crafttweaker.api.block.*;
import crafttweaker.api.world.*;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.*;

public class MCBlockState extends MCBlockProperties implements crafttweaker.api.block.IBlockState {
    
    private final IBlockState blockState;
    private IBlock block;
    
    public MCBlockState(IBlockState blockState) {
        super(blockState);
        this.blockState = blockState;
        Block block = blockState.getBlock();
        int meta = block.getMetaFromState(blockState);
        this.block = new MCSpecificBlock(block, meta);
    }
    
    @Override
    public IBlock getBlock() {
        return block;
    }
    
    @Override
    public int getMeta() {
        return block.getMeta();
    }
    
    @Override
    public boolean isReplaceable(IWorld world, IBlockPos blockPos) {
        return blockState.getBlock().isReplaceable((World) world.getInternal(), (BlockPos) blockPos.getInternal());
    }
    
    @Override
    public int compare(crafttweaker.api.block.IBlockState other) {
        int result = 0;
        if(!this.getInternal().equals(other.getInternal())) {
            if(blockState.getBlock().equals(((IBlockState) other.getInternal()).getBlock())) {
                result = Integer.compare(this.getMeta(), other.getMeta());
            } else {
                int blockId = ((ForgeRegistry<Block>) ForgeRegistries.BLOCKS).getID(((IBlockState) this.getInternal()).getBlock());
                int otherBlockId = ((ForgeRegistry<Block>) ForgeRegistries.BLOCKS).getID(((IBlockState) other.getInternal()).getBlock());
                result = Integer.compare(blockId, otherBlockId);
            }
        }
        return result;
    }

    @Override
    public crafttweaker.api.block.IBlockState withProperty(String name, String value) {
        IProperty property = blockState.getBlock().getBlockState().getProperty(name);
        //noinspection unchecked
        Optional<? extends Comparable> propValue = property.parseValue(value);
        if (propValue.isPresent()) {
            //noinspection unchecked
            return new MCBlockState(blockState.withProperty(property, propValue.get()));
        }
        return null;
    }

    @Override
    public boolean matches(crafttweaker.api.block.IBlockState other) {
        return compare(other) == 0;
    }

    @Override
    public IBlockStateMatcher allowValuesForProperty(String propertyName, String... propertyValues) {
        Map<String, List<String>> newProps = new HashMap<>();
        newProps.put(propertyName, ImmutableList.copyOf(propertyValues));
        return new BlockStateMatcher(this, newProps);
    }

    @Override
    public IBlockStateMatcher or(IBlockStateMatcher matcher) {
        return new BlockStateMatcherOr(this, matcher);
    }

    @Override
    public Collection<crafttweaker.api.block.IBlockState> getMatchingBlockStates() {
        return ImmutableList.of(this);
    }
}
