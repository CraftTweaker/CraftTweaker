package crafttweaker.api.liquid;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenClass("crafttweaker.liquid.ILiquidStack")
@ZenRegister
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
