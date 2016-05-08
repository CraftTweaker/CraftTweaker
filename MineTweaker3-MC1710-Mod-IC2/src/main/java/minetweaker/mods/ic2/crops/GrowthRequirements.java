package minetweaker.mods.ic2.crops;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * Contains all of the growth requirements for an IC2 crop in one easy place
 * @author ben
 *
 */
@ZenClass("mods.ic2.GrowthRequirements")
public class GrowthRequirements {
	
	private int minNutrients;
	private int minHumidity;
	private int minAirQuality;
	
	private int minLight;
	private int maxLight = 17;
	
	private IItemStack requiredBlock;

	/**
	 * Get the minimum amount of nutrients this plant needs to grow
	 * @return The minimum amount of nutrients this plant needs
	 */
	@ZenGetter("minNutrients")
	public int getMinNutrients() {
		return minNutrients;
	}

	/**
	 * Set the minimum amount of nutrients this plant needs to grow
	 * @param minNutrients The new minimum amount of nutrients this plant requires
	 */
	@ZenSetter("minNutrients")
	public void setMinNutrients(int minNutrients) {
		this.minNutrients = minNutrients;
	}

	/**
	 * Get the minimum amount of humidity this plant requires
	 * @return The minimum amount of humidity this plant requires
	 */
	@ZenGetter("minHumidity")
	public int getMinHumidity() {
		return minHumidity;
	}

	/**
	 * Set the minimum amount of humidity this plant requires
	 * @param minHumidity The new minimum amount of humidity this plant requires
	 */
	@ZenSetter("minHumidity")
	public void setMinHumidity(int minHumidity) {
		this.minHumidity = minHumidity;
	}

	/**
	 * Get the minimum air quality this plant requires
	 * @return The minimum air quality this plant requires
	 */
	@ZenGetter("minAirQuality")
	public int getMinAirQuality() {
		return minAirQuality;
	}

	/**
	 * Set the minimum air quality this plant requires
	 * @param minAirQuality The new amount of air quality this plant requires
	 */
	@ZenSetter("minAirQuality")
	public void setMinAirQuality(int minAirQuality) {
		this.minAirQuality = minAirQuality;
	}

	/**
	 * Get the minimum amount of light this plant requires
	 * @return The minimum amount of light this plant requires
	 */
	@ZenGetter("minLight")
	public int getMinLight() {
		return minLight;
	}

	/**
	 * Set the minimum amount of light this plant requires
	 * @param minLight The new minimum amount of light this plant requires
	 */
	@ZenSetter("minLight")
	public void setMinLight(int minLight) {
		this.minLight = minLight;
	}

	/**
	 * Get the maximum amount of light this plant can have
	 * @return The maximum amount of light this plant can have
	 */
	@ZenGetter("maxLight")
	public int getMaxLight() {
		return maxLight;
	}

	/**
	 * Set the maximum amount of light this plant can have
	 * @param maxLight The new maximum amount of light thi plant can have
	 */
	@ZenSetter("maxLight")
	public void setMaxLight(int maxLight) {
		this.maxLight = maxLight;
	}

	/**
	 * Get the block that is required to be below this one
	 * @return The block required to be below this one
	 */
	@ZenGetter("requiredBlock")
	public IItemStack getRequiredBlock() {
		return requiredBlock;
	}

	/** 
	 * Set the block required to be below this one
	 * @param requiredBlock The new block required to be below this one
	 */
	@ZenSetter("requiredBlock")
	public void setRequiredBlock(IItemStack requiredBlock) {
		this.requiredBlock = requiredBlock;
	}

	
	public GrowthRequirements(int minNutrients, int minHumidity, int minAirQuality, int minLight, int maxLight,
			IItemStack requiredBlock) {
		super();
		this.minNutrients = minNutrients;
		this.minHumidity = minHumidity;
		this.minAirQuality = minAirQuality;
		this.minLight = minLight;
		this.maxLight = maxLight;
		this.requiredBlock = requiredBlock;
	}
}
