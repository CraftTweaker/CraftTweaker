/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.liquid;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenOperator;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 *
 * @author Stanneke
 */
@ZenClass("minetweaker.liquid.ILiquidDefinition")
public interface ILiquidDefinition {
	@ZenOperator(OperatorType.MUL)
	public ILiquidStack asStack(int millibuckets);
	
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
}
