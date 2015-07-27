package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import gregtech.api.GregTech_API;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the Chemical Reactor recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.ChemicalReactor")
@ModOnly("gregtech")
public class ChemicalReactor {
	/**
	 * Adds a Chemical Reactor recipe.
	 * 
	 * @param output recipe output
	 * @param input1 primary input
	 * @param input2 secondary input
	 * @param durationTicks reaction time, in ticks
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input1, IItemStack input2, int durationTicks) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input1, input2, durationTicks));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack output, ILiquidStack lOutput, IItemStack input1, IItemStack input2, ILiquidStack lInput, int dur) {
		MineTweakerAPI.apply(new AddFluidRecipeAction(output, lOutput, input1, input2, lInput, dur));
	}
	
	private static final class AddFluidRecipeAction extends OneWayAction {
		private IItemStack output;
		private ILiquidStack lOutput;
		private IItemStack input1;
		private IItemStack input2;
		private ILiquidStack lInput;
		private int dur;

		public AddFluidRecipeAction(IItemStack output, ILiquidStack lOutput, IItemStack input1, IItemStack input2,
				ILiquidStack lInput, int dur) {
					this.output = output;
			// TODO Auto-generated constructor stub
					this.lOutput = lOutput;
					this.input1 = input1;
					this.input2 = input2;
					this.lInput = lInput;
					this.dur = dur;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding chemical reaction w/ fluids";
		}

		@Override
		public void apply() {
			RA.addChemicalRecipe(MineTweakerMC.getItemStack(input1)
					,MineTweakerMC.getItemStack(input2)
					, MineTweakerMC.getLiquidStack(lInput)
					, MineTweakerMC.getLiquidStack(lOutput)
					, MineTweakerMC.getItemStack(output)
					, dur);
		}
	}

	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output;
		private final IItemStack input1;
		private final IItemStack input2;
		private final int duration;
		
		public AddRecipeAction(IItemStack output, IItemStack input1, IItemStack input2, int duration) {
			this.output = output;
			this.input1 = input1;
			this.input2 = input2;
			this.duration = duration;
		}

		@Override
		public void apply() {
			RA.addChemicalRecipe(
					MineTweakerMC.getItemStack(input1),
					MineTweakerMC.getItemStack(input2),
					MineTweakerMC.getItemStack(output),
					duration);
		}

		@Override
		public String describe() {
			return "Adding chemical reactor recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 11 * hash + (this.output != null ? this.output.hashCode() : 0);
			hash = 11 * hash + (this.input1 != null ? this.input1.hashCode() : 0);
			hash = 11 * hash + (this.input2 != null ? this.input2.hashCode() : 0);
			hash = 11 * hash + this.duration;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final AddRecipeAction other = (AddRecipeAction) obj;
			if (this.output != other.output && (this.output == null || !this.output.equals(other.output))) {
				return false;
			}
			if (this.input1 != other.input1 && (this.input1 == null || !this.input1.equals(other.input1))) {
				return false;
			}
			if (this.input2 != other.input2 && (this.input2 == null || !this.input2.equals(other.input2))) {
				return false;
			}
			if (this.duration != other.duration) {
				return false;
			}
			return true;
		}
	}
}
