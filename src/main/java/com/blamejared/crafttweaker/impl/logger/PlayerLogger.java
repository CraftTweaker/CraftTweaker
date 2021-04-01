package com.blamejared.crafttweaker.impl.logger;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.logger.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.*;
import net.minecraft.util.Util;
import net.minecraft.util.text.*;

import java.util.EnumMap;
import java.util.Map;

public class PlayerLogger implements ILogger {
    
    private static final Map<LogLevel, Pair<Style, Style>> STYLING = Util.make(new EnumMap<>(LogLevel.class), map -> {
        map.put(LogLevel.ERROR, make(TextFormatting.DARK_RED.getColor(), TextFormatting.RED.getColor()));
        map.put(LogLevel.WARNING, make(TextFormatting.GOLD.getColor(), TextFormatting.YELLOW.getColor()));
        // If someone ever sets the log level lower than this, they will be officially sniped by Silk 47
        // (Unless done by the CT team, that is)
        map.put(LogLevel.INFO, make(TextFormatting.DARK_GREEN.getColor(), TextFormatting.GREEN.getColor()));
        map.put(LogLevel.DEBUG, make(TextFormatting.DARK_PURPLE.getColor(), TextFormatting.LIGHT_PURPLE.getColor()));
        map.put(LogLevel.TRACE, make(TextFormatting.DARK_GRAY.getColor(), TextFormatting.GRAY.getColor()));
    });
    
    private PlayerEntity player;
    private LogLevel logLevel = LogLevel.WARNING;
    
    public PlayerLogger(PlayerEntity player) {
        this.player = player;
    }
    
    private static Pair<Style, Style> make(final Integer header, final Integer content) {
        return Pair.of(
                header == null? Style.EMPTY : Style.EMPTY.setColor(Color.fromInt(header)),
                content == null? Style.EMPTY : Style.EMPTY.setColor(Color.fromInt(content))
        );
    }
    
    @Override
    public void setLogLevel(LogLevel logLevel) {
        if(logLevel.canLog(LogLevel.WARNING))
            this.logLevel = logLevel;
    }
    
    @Override
    public LogLevel getLogLevel() {
        return logLevel;
    }
    
    @Override
    public void log(LogLevel level, String message, boolean prefix) {
        if(this.logLevel.canLog(level)) {
            final Pair<Style, Style> styling = STYLING.get(level);
            final IFormattableTextComponent header = new StringTextComponent("[" + level + "] ").setStyle(styling.getFirst());
            final IFormattableTextComponent line = header.append(new StringTextComponent(message).setStyle(styling.getSecond()));
            this.player.sendMessage(line, CraftTweaker.CRAFTTWEAKER_UUID);
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
