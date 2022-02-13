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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.io.File;

public final class CommandUtilities {
    
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
        CraftTweakerAPI.LOGGER.info(component.getString());
    }
    
    public static void send(Component component, Player player) {
        
        player.sendMessage(component, CraftTweakerConstants.CRAFTTWEAKER_UUID);
        CraftTweakerAPI.LOGGER.info(component.getContents());
    }
    
    public static void copy(final Player player, final String toCopy) {
        
        if(player instanceof ServerPlayer) {
            Services.NETWORK.sendCopyMessage((ServerPlayer) player, new MessageCopy(toCopy));
        }
    }
    
    public static void open(final Player player, final File file) {
    
        MutableComponent component = new TranslatableComponent("crafttweaker.command.click.open", new TextComponent(file.getPath()).withStyle(ChatFormatting.GOLD));
        send(component.withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component))
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getPath()))), player);
    }
    
    public static String stripNewLine(String string) {
        
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    public static String stripNewLine(StringBuilder string) {
        
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    public static Component copy(MutableComponent base, String toCopy) {
        
        Style style = base.getStyle();
        style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("crafttweaker.command.click.copy", new TextComponent(toCopy).withStyle(ChatFormatting.GOLD))));
        style = style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, toCopy));
        return base.setStyle(style);
    }
    
    public static Component open(MutableComponent base, String path) {
        
        Style style = base.getStyle();
        style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("crafttweaker.command.click.open", new TextComponent(path).withStyle(ChatFormatting.GOLD))));
        style = style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
        return base.setStyle(style);
    }
    
    public static Component run(MutableComponent base, String command) {
        
        Style style = base.getStyle();
        style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("crafttweaker.command.click.run", new TextComponent(command).withStyle(ChatFormatting.GOLD))));
        style = style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        
        return base.setStyle(style);
    }
    
    
    public static Component openingUrl(MutableComponent base, String url) {
        
        MutableComponent component = new TranslatableComponent("crafttweaker.command.click.goto", new TextComponent(url).withStyle(ChatFormatting.GOLD));
        return base.withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component))
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url)));
    }
    
    public static Component openingFile(MutableComponent base, String path) {
        
        MutableComponent component = new TranslatableComponent("crafttweaker.command.click.open", new TextComponent(path).withStyle(ChatFormatting.GOLD));
        return base.withStyle(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component))
                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path)));
    }
    
    public static Component openingLogFile(MutableComponent base) {
        
        return openingFile(base, CraftTweakerConstants.LOG_PATH);
    }
    
    public static MutableComponent getFormattedLogFile() {
        
        return new TextComponent(CraftTweakerConstants.LOG_PATH).withStyle(ChatFormatting.AQUA);
    }
    
    public static MutableComponent makeNoticeable(MutableComponent text) {
        
        return text.withStyle(ChatFormatting.YELLOW);
    }
    
    public static MutableComponent makeNoticeable(String text) {
        
        return makeNoticeable(new TextComponent(text));
    }
    
}
