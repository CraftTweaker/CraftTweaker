package crafttweaker.runtime.events;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.network.NetworkSide;
import crafttweaker.runtime.ScriptLoader;

/**
 * Deprecated, use the newly added super class
 */
@Deprecated
public class CrTLoadingStartedEvent extends CrTLoaderLoadingEvent.Started {
    
    @Deprecated
    public CrTLoadingStartedEvent(String loaderName, boolean isSyntaxCommand, NetworkSide networkSide) {
        super(CraftTweakerAPI.tweaker.addLoader(loaderName), networkSide, isSyntaxCommand);
    }
    
    public CrTLoadingStartedEvent(ScriptLoader loader, NetworkSide networkSide, boolean isSyntaxCommand) {
        super(loader, networkSide, isSyntaxCommand);
    }
    
    public String getLoaderName() {
        return getLoader().getNames().iterator().next();
    }
}
