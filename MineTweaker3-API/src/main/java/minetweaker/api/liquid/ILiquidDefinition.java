package minetweaker.api.liquid;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * Contains a liquid definition. Liquid definitions provide additional
 * information about liquids.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.liquid.ILiquidDefinition")
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

    @ZenGetter("containers")
    List<IItemStack> getContainers();

    @ZenMethod
    void addContainer(IItemStack filled, IItemStack empty, int amount);

    @ZenMethod
    void removeContainer(IItemStack filled);
}
