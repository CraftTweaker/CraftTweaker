package crafttweaker.api.block;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlockStateMatcherOr implements IBlockStateMatcher {

    private final IBlockStateMatcher[] elements;

    public BlockStateMatcherOr(IBlockStateMatcher[] elements) {
        this.elements = elements;
    }

    public BlockStateMatcherOr(IBlockStateMatcher a, IBlockStateMatcher b) {
        this.elements = new IBlockStateMatcher[]{a, b};
    }

    @Override
    public boolean matches(IBlockState blockState) {
        for (IBlockStateMatcher matcher : elements) {
            if (matcher.matches(blockState)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IBlockStateMatcher allowValuesForProperty(String name, String... values) {
        CraftTweakerAPI.logWarning("Cannot change allowed values for a compound blockstate matcher")
        return this;
    }

    @Override
    public IBlockStateMatcher or(IBlockStateMatcher matcher) {
        return new BlockStateMatcherOr(ArrayUtil.append(elements, matcher));
    }

    @Override
    public Collection<IBlockState> getMatchingBlockStates() {
        Collection<IBlockState> matchingStates = new ArrayList<>();
        for (IBlockStateMatcher matcher : elements) {
            Collection<IBlockState> states = matcher.getMatchingBlockStates();
            if (states == null) {
                continue;
            }
            matchingStates.addAll(states);
        }
        return matchingStates;
    }
}
