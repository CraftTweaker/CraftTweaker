package com.blamejared.crafttweaker.impl.logging;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.logging.ILoggerRegistry;
import com.google.common.base.Suppliers;
import org.apache.logging.log4j.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class LoggerRegistry implements ILoggerRegistry {
    
    private static final Supplier<LoggerRegistry> INSTANCE = Suppliers.memoize(LoggerRegistry::new);
    
    private final Logger mainLogger;
    private final Map<String, Logger> loggers;
    
    private LoggerRegistry() {
        
        final boolean forward = Boolean.parseBoolean(System.getenv(CraftTweakerConstants.ENV_FORWARD_LOG_TO_LATEST_LOG));
        final Logger crtLog = LogManager.getLogger(CraftTweakerConstants.LOG_NAME);
        this.mainLogger = forward
                ? ForwardingLogger.of("CT_FORWARDING_LOGGER", crtLog, LogManager.getLogger(CraftTweakerConstants.MOD_NAME))
                : crtLog;
        this.loggers = new ConcurrentHashMap<>();
    }
    
    public static LoggerRegistry get() {
        
        return INSTANCE.get();
    }
    
    @Override
    public Logger getLoggerFor(final String system) {
        
        return this.loggers.computeIfAbsent(system, it -> SystemLogger.of("CT_SYSTEM_LOG_" + it, this.mainLogger, it));
    }
    
}
