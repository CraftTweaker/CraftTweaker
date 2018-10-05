package crafttweaker.mc1120.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.*;
import java.util.stream.Collectors;

@ZenClass("crafttweaker.mc1120.block.BlockStateMatcher")
@ZenRegister
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

    @ZenMethod
    public static IBlockStateMatcher create(crafttweaker.api.block.IBlockState... blockStates) {
        if (blockStates == null || blockStates.length == 0) return null;
        if (blockStates.length == 1) {
            return new BlockStateMatcher(blockStates[0]);
        } else {
            return new BlockStateMatcherOr(Arrays.copyOf(blockStates, blockStates.length));
        }
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

    @Override
    public Collection<crafttweaker.api.block.IBlockState> getMatchingBlockStates() {
        IBlockState state = ((IBlockState) blockState.getInternal());
        return state.getBlock().getBlockState().getValidStates()
                .stream()
                .map(MCBlockState::new)
                .collect(Collectors.toList());
    }
}
