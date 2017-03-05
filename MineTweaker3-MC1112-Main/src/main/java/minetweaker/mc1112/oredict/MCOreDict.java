package minetweaker.mc1112.oredict;

import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.oredict.*;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

import static minetweaker.api.minecraft.MineTweakerMC.getOreDict;

/**
 * @author Stan
 */
public class MCOreDict implements IOreDict {

    @Override
    public IOreDictEntry get(String name) {
        return getOreDict(name);
    }

    @Override
    public List<IOreDictEntry> getEntries() {
        List<IOreDictEntry> entries = new ArrayList<>();
        for(String key : OreDictionary.getOreNames()) {
            if(!OreDictionary.getOres(key).isEmpty()) {
                entries.add(MineTweakerMC.getOreDict(key));
            }
        }
        return entries;
    }
}
