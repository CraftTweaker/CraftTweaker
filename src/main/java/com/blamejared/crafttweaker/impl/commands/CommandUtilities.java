package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.network.messages.MessageCopy;
import com.blamejared.crafttweaker.impl.network.messages.MessageOpen;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.io.File;

public final class CommandUtilities {
    private CommandUtilities() {}
    
    public static void sendCopyingAndCopy(final TextComponent component, final String toCopy, final PlayerEntity player) {
        
        sendCopying(component, toCopy, player);
        copy(player, toCopy);
    }
    
    public static void sendCopying(final TextComponent component, final String toCopy, final PlayerEntity player) {
        
        CommandUtilities.send(CommandUtilities.copy(component, toCopy), player);
    }
    
    public static void send(ITextComponent component, CommandSource source) {
        
        source.sendFeedback(component, true);
        CraftTweakerAPI.logDump(component.getString());
    }
    
    public static void send(ITextComponent component, PlayerEntity player) {
        
        player.sendMessage(component, CraftTweaker.CRAFTTWEAKER_UUID);
        CraftTweakerAPI.logDump(component.getUnformattedComponentText());
    }
    
    @Deprecated // TODO("Localization")
    public static void send(String string, PlayerEntity player) {
        
        send(new StringTextComponent(string), player);
    }
    
    public static void copy(final PlayerEntity player, final String toCopy) {
        
        if (player instanceof ServerPlayerEntity) {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(toCopy));
        }
    }
    
    public static void open(final PlayerEntity player, final File file) {
        
        if (player instanceof ServerPlayerEntity) {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen(file.toURI().toString()));
        }
    }
    
    @Deprecated // Using string concatenation for color is... bad
    public static String color(String str, TextFormatting formatting) {
        
        return formatting + str + TextFormatting.RESET;
    }
    
    public static String stripNewLine(String string) {
        
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    public static String stripNewLine(StringBuilder string) {
        
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    public static ITextComponent copy(TextComponent base, String toCopy) {
        
        Style style = base.getStyle();
        style = style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to copy [%s]", color(toCopy, TextFormatting.GOLD))));
        style = style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ct copy " + StringUtils.quoteAndEscape(toCopy) + ""));
        return base.setStyle(style);
    }
    
    public static ITextComponent open(TextComponent base, String path) {
        
        Style style = base.getStyle();
        style = style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to open [%s]", color(path, TextFormatting.GOLD))));
        style = style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
        return base.setStyle(style);
    }
    
    public static TextComponent run(TextComponent base, String command) {
        
        Style style = Style.EMPTY;
        style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to run [%s]", color(command, TextFormatting.GOLD))));
        style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        base.setStyle(style);
        
        return base;
    }
}
