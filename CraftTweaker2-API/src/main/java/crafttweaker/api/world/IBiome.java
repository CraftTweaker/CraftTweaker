package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.List;

/**
 * @author Stan
 */

@ZenClass("crafttweaker.world.IBiome")
@ZenRegister
public interface IBiome {
    
	@ZenGetter("name")
    String getName();
	
	@ZenGetter("id")
    String getId();
	
	@ZenGetter("canRain")
	boolean getCanRain();
	
	@ZenGetter("isSnowyBiome")
	boolean isSnowyBiome();
	
	@ZenGetter("highHumidity")
	boolean getIsHighHumidity();
	
	@ZenGetter("spawningChance")
	float getSpawningChance();
	
	@ZenGetter("baseHeight")
	float getBaseHeight();
	
	@ZenGetter("rainfall")
	float getRainfall();
	
	@ZenGetter("waterColorMultiplier")
	int getWaterColorMultiplier();
	
	@ZenGetter("ignorePlayerSpawnSuitability")
	boolean getIgnorePlayerSpawnSuitability();
	
	@ZenGetter("heightVariation")
	float getHeightVariation();
	
	@ZenGetter("temperature")
	float getTemperature();
	
	@ZenGetter("types")
	List<IBiomeType> getTypes();
	
}
