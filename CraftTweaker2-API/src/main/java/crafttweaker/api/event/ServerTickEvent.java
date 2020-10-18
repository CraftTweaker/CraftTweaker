package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import net.minecraftforge.fml.relauncher.Side;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.ServerTickEvent")
@ZenRegister
public interface ServerTickEvent extends ITickEvent {

    @Override
    default String getSide() {
        return Side.SERVER.name().toUpperCase();
    }
    
}
