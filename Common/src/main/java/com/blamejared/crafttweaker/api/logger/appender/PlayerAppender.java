package com.blamejared.crafttweaker.api.logger.appender;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Plugin(name = "PlayerAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class PlayerAppender extends AbstractAppender {
    
    private static final Map<Level, Pair<Style, Style>> STYLING = Util.make(new HashMap<>(), map -> {
        // I would hope we would never need to log FATAL, but who knows.
        map.put(Level.FATAL, make(ChatFormatting.DARK_RED.getColor(), ChatFormatting.RED.getColor()));
        map.put(Level.ERROR, make(ChatFormatting.DARK_RED.getColor(), ChatFormatting.RED.getColor()));
        map.put(Level.WARN, make(ChatFormatting.GOLD.getColor(), ChatFormatting.YELLOW.getColor()));
        
        map.put(Level.INFO, make(ChatFormatting.DARK_GREEN.getColor(), ChatFormatting.GREEN.getColor()));
        map.put(Level.DEBUG, make(ChatFormatting.DARK_PURPLE.getColor(), ChatFormatting.LIGHT_PURPLE.getColor()));
        map.put(Level.TRACE, make(ChatFormatting.DARK_GRAY.getColor(), ChatFormatting.GRAY.getColor()));
        
        // These should probably never be used, but just incase
        map.put(Level.ALL, make(ChatFormatting.DARK_GREEN.getColor(), ChatFormatting.GREEN.getColor()));
        map.put(Level.OFF, make(ChatFormatting.BLACK.getColor(), ChatFormatting.GREEN.getColor()));
    });
    
    private static Pair<Style, Style> make(final Integer header, final Integer content) {
        
        return Pair.of(
                header == null ? Style.EMPTY : Style.EMPTY.withColor(TextColor.fromRgb(header)),
                content == null ? Style.EMPTY : Style.EMPTY.withColor(TextColor.fromRgb(content))
        );
    }
    
    private final List<Player> players = new ArrayList<>();
    private final List<LogMessage> previousMessages = new ArrayList<>();
    
    public PlayerAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        
        super(name, filter, layout);
    }
    
    @PluginFactory
    public static PlayerAppender createAppender(@PluginAttribute("name") String name, @PluginElement("Filter") Filter filter, @Nullable @PluginElement("Layout") Layout<? extends Serializable> layout) {
        
        return new PlayerAppender(name, filter, layout);
    }
    
    public void sendMessage(final Player player, final LogMessage event) {
        
        final Pair<Style, Style> styling = STYLING.get(event.level());
        final MutableComponent header = Component.literal("[%s]: ".formatted(event.level()
                .name())).setStyle(styling.getFirst());
        final MutableComponent line = header.append(Component.literal(event.message()).setStyle(styling.getSecond()));
        player.sendSystemMessage(line);
    }
    
    
    @Override
    public void append(final LogEvent event) {
        
        final String message = ((PatternLayout) getLayout()).toSerializable(event).replaceAll("\r\n", " ");
        
        final LogMessage logMessage = new LogMessage(message, event.getLevel());
        this.players.forEach(player -> sendMessage(player, logMessage));
        this.previousMessages.add(logMessage);
    }
    
    public void addPlayerLogger(final Player player) {
        
        this.players.add(player);
        this.previousMessages.forEach(event -> sendMessage(player, event));
    }
    
    public void removePlayerLogger(final Player player) {
        
        this.players.remove(player);
    }
    
    public void clearPreviousMessages() {
        
        this.previousMessages.clear();
    }
    
    // LogEvent is Mutable, and calling LogEvent#toImmutable() can sometimes not work
    private record LogMessage(String message, Level level) {}
    
}
