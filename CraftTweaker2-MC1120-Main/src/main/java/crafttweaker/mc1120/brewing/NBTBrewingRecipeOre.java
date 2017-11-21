package crafttweaker.mc1120.brewing;

import crafttweaker.api.data.IData;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingOreRecipe;
import net.minecraftforge.common.brewing.BrewingRecipe;

public class NBTBrewingRecipeOre extends BrewingOreRecipe {

	private IData inTag;
	
	public NBTBrewingRecipeOre(ItemStack input, String ingredient, ItemStack output) {
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
