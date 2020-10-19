package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import net.minecraftforge.fml.relauncher.Side;

@ZenClass("crafttweaker.event.ClientTickEvent")
@ZenRegister
public interface ClientTickEvent extends ITickEvent {
    
    @Override
    default String getSide() {
        return Side.CLIENT.name().toUpperCase();
    }

}
