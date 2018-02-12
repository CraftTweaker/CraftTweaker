package crafttweaker.runtime;

import crafttweaker.CraftTweakerAPI;

/**
 * @author Stan
 */
public class GlobalFunctions {
    
    private GlobalFunctions() {
    }
    
    public static void print(Object message) {
        CraftTweakerAPI.logInfo(message.toString());
    }
    
    // public static void print(Object object) {
    //     CraftTweakerAPI.logInfo(String.valueOf(object));
    // }
    
    public static int totalActions() {
        return CraftTweakerAPI.tweaker.getActions().size();
    }
    
    public static void enableDebug() {
        CraftTweakerAPI.tweaker.enableDebug();
    }
    
    
    public static boolean isNull(Object any){
        return any == null;
    }
    
    
    
}
