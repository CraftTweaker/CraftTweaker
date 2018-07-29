import crafttweaker.*;
import crafttweaker.api.player.IPlayer;
import crafttweaker.runtime.*;

import java.util.Collections;

public class TestLoaders {
    
    public static void main(String[] args) {
        
        CraftTweakerAPI.tweaker.setScriptProvider(Collections::emptyIterator);
        CrafttweakerImplementationAPI.logger.addLogger(new SoutLogger());
        
        
        CraftTweakerAPI.tweaker.getOrCreateLoader("test1");
        CraftTweakerAPI.tweaker.getOrCreateLoader("test2");
        CraftTweakerAPI.tweaker.getOrCreateLoader("test1", "test2");
        CraftTweakerAPI.tweaker.getOrCreateLoader("test3", "test4").setMainName("test5");
        CraftTweakerAPI.tweaker.getOrCreateLoader("test1", "test3");
    
        CraftTweakerAPI.logInfo("Stage: " + CraftTweakerAPI.tweaker.getOrCreateLoader("test2").getLoaderStage());
        
        CraftTweakerAPI.tweaker.loadScript(false, "test1");
    
        CraftTweakerAPI.logInfo("Stage: " + CraftTweakerAPI.tweaker.getOrCreateLoader("test2").getLoaderStage());
        
        CraftTweakerAPI.tweaker.getOrCreateLoader("test5", "test6", "test7", "test8");
        CraftTweakerAPI.tweaker.loadScript(false, "test4");
        
        
        CraftTweakerAPI.logInfo("Stage: " + CraftTweakerAPI.tweaker.getOrCreateLoader("test2").getLoaderStage());
    }
    
    
    private static class SoutLogger implements ILogger {
        
        @Override
        public void logCommand(String message) {
            System.out.println("[Command] " + message);
        }
        
        @Override
        public void logInfo(String message) {
            System.out.println("[INFO] " + message);
        }
        
        @Override
        public void logWarning(String message) {
            System.out.println("[WARNING]" + message);
        }
        
        @Override
        public void logError(String message) {
            System.out.println("[ERROR]" + message);
        }
        
        @Override
        public void logError(String message, Throwable exception) {
            logError(message);
        }
        
        @Override
        public void logPlayer(IPlayer player) {
            System.out.println("[PLAYER] " + player.getName());
        }
        
        @Override
        public void logDefault(String message) {
            System.out.println("[DEFAULT]" + message);
        }
        
        @Override
        public boolean isLogDisabled() {
            return false;
        }
        
        @Override
        public void setLogDisabled(boolean logDisabled) {
            //No-OP
        }
    }
}
