package crafttweaker.mc1120.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.*;
import java.util.stream.Collectors;

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

    public static IBlockStateMatcher create(crafttweaker.api.block.IBlockState... blockStates) {
        if (blockStates == null) {
            throw new IllegalArgumentException("Cannot create matcher for null blockstate");
        }
        for (Object o : blockStates) {
            if (o == null) {
                throw new IllegalArgumentException("Cannot create matcher for null blockstate");
            }
        }
        if (blockStates.length == 1) {
            return new BlockStateMatcher(blockStates[0]);
        } else {
            return new BlockStateMatcherOr(Arrays.copyOf(blockStates, blockStates.length));
        }
    }

    @Override
    public boolean matches(crafttweaker.api.block.IBlockState toMatch) {
        if (toMatch == null) {
            return false;
        }
        // If internal states are equal, they must match
        if (this.blockState.getInternal().equals(toMatch.getInternal())) {
            return true;
        // Otherwise if the internal Blocks are equal, there is a possible match
        } else if (((IBlockState) this.blockState.getInternal()).getBlock().equals(((IBlockState) toMatch.getInternal()).getBlock())) {
            // Iterate over the properties in `toMatch`
            for (Map.Entry<IProperty<?>, Comparable<?>> entry : ((IBlockState) toMatch.getInternal()).getProperties().entrySet()) {
                // If this matcher has values specified for this property name, and the value is not in the list of allowed values
                List<String> allowed = allowedProperties.get(entry.getKey().getName());
                if (allowed != null && !(allowed.contains(entry.getValue().toString()) || allowed.contains("*"))) {
                    // No dice.
                    return false;
                }
            }
            // Every property was either not specified, wildcarded, or matched an allowed value
            return true;
        }
        return false;
    }

    @Override
    public IBlockStateMatcher allowValuesForProperty(String name, String... values) {
        // Create a new map
        Map<String, List<String>> newProps = new HashMap<>();
        // Copy over current values
        for (Map.Entry<String, List<String>> entry : allowedProperties.entrySet()) {
            newProps.put(entry.getKey(), ImmutableList.copyOf(entry.getValue()));
        }
        // Put the specified allowed values into the map
        newProps.put(name, ImmutableList.copyOf(values));
        // Wham, bam, thank-you-ma'am
        return new BlockStateMatcher(blockState, newProps);
    }

    @Override
    public IBlockStateMatcher or(IBlockStateMatcher matcher) {
        return new BlockStateMatcherOr(this, matcher);
    }

    @Override
    public Collection<crafttweaker.api.block.IBlockState> getMatchingBlockStates() {
        IBlockState state = ((IBlockState) blockState.getInternal());
        return state.getBlock().getBlockState().getValidStates() // get list of valid states
                .stream()
                .map(MCBlockState::new)     // convert to crafttweaker IBlockStates
                .filter(this::matches)      // filter for matching states
                .collect(Collectors.toList());  // collect and return
    }
}
