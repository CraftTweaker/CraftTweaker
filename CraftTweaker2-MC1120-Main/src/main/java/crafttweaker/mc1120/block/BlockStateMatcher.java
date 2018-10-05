package crafttweaker.mc1120.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import crafttweaker.api.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockStateMatcher implements IBlockStateMatcher {

    private final crafttweaker.api.block.IBlockState blockState;
    private final Map<String, List<String>> allowedProperties;

    public BlockStateMatcher(crafttweaker.api.block.IBlockState blockState, Map<String, List<String>> allowedProperties) {
        this.blockState = blockState;
        this.allowedProperties = ImmutableMap.copyOf(allowedProperties);
    }

    public BlockStateMatcher(crafttweaker.api.block.IBlockState blockState) {
        this(blockState, ImmutableMap.of());
    }

    @Override
    public boolean matches(crafttweaker.api.block.IBlockState other) {
        if (this.blockState.getInternal().equals(other.getInternal())) {
            return true;
        } else if (((IBlockState) this.blockState.getInternal()).getBlock().equals(((IBlockState) other.getInternal()).getBlock())) {
            for (Map.Entry<IProperty<?>, Comparable<?>> entry : ((IBlockState)other.getInternal()).getProperties().entrySet()) {
                if (allowedProperties.containsKey(entry.getKey().getName())) {
                    if (!allowedProperties.get(entry.getKey().getName()).contains(entry.getValue().toString())) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public IBlockStateMatcher allowValuesForProperty(String name, String... values) {
        Map<String, List<String>> newProps = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : allowedProperties.entrySet()) {
            newProps.put(entry.getKey(), ImmutableList.copyOf(entry.getValue()));
        }
        newProps.put(name, ImmutableList.copyOf(values));
        return new BlockStateMatcher(this.blockState, newProps);
    }

    @Override
    public IBlockStateMatcher or(IBlockStateMatcher matcher) {
        return new BlockStateMatcherOr(this, matcher);
    }
}
