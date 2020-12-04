package crafttweaker.mc1120.liquid;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.liquid.WeightedLiquidStack;
import stanhebben.zenscript.annotations.*;


@ZenRegister
@ZenExpansion("crafttweaker.liquid.ILiquidStack")
public class ExpandLiquidStack {
    /**
     * Creates a weighted liquid stack with the given percentage chance. Does the
     * same as liquid.weight(p * 0.01).
     *
     * @param p probability, with percent
     *
     * @return weighted liquid stack
     */
    @ZenOperator(OperatorType.MOD)
    public static WeightedLiquidStack percent(ILiquidStack thisStack, float p) {
        return new WeightedLiquidStack(thisStack, p * 0.01f);
    }
    
    /**
     * Creates a weighted liquid stack with the given weight.
     *
     * @param p liquid weight
     *
     * @return weighted liquid stack
     */
    @ZenMethod
    public static WeightedLiquidStack weight(ILiquidStack thisStack, float p) {
        return new WeightedLiquidStack(thisStack, p);
    }
}
