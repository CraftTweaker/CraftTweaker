package crafttweaker.mc1120.util;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author Stan
 */
public class CraftTweakerPlatformUtils {

    private CraftTweakerPlatformUtils() {
    }

    public static String getLanguage() {
        return FMLCommonHandler.instance().getSide() == Side.SERVER ? null : FMLClientHandler.instance().getCurrentLanguage();
    }

    public static boolean isLanguageActive(String lang) {
        String current = getLanguage();
        return current != null && current.equals(lang);
    }

    public static boolean isClient() {
        return FMLCommonHandler.instance().getSide() == Side.CLIENT;
    }
}
