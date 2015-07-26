package minetweaker.mods.ic2.crops;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.common.BiomeDictionary;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
/**
 * Provides access to IC2 crop system in general
 * @author ben
 *
 */
import stanhebben.zenscript.annotations.ZenMethod;
@ZenClass("mods.ic2.CropsConfig")
@ModOnly("IC2")
public class CropsConfig {
	/**
	 * Action to add a new seed for a base crop
	 * @author ben
	 *
	 */
	private static final class RegisterBaseSeedAction extends OneWayAction {
		private IItemStack seed;
		private CropCard c;
		private int sz;
		private int growth;
		private int gain;
		private int resist;

		public RegisterBaseSeedAction(IItemStack seed, CropCard c, int sz, int growth, int gain, int resist) {
			this.seed = seed;
			this.c = c;
			this.sz = sz;
			this.growth = growth;
			this.gain = gain;
			this.resist = resist;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Registering base seed for crop " + c.name();
		}

		@Override
		public void apply() {
			Crops.instance.registerBaseSeed(MineTweakerMC.getItemStack(seed), c, sz, growth, gain, resist);
		}
	}
	


	/**
	 * Action to register a new crop
	 * @author ben
	 *
	 */
	private static final class RegisterCropCardAction extends OneWayAction {
		private CropCard c;

		public RegisterCropCardAction(CropCard c) {
			this.c = c;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			// TODO Auto-generated method stub
			return "Registering crop " + c.name();
		}

		@Override
		public void apply() {
			Crops.instance.registerCrop(c);
		}
	}
	

	/**
	 * Action to add a biome nutrient bonus
	 * @author ben
	 *
	 */
	private static final class AddBiomeNutrientBonusAction extends OneWayAction {
		private String biomeType;
		private int nutrientBonus;

		public AddBiomeNutrientBonusAction(String biomeType, int nutrientBonus) {
			this.biomeType = biomeType;
			this.nutrientBonus = nutrientBonus;
		}

		@Override
		public Object getOverrideKey() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String describe() {
			return "Setting biome nutrient bonus for " + biomeType + " biomes to " +
					nutrientBonus;
		}

		@Override
		public void apply() {
			Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.valueOf(biomeType),
					nutrientBonus);
		}
	}
	
	/**
	 * Action to add a biome humidity bonus
	 * @author ben
	 *
	 */
	private static final class AddBiomeHumidityBonusAction extends OneWayAction {
		private String biomeType;
		private int humidityBonus;

		public AddBiomeHumidityBonusAction(String biomeType, int humidityBonus) {
			this.biomeType = biomeType;
			this.humidityBonus = humidityBonus;
		}

		@Override
		public Object getOverrideKey() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String describe() {
			return "Setting biome humidity bonus for " + biomeType + " biomes to " +
					humidityBonus;
		}

		@Override
		public void apply() {
			Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.valueOf(biomeType),
					humidityBonus);
		}
	}
	
	/**
	 * Add a nutrient bonus to crops in a specific biome
	 */
	@ZenMethod
	public static void addBiomeNutrientBonus(String biomeType, int nutrientBonus) {
		if(BiomeDictionary.Type.valueOf(biomeType) == null) {
			MineTweakerAPI.logWarning("invalid biome type: " + biomeType);
		} else if (nutrientBonus < -10 || nutrientBonus > 10) {
			MineTweakerAPI.logWarning("nutrient bonus " + nutrientBonus +
					" not in -10, +10 range");
		} else {
			MineTweakerAPI.apply(new AddBiomeNutrientBonusAction(biomeType, nutrientBonus));
		}
	}
	
	/**
	 * Add a humidity bonus to crops in a specific biome
	 */
	@ZenMethod
	public static void addBiomeHumidityBonus(String biomeType, int humidityBonus) {
		if(BiomeDictionary.Type.valueOf(biomeType) == null) {
			MineTweakerAPI.logWarning("invalid biome type: " + biomeType);
		} else if (humidityBonus < -10 || humidityBonus > 10) {
			MineTweakerAPI.logWarning("humidity bonus " + humidityBonus +
					" not in -10, +10 range");
		} else {
			MineTweakerAPI.apply(new AddBiomeHumidityBonusAction(biomeType, humidityBonus));
		}
	}

	/**
	 * Creates a new synthetic crop to manipulate
	 * @param cropOwner The mod that owns the crop
	 * @param cropName The name of the crop
	 */
	@ZenMethod
	public static SyntheticCrop newCrop(String cropName, String cropOwner) {
		return new SyntheticCrop(cropName, cropOwner);
	}
	
	/**
	 * Register a crop into the crop system
	 */
	@ZenMethod
	public static void registerCrop(CropCard c) {
		MineTweakerAPI.apply(new RegisterCropCardAction(c));
	}
	
	/**
	 * Register a new base seed item to plant a crop
	 * 
	 * @param seed The item to plant with
	 * @param c The crop being planted
	 * @param sz The initial size of the planted crop
	 * @param growth The initial growth stat of the planted crop
	 * @param gain The initial gain stat of the planted crop
	 * @param resist The initial resistance of the planted crop
	 */
	public static void registerBaseSeed(IItemStack seed, CropCard c, int sz,
			int growth, int gain, int resist) {
		MineTweakerAPI.apply(new RegisterBaseSeedAction(seed, c, sz, growth, gain, resist));
	}

	/**
	 * Get a crop by its name and owning mod
	 * @param owner The mod that owns the crop
	 * @param name The name of the crop
	 * @return A crop with the given name, owned by the given mod
	 */
	@ZenMethod
	public static Crop getCrop(String owner, String name) {
		return new Crop(Crops.instance.getCropCard(owner, name));
	}
	
	/**
	 * Get a crop by the seed that plants it
	 * @param seed The seed that plants the crop
	 * @return The crop planted by the seed
	 */
	@ZenMethod
	public static Crop getCrop(IItemStack seed) {
		return new Crop(Crops.instance.getCropCard(MineTweakerMC.getItemStack(seed)));
	}

	/**
	 * Get all of the currently registered crops
	 * @return All of the currently registered crops
	 */
	@ZenGetter("crops")
	public static List<Crop> getAllCrops() {
		Collection<CropCard> ccList = Crops.instance.getCrops();
		
		List<Crop> cList = new ArrayList<Crop>(ccList.size());
		for (CropCard cropCard : ccList) {
			cList.add(new Crop(cropCard));
		}
		
		return cList;
	}
}
