package com.blamejared.crafttweaker.impl.logger;

import com.blamejared.crafttweaker.api.logger.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.thread.*;

import java.io.*;
import java.nio.charset.*;
import java.time.*;
import java.time.format.*;
import java.util.regex.*;

public class FileLogger implements ILogger {
    
    private static final Pattern FORMATTING_CODE_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    private final Writer output;
    private LogLevel logLevel = LogLevel.INFO;
    
    public FileLogger(File logFile) {
        try {
            this.output = new OutputStreamWriter(new FileOutputStream(logFile), StandardCharsets.UTF_8);
        } catch(FileNotFoundException e) {
            throw new RuntimeException("Cannot create log file at: " + logFile.getAbsolutePath() + " writeable: " + logFile.canWrite() + " parent writeable: " + logFile.getParentFile().canWrite(), e);
        }
    }

    @Override
    public void setLogLevel(LogLevel logLevel) {
        if(logLevel.canLog(LogLevel.INFO))
            this.logLevel = logLevel;
    }

    @Override
    public LogLevel getLogLevel() {
        return logLevel;
    }

    @Override
    public void log(LogLevel level, String message, boolean prefix) {
        if(this.logLevel.canLog(level)) {
            try {
                if(prefix) {
                    message = String.format("[%s][%s][%s][%s] %s", TIME_FORMAT.format(LocalDateTime.now()), ModLoadingContext.get().getActiveContainer().getCurrentState(), EffectiveSide.get(), level, strip(message));
                }
                this.output.write(strip(message) + "\n");
                this.output.flush();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    private String strip(String message) {
        return message == null ? null : FORMATTING_CODE_PATTERN.matcher(message).replaceAll("");
    }
    
}
