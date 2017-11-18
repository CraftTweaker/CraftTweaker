package crafttweaker.mc1120.oredict;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.*;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

import static crafttweaker.api.minecraft.CraftTweakerMC.getOreDict;

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
                entries.add(CraftTweakerMC.getOreDict(key));
            }
        }
        return entries;
    }

	@Override
	public boolean contains(String name) {
		for (String id : OreDictionary.getOreNames()) {
			if (id == name) 
				return true;
		}
		return false;
	}

	@Override
	public Iterator<IOreDictEntry> iterator() {
		return Arrays.asList(OreDictionary.getOreNames())
				.stream()
				.map(CraftTweakerMC::getOreDict)
				.iterator();
		
	}
}
