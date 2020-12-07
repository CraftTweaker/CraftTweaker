package crafttweaker.api.logger;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.player.IPlayer;
import crafttweaker.runtime.ILogger;

import java.util.*;

/**
 * @author Stan
 */
public class MTLogger implements ILogger {
    
    private final List<ILogger> loggers = new ArrayList<>();
    private final List<IPlayer> players = new ArrayList<>();
    private final List<ErrorMessage> unprocessed = new ArrayList<>();
    private boolean isDefaultDisabled = false;
    
    public void addLogger(ILogger logger) {
        loggers.add(logger);
    }
    
    public void removeLogger(ILogger logger) {
        loggers.remove(logger);
    }
    
    public void addPlayer(IPlayer player) {
        players.add(player);
        if(!unprocessed.isEmpty()) {
            unprocessed.stream()
                    .filter(errorMessage -> !(CraftTweakerAPI.noWarn && errorMessage.isWarning))
                    .forEach(errorMessage -> player.sendChat(errorMessage.message));
        }
    }
    
    public void removePlayer(IPlayer player) {
        players.remove(player);
    }
    
    public void clear() {
        unprocessed.clear();
    }
    
    @Override
    public void logCommand(String message) {
        for(ILogger logger : loggers) {
            logger.logCommand(message);
        }
    }
    
    @Override
    public void logInfo(String message) {
        for(ILogger logger : loggers) {
            logger.logInfo(message);
        }
    }
    
    @Override
    public void logWarning(String message) {
        for(ILogger logger : loggers) {
            logger.logWarning(message);
        }
        
        String message2 = "WARNING: " + message;
        if(players.isEmpty()) {
            unprocessed.add(new ErrorMessage(message2, true));
        } else {
            if(!CraftTweakerAPI.noWarn) {
                for(IPlayer player : players) {
                    player.sendChat(message2);
                }
            }
        }
    }
    
    @Override
    public void logError(String message) {
        logError(message, null);
    }
    
    @Override
    public void logError(String message, Throwable exception) {
        for(ILogger logger : loggers) {
            logger.logError(message, exception);
        }
        
        String message2 = "ERROR: " + message;
        if(players.isEmpty()) {
            unprocessed.add(new ErrorMessage(message2, false));
        } else {
            players.forEach(player -> player.sendChat(message2));
        }
    }
    
    @Override
    public void logPlayer(IPlayer player) {
    
    }
    
    @Override
    public void logDefault(String message) {
        if(!isLogDisabled())
            logInfo(message);
    }
    
    @Override
    public boolean isLogDisabled() {
        return isDefaultDisabled;
    }
    
    @Override
    public void setLogDisabled(boolean logDisabled) {
        this.isDefaultDisabled = logDisabled;
    }

    private static class ErrorMessage {
        final String message;
        final boolean isWarning;

        public ErrorMessage(String message, boolean isWarning) {
            this.message = message;
            this.isWarning = isWarning;
        }
    }
}
