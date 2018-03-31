package crafttweaker.api.liquid;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * Contains a liquid definition. Liquid definitions provide additional
 * information about liquids.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.liquid.ILiquidDefinition")
@ZenRegister
public interface ILiquidDefinition {
    
    /**
     * Converts this liquid into a liquid stack.
     *
     * @param millibuckets item stack size
     *
     * @return resulting item stack
     */
    @ZenOperator(OperatorType.MUL)
    ILiquidStack asStack(int millibuckets);
    
    /**
     * Gets the unlocalized name of this item.
     *
     * @return unlocalized name
     */
    @ZenGetter("name")
    String getName();
    
    @ZenGetter("displayName")
    String getDisplayName();
    
    @ZenGetter("luminosity")
    int getLuminosity();
    
    @ZenSetter("luminosity")
    void setLuminosity(int value);
    
    @ZenGetter("density")
    int getDensity();
    
    @ZenSetter("density")
    void setDensity(int density);
    
    @ZenGetter("temperature")
    int getTemperature();
    
    @ZenSetter("temperature")
    void setTemperature(int temperature);
    
    @ZenGetter("viscosity")
    int getViscosity();
    
    @ZenSetter("viscosity")
    void setViscosity(int viscosity);
    
    @ZenGetter("gaseous")
    boolean isGaseous();
    
    @ZenSetter("gaseous")
    void setGaseous(boolean gaseous);
    
    Object getInternal();
}
