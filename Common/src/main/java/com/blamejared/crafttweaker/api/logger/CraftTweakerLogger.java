package com.blamejared.crafttweaker.api.logger;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.logger.appender.PlayerAppender;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Preconditions;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.LevelRangeFilter;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class CraftTweakerLogger {
    
    private static PlayerAppender PLAYER_APPENDER;
    public static final String LOGGER_NAME = "CRT_LOG_FILE";
    private static final String CRT_LOG_NAME = "CRT_LOG";
    private static final String PLAYER_LOG_NAME = "CRT_PLAYER";
    
    
    public static void init() {
        
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        
        PatternLayout logPattern = PatternLayout.newBuilder()
                .withPattern(Services.PLATFORM.getLogFormat())
                .build();
        PatternLayout playerPattern = PatternLayout.newBuilder()
                .withPattern("%msg%n%throwable{short.message}")
                .build();
        
        Appender fileAppender = FileAppender.newBuilder()
                .withFileName(CraftTweakerConstants.LOG_PATH)
                .withAppend(false)
                .setName(CRT_LOG_NAME)
                .withImmediateFlush(true)
                .setIgnoreExceptions(false)
                .setConfiguration(config)
                .withLayout(logPattern)
                .build();
        PLAYER_APPENDER = PlayerAppender.createAppender(PLAYER_LOG_NAME, LevelRangeFilter.createFilter(Level.FATAL, Level.WARN, Filter.Result.ACCEPT, Filter.Result.DENY), playerPattern);
        
        fileAppender.start();
        PLAYER_APPENDER.start();
        config.addAppender(fileAppender);
        config.addAppender(PLAYER_APPENDER);
        AppenderRef[] refs = new AppenderRef[] {createAppenderRef(CRT_LOG_NAME), createAppenderRef(PLAYER_LOG_NAME)};
        
        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.INFO, LOGGER_NAME, "true", refs, null, config, null);
        
        loggerConfig.addAppender(fileAppender, null, null);
        loggerConfig.addAppender(PLAYER_APPENDER, null, null);
        
        config.addLogger(LOGGER_NAME, loggerConfig);
        ctx.updateLoggers();
    }
    
    private static AppenderRef createAppenderRef(String name) {
        
        return AppenderRef.createAppenderRef(name, null, null);
    }
    
    
    public static void addPlayer(Player player) {
        
        Preconditions.checkNotNull(PLAYER_APPENDER, "Cannot add player before 'PLAYER_APPENDER' has been initialized!");
        
        PLAYER_APPENDER.addPlayerLogger(player);
    }
    
    public static void removePlayer(Player player) {
        
        Preconditions.checkNotNull(PLAYER_APPENDER, "Cannot add remove before 'PLAYER_APPENDER' has been initialized!");
        
        PLAYER_APPENDER.removePlayerLogger(player);
    }
    
    public static void clearPreviousMessages() {
        
        Preconditions.checkNotNull(PLAYER_APPENDER, "Cannot add remove before 'PLAYER_APPENDER' has been initialized!");
        PLAYER_APPENDER.clearPreviousMessages();
    }
    
}
