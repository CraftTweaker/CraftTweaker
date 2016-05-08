package minetweaker.mods.ic2.crops;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.crops.CropCard;
import ic2.api.crops.ICropTile;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * A synthetic crop, created in ZS
 * 
 * For your crop textures to be picked up, put them at owner + ":crops/crop" +
 * name + "." + size where owner is the mod responsible for the crop name is the
 * name of the crop size is the current size of the crop, starting at 0 for new
 * crops
 * 
 * Localizations will be looked for at owner + ".crop." + name where each field
 * has the value it has above
 * 
 * @author ben
 *
 */
@ZenClass("mods.ic2.SyntheticCrop")
public class SyntheticCrop extends CropCard {

	private String name;
	private String owner;

	private int tier;

	private int[] stats = new int[4];
	private String[] attributes;

	private int maxSize;

	private float dropChance;
	private IItemStack fruit;

	private GrowthRequirements reqs;

	/**
	 * Create a new synthetic crop
	 * 
	 * @param cropName
	 *            The name of this crop
	 * @param cropOwner
	 *            The mod creating this crop
	 */
	public SyntheticCrop(String cropName, String cropOwner) {
		name = cropName;
	}

	// Required IC2 Interface
	/**
	 * Gets the name of this crop used as a identifier
	 */
	@Override
	@ZenGetter("name")
	public String name() {
		return name;
	}

	/**
	 * Get the mod that owns this crop
	 */
	@Override
	@ZenGetter("owner")
	public String owner() {
		return owner;
	}

	/**
	 * Gets the localization key used for this crop
	 */
	@Override
	public String displayName() {
		return owner + ".crop." + name;
	}

	/**
	 * Gets the seed tier of this crop.
	 */
	@Override
	@ZenGetter("tier")
	public int tier() {
		return tier;
	}

	/**
	 * Gets the value of a particular stat for this crop
	 * 
	 * Stats: - 0: Chemistry (Industrial uses based on chemical plant
	 * components) - 1: Consumable (Food, potion ingredients, stuff meant to be
	 * eaten or similarly used) - 2: Defensive (Plants with defense capabilities
	 * (damaging, explosive, chemical) or special abilities in general) - 3:
	 * Colorful (How colorful/aesthetically/beautiful is the plant, like
	 * dye-plants or plants without actual effects) - 4: Weed (Is this plant
	 * weed-like and rather unwanted/quick-spreading? Rare super-breed plants
	 * should have low values here)
	 *
	 */
	@Override
	@ZenMethod
	public int stat(int n) {
		return stats[n];
	}

	/**
	 * Gets any additional attributes of this crop
	 */
	@Override
	@ZenGetter("attributes")
	public String[] attributes() {
		return attributes;
	}

	/**
	 * Gets the maximum size of this crop
	 */
	@Override
	@ZenGetter("maxSize")
	public int maxSize() {
		return maxSize;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerSprites(IIconRegister iconRegister) {
		textures = new IIcon[maxSize()];

		for (int i = 1; i <= textures.length; i++) {
			textures[i - 1] = iconRegister.registerIcon(owner() + ":crop/crop" + name() + "." + i);
		}
	}

	@Override
	public boolean canGrow(ICropTile crop) {
		if (crop.getSize() == maxSize) {
			return false;
		} else if (reqs == null) {
			return true;
		} else {
			if(!crop.isBlockBelow(MineTweakerMC.getBlock(reqs.getRequiredBlock()))) {
				return false;
			} else if(crop.getAirQuality() < reqs.getMinAirQuality()){
				return false;
			} else if(crop.getHumidity() < reqs.getMinHumidity()) {
				return false;
			} else if(crop.getNutrients() < reqs.getMinNutrients()) {
				return false;
			} else {
				int lgh = crop.getLightLevel();
				return reqs.getMinLight() < lgh && reqs.getMaxLight() > lgh;
			}
		}
	}

	@Override
	public int getOptimalHavestSize(ICropTile crop) {
		return maxSize;
	}

	@Override
	public boolean canBeHarvested(ICropTile crop) {
		return crop.getSize() == maxSize;
	}

	@Override
	public ItemStack getGain(ICropTile crop) {
		return MineTweakerMC.getItemStack(fruit);
	}

	/**
	 * Gets the chance for this crop to drop an item
	 */
	@Override
	@ZenGetter("dropChance")
	public float dropGainChance() {
		return dropChance;
	};

	// ZS Support

	/**
	 * Sets the tier of this crop
	 * 
	 * @param tier
	 *            The new tier of this crop
	 */
	@ZenSetter("tier")
	public void setTier(int tier) {
		this.tier = tier;
	}

	/**
	 * Sets all of the stats for this crop
	 * 
	 * @param stats
	 *            The new stats to give to this crop
	 */
	@ZenSetter("stats")
	public void setStats(int[] stats) {
		this.stats = stats;
	}

	/**
	 * Sets an individual stat for this crop
	 * 
	 * @param stat
	 *            The stat to set a value for
	 * @param val
	 *            The new value for this stat
	 */
	@ZenMethod
	public void setStat(int stat, int val) {
		if (stat < 0 || stat > 3) {
			MineTweakerAPI.logWarning("invalid stat index " + stat);
		} else {
			stats[stat] = val;
		}
	}

	/**
	 * Sets the additional attributes for this crop
	 * 
	 * @param attributes
	 *            Sets the attributes of this crop
	 */
	@ZenSetter("attributes")
	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	/**
	 * Sets the maximum size this crop goes to
	 * 
	 * @param maxSize
	 *            The new max size of this crop
	 */
	@ZenSetter("maxSize")
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * Sets the item this crop gives when harvested
	 * 
	 * @param fruit
	 *            The item to give from the crop
	 */
	@ZenSetter("fruit")
	public void setFruit(IItemStack fruit) {
		this.fruit = fruit;
	}

	/**
	 * Sets the chance for this plant to yield its fruit
	 * 
	 * @param dropChance
	 *            The new chance for this plant to yield fruit
	 */
	@ZenSetter("dropChance")
	public void setDropChance(int dropChance) {
		this.dropChance = dropChance;
	}

	/**
	 * Get the current requirements for this plant to grow
	 * 
	 * @return The current growth requirements for this plant
	 */
	public GrowthRequirements getReqs() {
		return reqs;
	}

	/**
	 * Set the current growth requirements for this plant to grow
	 * 
	 * @param reqs
	 *            The new requirements for this plant to grow
	 */
	public void setReqs(GrowthRequirements reqs) {
		this.reqs = reqs;
	}

}
