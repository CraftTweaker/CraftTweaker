package com.blamejared.crafttweaker.api.command;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.network.message.MessageCopy;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public final class CommandUtilities {
    
    public static final Logger COMMAND_LOGGER = CraftTweakerAPI.getLogger(CraftTweakerConstants.MOD_NAME + "-Commands");
    
    private CommandUtilities() {}
    
    public static void sendCopyingAndCopy(final MutableComponent component, final String toCopy, final Player player) {
        
        sendCopying(component, toCopy, player);
        copy(player, toCopy);
    }
    
    public static void sendCopying(final MutableComponent component, final String toCopy, final Player player) {
        
        CommandUtilities.send(CommandUtilities.copy(component, toCopy), player);
    }
    
    public static void send(Component component, CommandSourceStack source) {
        
        source.sendSuccess(component, true);
        if(!component.getString().isBlank()) {
            COMMAND_LOGGER.info(component.getString());
        }
    }
    
    public static void send(Component component, Player player) {
        
        player.sendSystemMessage(component);
        if(!component.getString().isBlank()) {
            COMMAND_LOGGER.info(component.getString());
        }
    }
    
    public static void copy(final Player player, final String toCopy) {
        
        if(player instanceof ServerPlayer) {
            Services.NETWORK.sendCopyMessage((ServerPlayer) player, new MessageCopy(toCopy));
        }
    }
    
    public static void open(final Player player, final Path path) {
        
        MutableComponent component = Component.translatable("crafttweaker.command.click.open", Component.literal(path.toString())
                .withStyle(ChatFormatting.GOLD));
        send(component.withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component.copy()))
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path.toString()))), player);
    }
    
    public static String stripNewLine(String string) {
        
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    public static String stripNewLine(StringBuilder string) {
        
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    public static Component copy(MutableComponent base, String toCopy) {
        
        Style style = base.getStyle();
        style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("crafttweaker.command.click.copy", Component.literal(toCopy)
                .withStyle(ChatFormatting.GOLD))));
        style = style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, toCopy));
        return base.setStyle(style);
    }
    
    public static Component open(MutableComponent base, String path) {
        
        Style style = base.getStyle();
        style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("crafttweaker.command.click.open", Component.literal(path)
                .withStyle(ChatFormatting.GOLD))));
        style = style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
        return base.setStyle(style);
    }
    
    public static Component run(MutableComponent base, String command) {
        
        Style style = base.getStyle();
        style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("crafttweaker.command.click.run", Component.literal(command)
                .withStyle(ChatFormatting.GOLD))));
        style = style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        
        return base.setStyle(style);
    }
    
    
    public static Component openingUrl(MutableComponent base, String url) {
        
        MutableComponent component = Component.translatable("crafttweaker.command.click.goto", Component.literal(url)
                .withStyle(ChatFormatting.GOLD));
        return base.withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component))
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));
    }
    
    public static Component openingFile(MutableComponent base, String path) {
        
        MutableComponent component = Component.translatable("crafttweaker.command.click.open", Component.literal(path)
                .withStyle(ChatFormatting.GOLD));
        return base.withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component))
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path)));
    }
    
    public static Component openingLogFile(MutableComponent base) {
        
        return openingFile(base, CraftTweakerConstants.LOG_PATH);
    }
    
    public static MutableComponent getFormattedLogFile() {
        
        return Component.literal(CraftTweakerConstants.LOG_PATH).withStyle(ChatFormatting.AQUA);
    }
    
    public static MutableComponent makeNoticeable(MutableComponent text) {
        
        return text.withStyle(ChatFormatting.YELLOW);
    }
    
    public static MutableComponent makeNoticeable(String text) {
        
        return makeNoticeable(Component.literal(text));
    }
    
}
