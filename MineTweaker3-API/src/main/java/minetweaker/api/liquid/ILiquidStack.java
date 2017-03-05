package minetweaker.api.liquid;

import minetweaker.api.data.IData;
import minetweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenClass("minetweaker.liquid.ILiquidStack")
public interface ILiquidStack extends IIngredient {
    
    @ZenGetter("definition")
    ILiquidDefinition getDefinition();
    
    @ZenGetter("name")
    String getName();
    
    @ZenGetter("displayName")
    String getDisplayName();
    
    @ZenGetter("amount")
    int getAmount();
    
    @ZenGetter("luminosity")
    int getLuminosity();
    
    @ZenGetter("density")
    int getDensity();
    
    @ZenGetter("temperature")
    int getTemperature();
    
    @ZenGetter("viscosity")
    int getViscosity();
    
    @ZenGetter("gaseous")
    boolean isGaseous();
    
    @ZenGetter("tag")
    IData getTag();
    
    @ZenMethod
    ILiquidStack withTag(IData data);
    
    @ZenOperator(OperatorType.MUL)
    @ZenMethod
    ILiquidStack withAmount(int amount);
    
    Object getInternal();
}
