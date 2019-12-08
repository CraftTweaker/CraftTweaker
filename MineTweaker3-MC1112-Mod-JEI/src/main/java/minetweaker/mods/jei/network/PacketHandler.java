package minetweaker.mods.jei.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("crafttweakerjei");
    
    public static void init() {
        INSTANCE.registerMessage(MessageJEIHide.class, MessageJEIHide.class, 0, Side.CLIENT);
    }
    
}
