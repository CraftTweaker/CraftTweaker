package com.blamejared.crafttweaker.gametest.logging.appender;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import net.minecraft.gametest.framework.GameTestAssertException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Plugin(name = "PlayerAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class GameTestLoggerAppender extends AbstractAppender {
    
    private final List<LogMessage> messages = new LinkedList<>();
    
    protected GameTestLoggerAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        
        super(name, filter, layout, ignoreExceptions, properties);
    }
    
    @Override
    public void append(LogEvent event) {
        
        String originalMessage = ((PatternLayout) getLayout()).toSerializable(event);
        final String message = originalMessage.replaceAll(System.lineSeparator(), "");
        
        messages.add(new LogMessage(message, originalMessage, event.getLevel()));
    }
    
    @PluginFactory
    public static GameTestLoggerAppender createAppender(@PluginAttribute("name") String name, @PluginElement("Filter") Filter filter, @Nullable @PluginElement("Layout") Layout<? extends Serializable> layout) {
        
        return new GameTestLoggerAppender(name, filter, layout, true, Property.EMPTY_ARRAY);
    }
    
    private record LogMessage(String message, String actualMessage, Level level) {}
    
    
    public void claim() {
        
        this.messages.clear();
    }
    
    public QueryableLog query() {
        
        return new QueryableLog(this.messages);
    }
    
    public static final class QueryableLog {
        
        private final List<LogMessage> log;
        
        public QueryableLog(List<LogMessage> log) {
            
            this.log = log;
        }
        
        public void assertNoErrors() {
            
            if(this.log.stream().anyMatch(logMessage -> logMessage.level() == Level.ERROR)) {
                dump();
                throw new GameTestAssertException("Expected no errors but errors were found!");
            }
        }
        
        public void assertNoWarnings() {
            
            if(this.log.stream().anyMatch(logMessage -> logMessage.level() == Level.WARN)) {
                dump();
                throw new GameTestAssertException("Expected no warnings but errors were found!");
            }
        }
        
        public void assertOutput(int index, String message) {
            
            LogMessage logMessage = this.log.get(index);
            if(logMessage == null || !logMessage.message().equals(message)) {
                throw new GameTestAssertException("Expected line '" + index + "' to equal '" + message + "', but found '" + (logMessage == null ? null : logMessage.message()) + "'");
            }
        }
        
        public void assertOutputContains(int index, String message) {
            
            LogMessage logMessage = this.log.get(index);
            if(logMessage == null || !logMessage.message().contains(message)) {
                throw new GameTestAssertException("Expected line '" + index + "' to contain '" + message + "', but found '" + (logMessage == null ? null : logMessage.message()) + "'");
            }
        }
        
        public void dump() {
            
            for(int i = 0; i < this.log.size(); i++) {
                CraftTweakerCommon.LOG.info("{}: {} '{}'", i, this.log.get(i).level, this.log.get(i).actualMessage);
            }
        }
        
    }
    
}
