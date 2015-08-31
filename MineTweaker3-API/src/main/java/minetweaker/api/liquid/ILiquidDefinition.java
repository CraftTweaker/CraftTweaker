package minetweaker.api.liquid;

import java.util.List;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenOperator;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

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
	 * @return resulting item stack
	 */
	@ZenOperator(OperatorType.MUL)
	public ILiquidStack asStack(int millibuckets);

	/**
	 * Gets the unlocalized name of this item.
	 * 
	 * @return unlocalized name
	 */
	@ZenGetter("name")
	public String getName();

	@ZenGetter("displayName")
	public String getDisplayName();

	@ZenGetter("luminosity")
	public int getLuminosity();

	@ZenSetter("luminosity")
	public void setLuminosity(int value);

	@ZenGetter("density")
	public int getDensity();

	@ZenSetter("density")
	public void setDensity(int density);

	@ZenGetter("temperature")
	public int getTemperature();

	@ZenSetter("temperature")
	public void setTemperature(int temperature);

	@ZenGetter("viscosity")
	public int getViscosity();

	@ZenSetter("viscosity")
	public void setViscosity(int viscosity);

	@ZenGetter("gaseous")
	public boolean isGaseous();

	@ZenSetter("gaseous")
	public void setGaseous(boolean gaseous);

	@ZenGetter("containers")
	public List<IItemStack> getContainers();

	@ZenMethod
	public void addContainer(IItemStack filled, IItemStack empty, int amount);

	@ZenMethod
	public void removeContainer(IItemStack filled);
}
