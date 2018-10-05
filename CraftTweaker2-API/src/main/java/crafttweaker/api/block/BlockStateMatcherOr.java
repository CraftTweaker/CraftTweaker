package crafttweaker.api.block;

import crafttweaker.util.ArrayUtil;

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
        return null;
    }

    @Override
    public IBlockStateMatcher or(IBlockStateMatcher matcher) {
        return new BlockStateMatcherOr(ArrayUtil.append(elements, matcher));
    }
}
