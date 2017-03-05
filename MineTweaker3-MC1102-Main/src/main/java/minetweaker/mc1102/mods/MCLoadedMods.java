package minetweaker.mc1102.mods;

import minetweaker.api.mods.*;
import net.minecraftforge.fml.common.*;
import stanhebben.zenscript.util.MappingIterator;

import java.util.*;

/**
 * @author Stan
 */
public class MCLoadedMods implements ILoadedMods {

    public MCLoadedMods() {
    }

    @Override
    public boolean contains(String name) {
        return Loader.isModLoaded(name);
    }

    @Override
    public IMod get(String name) {
        for(ModContainer mod : Loader.instance().getActiveModList()) {
            if(mod.getModId().equals(name)) {
                return new MCMod(mod);
            }
        }

        return null;
    }

    @Override
    public Iterator<IMod> iterator() {
        List<ModContainer> mods = Loader.instance().getActiveModList();
        return new MappingIterator<ModContainer, IMod>(mods.iterator()) {
            @Override
            public IMod map(ModContainer value) {
                return new MCMod(value);
            }
        };
    }
}
