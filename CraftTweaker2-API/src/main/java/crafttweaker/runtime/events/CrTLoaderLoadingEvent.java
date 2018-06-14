package crafttweaker.runtime.events;

import crafttweaker.api.network.NetworkSide;
import crafttweaker.runtime.ScriptLoader;

public class CrTLoaderLoadingEvent {
    
    private final ScriptLoader loader;
    private final NetworkSide networkSide;
    private final boolean isSyntaxCommand;
    
    public CrTLoaderLoadingEvent(ScriptLoader loader, NetworkSide networkSide, boolean isSyntaxCommand) {
        this.loader = loader;
        this.networkSide = networkSide;
        this.isSyntaxCommand = isSyntaxCommand;
    }
    
    public ScriptLoader getLoader() {
        return loader;
    }
    
    public NetworkSide getNetworkSide() {
        return networkSide;
    }
    
    public boolean isSyntaxCommand() {
        return isSyntaxCommand;
    }
    
    public static class Started extends CrTLoaderLoadingEvent {
        
        public Started(ScriptLoader loader, NetworkSide networkSide, boolean isSyntaxCommand) {
            super(loader, networkSide, isSyntaxCommand);
        }
    }
    
    public static class Finished extends CrTLoaderLoadingEvent {
        
        public Finished(ScriptLoader loader, NetworkSide networkSide, boolean isSyntaxCommand) {
            super(loader, networkSide, isSyntaxCommand);
        }
    }
    
    public static class Aborted extends CrTLoaderLoadingEvent {
        
        private final String message;
        
        public Aborted(ScriptLoader loader, NetworkSide networkSide, boolean isSyntaxCommand, String message) {
            super(loader, networkSide, isSyntaxCommand);
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
