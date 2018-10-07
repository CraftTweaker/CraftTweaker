package crafttweaker.util;

import crafttweaker.api.block.IBlockPattern;
import crafttweaker.api.block.IBlockStateMatcher;
import crafttweaker.api.item.*;

import java.util.Arrays;

/**
 * @author Stan
 */
public class ArrayUtil {
    
    public static final IItemCondition[] EMPTY_CONDITIONS = new IItemCondition[0];
    public static final IItemTransformerNew[] EMPTY_TRANSFORMERS = new IItemTransformerNew[0];
    public static final IItemTransformer[] EMPTY_TRANSFORMERS_NEW = new IItemTransformer[0];
    
    public static IItemCondition[] append(IItemCondition[] values, IItemCondition value) {
        IItemCondition[] result = Arrays.copyOf(values, values.length + 1);
        result[values.length] = value;
        return result;
    }
    
    public static IItemTransformerNew[] append(IItemTransformerNew[] values, IItemTransformerNew value) {
        IItemTransformerNew[] result = Arrays.copyOf(values, values.length + 1);
        result[values.length] = value;
        return result;
    }
    
    public static IIngredient[] append(IIngredient[] values, IIngredient value) {
        IIngredient[] result = Arrays.copyOf(values, values.length + 1);
        result[values.length] = value;
        return result;
    }
    
    public static IBlockPattern[] append(IBlockPattern[] values, IBlockPattern value) {
        IBlockPattern[] result = Arrays.copyOf(values, values.length + 1);
        result[values.length] = value;
        return result;
    }

    public static IBlockStateMatcher[] append(IBlockStateMatcher[] values, IBlockStateMatcher value) {
        IBlockStateMatcher[] result = Arrays.copyOf(values, values.length + 1);
        result[values.length] = value;
        return result;
    }
}
