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
        super(CraftTweakerAPI.tweaker.getOrCreate(loaderName), networkSide, isSyntaxCommand);
    }
    
    public CrTLoadingStartedEvent(ScriptLoader loader, NetworkSide networkSide, boolean isSyntaxCommand) {
        super(loader, networkSide, isSyntaxCommand);
    }
    
    public String getLoaderName() {
        return getLoader().getMainName();
    }
    
    @Override
    public ScriptLoader getLoader() {
        return super.getLoader();
    }
    
    @Override
    public NetworkSide getNetworkSide() {
        return super.getNetworkSide();
    }
    
    @Override
    public boolean isSyntaxCommand() {
        return super.isSyntaxCommand();
    }
}
