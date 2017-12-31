package crafttweaker.mc1120.oredict;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDict;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
        for (String key : OreDictionary.getOreNames()) {
            if (!OreDictionary.getOres(key).isEmpty()) {
                entries.add(CraftTweakerMC.getOreDict(key));
            }
        }
        return entries;
    }

    @Override
    public boolean contains(String name) {
        return OreDictionary.doesOreNameExist(name);
    }

    @Override
    public Iterator<IOreDictEntry> iterator() {
        return Arrays.asList(OreDictionary.getOreNames())
                .stream()
                .map(CraftTweakerMC::getOreDict)
                .iterator();

    }
}
