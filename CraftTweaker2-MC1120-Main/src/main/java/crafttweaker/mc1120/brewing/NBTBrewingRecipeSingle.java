package crafttweaker.mc1120.brewing;

import crafttweaker.api.data.IData;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipe;

public class NBTBrewingRecipeSingle extends BrewingRecipe {

	private IData inTag;
	
	public NBTBrewingRecipeSingle(ItemStack input, ItemStack ingredient, ItemStack output) {
		super(input, ingredient, output);
		this.inTag = NBTConverter.from(input.getTagCompound(),false);
	}
	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		IData testTag = NBTConverter.from(input.getTagCompound(), true);
		if (testTag == null) return ItemStack.EMPTY;
		boolean equal = testTag.contains(inTag);
		return equal ? super.getOutput(input, ingredient) : ItemStack.EMPTY;
	}
}
