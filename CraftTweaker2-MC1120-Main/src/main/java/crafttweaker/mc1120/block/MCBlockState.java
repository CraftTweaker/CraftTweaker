package crafttweaker.mc1120.block;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import crafttweaker.CraftTweakerAPI;
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
        if (property == null) {
            CraftTweakerAPI.logWarning("Invalid property name");
        } else {
            //noinspection unchecked
            Optional<? extends Comparable> propValue = property.parseValue(value);
            if (propValue.isPresent()) {
                //noinspection unchecked
                return new MCBlockState(blockState.withProperty(property, propValue.get()));
            }
            CraftTweakerAPI.logWarning("Invalid property value");
        }
        return this;
    }

    @Override
    public List<String> getPropertyNames() {
        return null;
    }

    @Override
    public String getPropertyValue(String name) {
        IProperty prop = blockState.getBlock().getBlockState().getProperty(name);
        if (prop != null) {
            //noinspection unchecked
            return blockState.getValue(prop).toString();
        }
        CraftTweakerAPI.logWarning("Invalid property name");
        return "";
    }

    @Override
    public List<String> getAllowedValuesForProperty(String name) {
        IProperty prop = blockState.getBlock().getBlockState().getProperty(name);
        List<String> values = new ArrayList<>();
        //noinspection unchecked
        prop.getAllowedValues().forEach(v -> values.add(v.toString()));
        return values;
    }

    @Override
    public Map<String, String> getProperties() {
        Map<String, String> props = new HashMap<>();
        for (Map.Entry<IProperty<?>, Comparable<?>> entry : blockState.getProperties().entrySet()) {
            props.put(entry.getKey().getName(), entry.getValue().toString());
        }
        return ImmutableMap.copyOf(props);
    }

    @Override
    public boolean matches(crafttweaker.api.block.IBlockState other) {
        return compare(other) == 0;
    }

    @Override
    public IBlockStateMatcher or(IBlockStateMatcher matcher) {
        return new BlockStateMatcherOr(this, matcher);
    }

    @Override
    public Collection<crafttweaker.api.block.IBlockState> getMatchingBlockStates() {
        return ImmutableList.of(this);
    }

    @Override
    public IBlockStateMatcher allowValuesForProperty(String name, String... values) {
        CraftTweakerAPI.logWarning("IBlockStateMatcher#allowValuesForProperty is deprecated. Please use IBlockStateMatcher#withMatchedValuesForProperty instead.");
        return withMatchedValuesForProperty(name, values);
    }

    @Override
    public IBlockStateMatcher withMatchedValuesForProperty(String name, String... values) {
        Map<String, List<String>> newProps = new HashMap<>();
        newProps.put(name, ImmutableList.copyOf(values));
        return new BlockStateMatcher(this, newProps);
    }

    @Override
    public List<String> getMatchedValuesForProperty(String name) {
        return ImmutableList.of(getPropertyValue(name));
    }

    @Override
    public Map<String, List<String>> getMatchedProperties() {
        Map<String, List<String>> props = new HashMap<>();
        for(Map.Entry<String,String> entry : getProperties().entrySet()) {
            props.put(entry.getKey(), ImmutableList.of(entry.getValue()));
        }
        return ImmutableMap.copyOf(props);
    }

    @Override
    public boolean isCompound() {
        return false;
    }
}
