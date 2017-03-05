package minetweaker.mods.ic2.crops;

import ic2.api.crops.CropCard;
import stanhebben.zenscript.annotations.*;

/**
 * Read-only data accessor for crops
 *
 * @author ben
 */
@ZenClass("mods.ic2.Crop")
public class Crop {

    private CropCard cc;

    public Crop(CropCard cc) {
        super();
        this.cc = cc;
    }

    @ZenGetter("name")
    public String name() {
        return cc.getName();
    }

    @ZenGetter("owner")
    public String owner() {
        return cc.getOwner();
    }

    @ZenGetter("displayName")
    public String displayName() {
        return cc.getDisplayName();
    }

    @ZenGetter("hashCode")
    public int hashCode() {
        return cc.hashCode();
    }

    @ZenGetter("discoveredBy")
    public String discoveredBy() {
        return cc.getDiscoveredBy();
    }

    @ZenMethod
    public String desc(int i) {
        return cc.desc(i);
    }

    @ZenGetter("tier")
    public int tier() {
        return cc.getProperties().getTier();
    }

    @ZenGetter("attributes")
    public String[] attributes() {
        return cc.getAttributes();
    }

    @ZenGetter("maxSize")
    public int maxSize() {
        return cc.getMaxSize();
    }

    @ZenGetter("dropGainChance")
    public double dropGainChance() {
        return cc.dropGainChance();
    }

    @ZenCaster
    public String toString() {
        return cc.toString();
    }


}
