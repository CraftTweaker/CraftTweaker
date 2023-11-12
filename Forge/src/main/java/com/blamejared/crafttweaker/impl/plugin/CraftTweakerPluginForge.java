package com.blamejared.crafttweaker.impl.plugin;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.mixin.common.access.entity.AccessFakePlayerFactory;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.stream.Stream;

@CraftTweakerPlugin(CraftTweakerConstants.MOD_ID + ":builtin_forge")
@SuppressWarnings("unused") // Autowired
public class CraftTweakerPluginForge implements ICraftTweakerPlugin {
    
    @Override
    public void registerCommands(ICommandRegistrationHandler handler) {
        
        handler.registerDump("tool_tiers", Component.translatable("crafttweaker.command.description.dump.tool_tiers"), builder -> {
            builder.executes(context -> {
                
                TierSortingRegistry.getSortedTiers().forEach(tier -> {
                    
                    Object toLog = TierSortingRegistry.getName(tier);
                    if(toLog == null) {
                        toLog = tier;
                    }
                    CraftTweakerCommon.logger().info("{}", toLog);
                });
                
                CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.tool_tiers")), CommandUtilities.getFormattedLogFile())
                        .withStyle(ChatFormatting.GREEN)), context.getSource()
                        .getPlayerOrException());
                
                return Command.SINGLE_SUCCESS;
            });
        });
    }
    
}