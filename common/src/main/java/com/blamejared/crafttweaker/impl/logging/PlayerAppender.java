package com.blamejared.crafttweaker.impl.logging;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
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

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Plugin(name = "PlayerAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public final class PlayerAppender extends AbstractAppender {
    
    private record ChatStyle(Style levelStyle, Style messageStyle) {
        
        ChatStyle(final ChatFormatting levelStyle, final ChatFormatting messageStyle) {
            
            this(Style.EMPTY.withColor(levelStyle), Style.EMPTY.withColor(messageStyle));
        }
        
    }
    
    // LogEvent is Mutable, and calling LogEvent#toImmutable() can sometimes not work
    private record LogMessage(Level level, String message) {}
    
    private static final Map<Level, ChatStyle> STYLES = Map.of(
            // I would hope we would never need to log FATAL, but who knows.
            Level.FATAL, new ChatStyle(ChatFormatting.DARK_RED, ChatFormatting.RED),
            Level.ERROR, new ChatStyle(ChatFormatting.DARK_RED, ChatFormatting.RED),
            Level.WARN, new ChatStyle(ChatFormatting.GOLD, ChatFormatting.YELLOW),
            
            Level.INFO, new ChatStyle(ChatFormatting.DARK_GREEN, ChatFormatting.GREEN),
            Level.DEBUG, new ChatStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.LIGHT_PURPLE),
            Level.TRACE, new ChatStyle(ChatFormatting.DARK_GRAY, ChatFormatting.GRAY),
            
            // These should probably never be used, but just in case
            Level.ALL, new ChatStyle(ChatFormatting.DARK_GREEN, ChatFormatting.GREEN),
            Level.OFF, new ChatStyle(ChatFormatting.BLACK, ChatFormatting.GREEN)
    );
    
    // We keep a list of weak references just in case players get Garbage Collected, and we have missed that code path.
    // We will generally remove the player from this list as soon as it is not needed anymore, but better safe than
    // sorry.
    private final List<WeakReference<Player>> players;
    private final List<LogMessage> previousMessages;
    
    private PlayerAppender(final String name, final Filter filter, final Layout<? extends Serializable> layout, final boolean ignoreExceptions, final Property[] properties) {
        
        super(name, filter, layout, ignoreExceptions, properties);
        this.players = new ArrayList<>();
        this.previousMessages = new ArrayList<>();
    }
    
    @PluginFactory
    public static PlayerAppender createAppender(
            @PluginAttribute("name") final String name,
            @PluginElement("Filter") final Filter filter,
            @Nullable @PluginElement("Layout") Layout<? extends Serializable> layout
    ) {
        
        return new PlayerAppender(name, filter, layout, true, Property.EMPTY_ARRAY);
    }
    
    @Override
    public void append(final LogEvent event) {
        
        final String message = this.getLayout().toSerializable(event).toString().replaceAll("(\r?\n)|\r", " ");
        final LogMessage logMessage = new LogMessage(event.getLevel(), message);
        
        for(final Iterator<WeakReference<Player>> iterator = this.players.iterator(); iterator.hasNext(); ) {
            final Player player = iterator.next().get();
            if(player == null) {
                iterator.remove();
                continue;
            }
            
            this.sendMessage(player, logMessage);
        }
        
        this.previousMessages.add(logMessage);
    }
    
    void addPlayerLogger(final Player player) {
        
        this.players.add(new WeakReference<>(player));
        this.previousMessages.forEach(event -> this.sendMessage(player, event));
    }
    
    void removePlayerLogger(final Player player) {
        
        this.players.removeIf(p -> p.get() == player);
    }
    
    void clearPreviousMessages() {
        
        this.previousMessages.clear();
    }
    
    private void sendMessage(final Player player, final LogMessage message) {
        
        final ChatStyle style = STYLES.get(message.level());
        final MutableComponent header = Component.literal("[%s]: ".formatted(message.level().name()))
                .setStyle(style.levelStyle());
        final Component line = Component.literal(message.message()).setStyle(style.messageStyle());
        player.sendSystemMessage(header.append(line));
    }
    
}
