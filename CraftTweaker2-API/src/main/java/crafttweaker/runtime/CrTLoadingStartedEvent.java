package crafttweaker.runtime;

import crafttweaker.api.network.NetworkSide;

public class CrTLoadingStartedEvent {
    private String loaderName;
    private boolean isSyntaxCommand;
    private NetworkSide networkSide;
    
    public CrTLoadingStartedEvent(String loaderName, boolean isSyntaxCommand, NetworkSide networkSide) {
        this.loaderName = loaderName;
        this.isSyntaxCommand = isSyntaxCommand;
        this.networkSide = networkSide;
    }
    
    public String getLoaderName() {
        return loaderName;
    }
    
    public boolean isSyntaxCommand() {
        return isSyntaxCommand;
    }
    
    public NetworkSide getNetworkSide() {
        return networkSide;
    }
}
