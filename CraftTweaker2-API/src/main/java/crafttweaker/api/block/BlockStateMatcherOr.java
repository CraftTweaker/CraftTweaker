package crafttweaker.api.block;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.util.ArrayUtil;

import java.util.*;

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

    @Override
    public IBlockStateMatcher allowValuesForProperty(String name, String... values) {
        CraftTweakerAPI.logWarning("IBlockStateMatcher#allowValuesForProperty is deprecated. Please use IBlockStateMatcher#withMatchedValuesForProperty instead.");
        return withMatchedValuesForProperty(name, values);
    }

    @Override
    public IBlockStateMatcher withMatchedValuesForProperty(String name, String... values) {
        CraftTweakerAPI.logWarning("Cannot change matched values for a compound blockstate matcher");
        return this;
    }

    @Override
    public List<String> getMatchedValuesForProperty(String name) {
        CraftTweakerAPI.logWarning("Cannot retrieve matched values for a compound blockstate matcher");
        return new ArrayList<>();
    }

    @Override
    public Map<String, List<String>> getMatchedProperties() {
        CraftTweakerAPI.logWarning("Cannot retrieve matched values for a compound blockstate matcher");
        return new HashMap<>();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" | ", "Matcher (", ")");
        for (IBlockStateMatcher element : elements) {
            joiner.add(element.toString());
        }
        return joiner.toString();
    }

    @Override
    public boolean isCompound() {
        return true;
    }
}
