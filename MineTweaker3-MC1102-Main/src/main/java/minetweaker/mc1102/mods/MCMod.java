package minetweaker.mc1102.mods;

import minetweaker.api.mods.IMod;
import net.minecraftforge.fml.common.ModContainer;

/**
 * @author Stan
 */
public class MCMod implements IMod {

    private final ModContainer mod;

    public MCMod(ModContainer mod) {
        this.mod = mod;
    }

    @Override
    public String getId() {
        return mod.getModId();
    }

    @Override
    public String getName() {
        return mod.getName();
    }

    @Override
    public String getVersion() {
        return mod.getVersion();
    }

    @Override
    public String getDescription() {
        return mod.getMetadata().description;
    }
}
