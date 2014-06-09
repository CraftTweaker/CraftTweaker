/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.liquid;

import minetweaker.mc172.data.NBTConverter;
import minetweaker.minecraft.data.IData;
import minetweaker.minecraft.liquid.ILiquidDefinition;
import minetweaker.minecraft.liquid.ILiquidStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

/**
 *
 * @author Stan
 */
public class TweakerLiquidStack implements ILiquidStack {
	private final FluidStack stack;
	private IData tag = null;
	
	public TweakerLiquidStack(FluidStack stack) {
		this.stack = stack;
	}
	
	private TweakerLiquidStack(FluidStack stack, IData tag) {
		this.stack = stack;
		this.tag = tag;
	}

	@Override
	public ILiquidDefinition getDefinition() {
		return new TweakerLiquidDefinition(stack.getFluid());
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
		return new TweakerLiquidStack(result, data.immutable());
	}

	@Override
	public ILiquidStack withAmount(int amount) {
		FluidStack result = new FluidStack(stack.fluidID, amount);
		result.tag = stack.tag;
		return new TweakerLiquidStack(result, tag);
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
