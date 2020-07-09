package com.blamejared.crafttweaker.impl.logger;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.logger.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;

public class PlayerLogger implements ILogger {
    
    private PlayerEntity player;
    private LogLevel logLevel = LogLevel.ERROR;
    
    public PlayerLogger(PlayerEntity player) {
        this.player = player;
    }
    
    @Override
    public void setLogLevel(LogLevel logLevel) {
        if(logLevel.canLog(LogLevel.ERROR))
            this.logLevel = logLevel;
    }
    
    @Override
    public LogLevel getLogLevel() {
        return logLevel;
    }
    
    @Override
    public void log(LogLevel level, String message, boolean prefix) {
        if(this.logLevel.canLog(level)) {
            this.player.sendMessage(new StringTextComponent(message), CraftTweaker.CRAFTTWEAKER_UUID);
        }
        
    }
    
    @Override
    public void throwingErr(String message, Throwable throwable) {
        error(String.format("%s: %s", message, throwable.getMessage()));
    }
    
    @Override
    public void throwingWarn(String message, Throwable throwable) {
        warning(String.format("%s: %s", message, throwable.getMessage()));
    }
}
