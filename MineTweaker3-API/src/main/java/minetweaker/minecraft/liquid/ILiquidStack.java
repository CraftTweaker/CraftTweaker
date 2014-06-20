/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.liquid;

import minetweaker.minecraft.data.IData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stanneke
 */
@ZenClass("minecraft.liquid.ILiquidStack")
public interface ILiquidStack {
	@ZenGetter("definition")
	public ILiquidDefinition getDefinition();
	
	@ZenGetter("name")
	public String getName();
	
	@ZenGetter("displayName")
	public String getDisplayName();
	
	@ZenGetter("amount")
	public int getAmount();
	
	@ZenGetter("luminosity")
	public int getLuminosity();
	
	@ZenGetter("density")
	public int getDensity();
	
	@ZenGetter("temperature")
	public int getTemperature();
	
	@ZenGetter("viscosity")
	public int getViscosity();
	
	@ZenGetter("gaseous")
	public boolean isGaseous();
	
	@ZenGetter("tag")
	public IData getTag();
	
	@ZenMethod
	public ILiquidStack withTag(IData data);
	
	@ZenMethod
	public ILiquidStack withAmount(int amount);
	
	public Object getInternal();
}
