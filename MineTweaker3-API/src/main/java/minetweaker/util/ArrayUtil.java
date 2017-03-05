package minetweaker.util;

import minetweaker.api.block.IBlockPattern;
import minetweaker.api.item.*;

import java.util.Arrays;

/**
 * @author Stan
 */
public class ArrayUtil {
    
    public static final IItemCondition[] EMPTY_CONDITIONS = new IItemCondition[0];
    public static final IItemTransformer[] EMPTY_TRANSFORMERS = new IItemTransformer[0];
    
    public static IItemCondition[] append(IItemCondition[] values, IItemCondition value) {
        IItemCondition[] result = Arrays.copyOf(values, values.length + 1);
        result[values.length] = value;
        return result;
    }
    
    public static IItemTransformer[] append(IItemTransformer[] values, IItemTransformer value) {
        IItemTransformer[] result = Arrays.copyOf(values, values.length + 1);
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
}
