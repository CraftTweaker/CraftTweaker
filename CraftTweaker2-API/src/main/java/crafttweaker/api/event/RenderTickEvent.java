package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import net.minecraftforge.fml.relauncher.Side;

@ZenClass("crafttweaker.event.RenderTickEvent")
@ZenRegister
public interface RenderTickEvent extends ITickEvent {
    
    @ZenGetter("renderTickTime")
    float getRenderTickTime();

    @Override
    default String getSide() {
        return Side.CLIENT.name().toUpperCase();
    }

}
