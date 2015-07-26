package minetweaker.mods.ic2.crops;

import ic2.api.crops.CropCard;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Read-only data accessor for crops
 * @author ben
 *
 */
@ZenClass("mods.ic2.Crop")
public class Crop {
	private CropCard cc;

	@ZenGetter("name")
	public String name() {
		return cc.name();
	}

	@ZenGetter("owner")
	public String owner() {
		return cc.owner();
	}

	@ZenGetter("displayName")
	public String displayName() {
		return cc.displayName();
	}

	@ZenGetter("hashCode")
	public int hashCode() {
		return cc.hashCode();
	}

	@ZenGetter("discoveredBy")
	public String discoveredBy() {
		return cc.discoveredBy();
	}

	@ZenMethod
	public String desc(int i) {
		return cc.desc(i);
	}

	@ZenGetter("tier")
	public int tier() {
		return cc.tier();
	}

	@ZenMethod
	public int stat(int n) {
		return cc.stat(n);
	}
	
	@ZenGetter("attributes")
	public String[] attributes() {
		return cc.attributes();
	}

	@ZenGetter("maxSize")
	public int maxSize() {
		return cc.maxSize();
	}

	@ZenGetter("dropGainChance")
	public float dropGainChance() {
		return cc.dropGainChance();
	}

	@ZenCaster
	public String toString() {
		return cc.toString();
	}

	public Crop(CropCard cc) {
		super();
		this.cc = cc;
	}

	
}
