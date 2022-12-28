package com.blamejared.crafttweaker.impl.logging;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.gametest.logging.appender.GameTestLoggerAppender;
import com.blamejared.crafttweaker.platform.Services;
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

import java.util.Objects;

public final class CraftTweakerLog4jEditor {
    
    private static final String CRT_LOG_NAME = "CRT_LOG";
    private static final String PLAYER_LOG_NAME = "CRT_PLAYER";
    
    private static final String GAME_TEST_LOG_NAME = "CRT_GAMETEST";
    
    private static CraftTweakerLog4jEditor instance;
    
    private final PlayerAppender playerAppender;
    private final GameTestLoggerAppender gameTestAppender;
    
    private CraftTweakerLog4jEditor(final PlayerAppender playerAppender, final GameTestLoggerAppender gameTestAppender) {
        
        this.playerAppender = playerAppender;
        this.gameTestAppender = gameTestAppender;
    }
    
    public static void edit() {
        
        final LoggerContext context = findContext();
        final Configuration config = context.getConfiguration();
        
        final PatternLayout logPattern = PatternLayout.newBuilder()
                .withPattern(Services.PLATFORM.getLogFormat())
                .build();
        final PatternLayout playerPattern = PatternLayout.newBuilder()
                .withPattern("%msg%n%throwable{short.message}")
                .build();
        final PatternLayout gameTestPattern = PatternLayout.newBuilder()
                .withPattern("%msg%n%throwable")
                .build();
        
        final Appender fileAppender = FileAppender.newBuilder()
                .withFileName(CraftTweakerConstants.LOG_PATH)
                .withAppend(false)
                .setName(CRT_LOG_NAME)
                .withImmediateFlush(true)
                .setIgnoreExceptions(false)
                .setConfiguration(config)
                .setLayout(logPattern)
                .build();
        
        final Filter playerFilter = LevelRangeFilter.createFilter(Level.FATAL, Level.WARN, Filter.Result.ACCEPT, Filter.Result.DENY);
        final PlayerAppender playerAppender = PlayerAppender.createAppender(PLAYER_LOG_NAME, playerFilter, playerPattern);
        
        final Filter gameTestFilter = LevelRangeFilter.createFilter(Level.FATAL, Level.ALL, Filter.Result.ACCEPT, Filter.Result.DENY);
        final GameTestLoggerAppender gameTestAppender = GameTestLoggerAppender.createAppender(GAME_TEST_LOG_NAME, gameTestFilter, gameTestPattern);
        
        fileAppender.start();
        playerAppender.start();
        gameTestAppender.start();
        
        config.addAppender(fileAppender);
        config.addAppender(playerAppender);
        config.addAppender(gameTestAppender);
        
        final AppenderRef[] refs = new AppenderRef[] {ref(CRT_LOG_NAME), ref(PLAYER_LOG_NAME)};
        
        final LoggerConfig loggerConfig = LoggerConfig.createLogger(
                false,
                Level.INFO,
                CraftTweakerConstants.LOG_NAME,
                "true",
                refs,
                null,
                config,
                null
        );
        
        loggerConfig.addAppender(fileAppender, null, null);
        loggerConfig.addAppender(playerAppender, null, null);
        loggerConfig.addAppender(gameTestAppender, null, null);
        
        config.addLogger(CraftTweakerConstants.LOG_NAME, loggerConfig);
        context.updateLoggers();
        
        instance = new CraftTweakerLog4jEditor(playerAppender, gameTestAppender);
    }
    
    private static LoggerContext findContext() {
        
        if(LogManager.getContext(false) instanceof LoggerContext context) {
            return context;
        }
        throw new IllegalStateException("Invalid context");
    }
    
    private static AppenderRef ref(final String name) {
        
        return AppenderRef.createAppenderRef(name, null, null);
    }
    
    public static void addPlayer(final Player player) {
        
        Objects.requireNonNull(instance, "Unable to add player before log init");
        instance.playerAppender.addPlayerLogger(player);
    }
    
    public static void removePlayer(Player player) {
        
        Objects.requireNonNull(instance, "Unable to remove player before log init");
        instance.playerAppender.removePlayerLogger(player);
    }
    
    public static void clearPreviousMessages() {
        
        Objects.requireNonNull(instance, "Unable to clear previous messages before log init");
        instance.playerAppender.clearPreviousMessages();
    }
    
    public static void claimGameTestLogger() {
        
        Objects.requireNonNull(instance, "Unable to query messages before log init");
        instance.gameTestAppender.claim();
    }
    
    public static GameTestLoggerAppender.QueryableLog queryGameTestLogger() {
        
        Objects.requireNonNull(instance, "Unable to query messages before log init");
        return instance.gameTestAppender.query();
    }
    
}
