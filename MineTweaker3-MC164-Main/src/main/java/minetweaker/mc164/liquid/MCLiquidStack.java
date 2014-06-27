/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.liquid;

import minetweaker.mc164.data.NBTConverter;
import minetweaker.api.data.IData;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

/**
 *
 * @author Stan
 */
public class MCLiquidStack implements ILiquidStack {
	private final FluidStack stack;
	private IData tag = null;
	
	public MCLiquidStack(FluidStack stack) {
		this.stack = stack;
	}
	
	private MCLiquidStack(FluidStack stack, IData tag) {
		this.stack = stack;
		this.tag = tag;
	}

	@Override
	public ILiquidDefinition getDefinition() {
		return new MCLiquidDefinition(stack.getFluid());
	}
	
	@Override
	public String getName() {
		return stack.getFluid().getName();
	}
	
	@Override
	public String getDisplayName() {
		return stack.getFluid().getLocalizedName();
	}

	@Override
	public int getAmount() {
		return stack.amount;
	}

	@Override
	public IData getTag() {
		if (tag == null) {
			if (stack.tag == null) {
				return null;
			}
			
			tag = NBTConverter.from(stack.tag, true);
		}
		
		return tag;
	}

	@Override
	public ILiquidStack withTag(IData data) {
		FluidStack result = new FluidStack(stack.fluidID, stack.amount);
		result.tag = (NBTTagCompound) NBTConverter.from(data);
		return new MCLiquidStack(result, data.immutable());
	}

	@Override
	public ILiquidStack withAmount(int amount) {
		FluidStack result = new FluidStack(stack.fluidID, amount);
		result.tag = stack.tag;
		return new MCLiquidStack(result, tag);
	}

	@Override
	public int getLuminosity() {
		return stack.getFluid().getLuminosity(stack);
	}

	@Override
	public int getDensity() {
		return stack.getFluid().getDensity(stack);
	}

	@Override
	public int getTemperature() {
		return stack.getFluid().getTemperature(stack);
	}

	@Override
	public int getViscosity() {
		return stack.getFluid().getViscosity(stack);
	}

	@Override
	public boolean isGaseous() {
		return stack.getFluid().isGaseous(stack);
	}

	@Override
	public Object getInternal() {
		return stack;
	}
}
