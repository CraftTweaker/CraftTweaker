package crafttweaker.zenscript;

import crafttweaker.CraftTweakerAPI;
import stanhebben.zenscript.IZenErrorLogger;
import stanhebben.zenscript.util.ZenPosition;

public class CrTErrorLogger implements IZenErrorLogger {
    
    @Override
    public void error(ZenPosition position, String message) {
        if(position == null) {
            CraftTweakerAPI.logError("system: " + message);
        } else {
            CraftTweakerAPI.logError(position + ": " + message);
        }
    }
    
    @Override
    public void warning(ZenPosition position, String message) {
        if(position == null) {
            CraftTweakerAPI.logWarning("system: " + message);
        } else {
            CraftTweakerAPI.logWarning(position + ": " + message);
        }
    }
    
    @Override
    public void info(ZenPosition position, String message) {
        if(position == null) {
            CraftTweakerAPI.logInfo("system: " + message);
        } else {
            CraftTweakerAPI.logInfo(position + ": " + message);
        }
    }
    
    @Override
    public void error(String message) {
        error(null, message);
    }
    
    @Override
    public void error(String message, Throwable e) {
        CraftTweakerAPI.logError("system: " + message, e);
    }
    
    @Override
    public void warning(String message) {
        warning(null, message);
    }
    
    @Override
    public void info(String message) {
        info(null, message);
    }
}