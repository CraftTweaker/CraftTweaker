package crafttweaker.runtime;

import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
public interface ILogger {
    
    @ZenMethod
    void logCommand(String message);
    
    @ZenMethod
    void logInfo(String message);
    
    @ZenMethod
    void logWarning(String message);
    
    @ZenMethod
    void logError(String message);
    
    @ZenMethod
    void logError(String message, Throwable exception);
    
    @ZenMethod
    void logPlayer(IPlayer player);
    
    @ZenMethod
    void logDefault(String message);
    
    @ZenGetter("logDisabled")
    boolean isLogDisabled();
    
    @ZenSetter("logDisabled")
    void setLogDisabled(boolean logDisabled);
}
