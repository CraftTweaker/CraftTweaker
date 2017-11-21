package crafttweaker.mc1120.brewing;

import java.util.List;
import java.util.stream.Collectors;

import crafttweaker.api.data.IData;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingOreRecipe;

public class NBTBrewingRecipeOre extends BrewingOreRecipe {

	private final IData inTag;
	private final boolean useNBT;
	
	public NBTBrewingRecipeOre(IItemStack input, String ingredient, IItemStack output, boolean useNBT) {
		super(CraftTweakerMC.getItemStack(input),
				ingredient,
				CraftTweakerMC.getItemStack(output));
		this.inTag = getTag(input, useNBT);
		this.useNBT = useNBT;
	}
	
	public NBTBrewingRecipeOre(IItemStack input, IIngredient ingredient, IItemStack output, boolean useNBT) {
		super(CraftTweakerMC.getItemStack(input),
				ingredient.getItems().stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList()),
				CraftTweakerMC.getItemStack(output));
		this.inTag = getTag(input, useNBT);
		this.useNBT = useNBT;
	}
	
	public NBTBrewingRecipeOre(IItemStack input, List<IItemStack> ingredient, IItemStack output, boolean useNBT) {
		super(CraftTweakerMC.getItemStack(input),
				ingredient.stream().map(CraftTweakerMC::getItemStack).collect(Collectors.toList()),
				CraftTweakerMC.getItemStack(output));
		this.inTag = getTag(input, useNBT);
		this.useNBT = useNBT;
	}
	
	
	
	
	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		if (!useNBT) {
			return super.getOutput(input, ingredient);
		}
		
		IData testTag = NBTConverter.from(input.getTagCompound(), true);
		if (testTag == null) return ItemStack.EMPTY;
		boolean equal = testTag.contains(inTag);
		return equal ? super.getOutput(input, ingredient) : ItemStack.EMPTY;
	}
	
	private IData getTag(IItemStack input, boolean _useNBT) {
		return _useNBT ? input.getTag() : null;
	}
}
