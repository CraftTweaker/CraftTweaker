package minetweaker.runtime;

import minetweaker.MineTweakerAPI;

/**
 * @author Stan
 */
public class GlobalFunctions {
    
    private GlobalFunctions() {
    }
    
    public static void print(String message) {
        MineTweakerAPI.logInfo(message);
    }
    
    public static int totalActions() {
        return MineTweakerAPI.tweaker.getActions().size();
    }
    
    public static void enableDebug() {
        MineTweakerAPI.tweaker.enableDebug();
    }
    
    
}
