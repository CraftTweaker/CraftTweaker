package com.blamejared.crafttweaker.impl.command.type.script.example;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.impl.command.CommandUtilities;
import com.blamejared.crafttweaker.impl.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.mixin.common.access.server.AccessMinecraftServer;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.IOUtils;
import org.openzen.zencode.shared.SourceFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public final class ExamplesCommand extends CommandImpl {
    
    public ExamplesCommand() {
        
        super("examples", new TranslatableComponent("crafttweaker.command.description.examples"), commandSourceStackLiteralArgumentBuilder -> {
            commandSourceStackLiteralArgumentBuilder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                return execute(player);
            });
        });
    }
    
    private static int execute(ServerPlayer player) {
        
        final MinecraftServer server = player.server;
        final ServerResources dataPackRegistries = ((AccessMinecraftServer) server).getResources();
        final ResourceManager resourceManager = dataPackRegistries.getResourceManager();
        
        //Collect all scripts that are in the scripts data pack folder and write them to the example scripts folder
        for(ResourceLocation file : resourceManager.listResources("scripts", n -> n.endsWith(".zs"))) {
            writeScriptFile(new ResourceManagerSourceFile(file, resourceManager));
        }
        
        player.sendMessage(CommandUtilities.openingFile(new TranslatableComponent("crafttweaker.command.example.generated").withStyle(ChatFormatting.GREEN), getExamplesDir().getPath()), CraftTweakerConstants.CRAFTTWEAKER_UUID);
        return Command.SINGLE_SUCCESS;
    }
    
    private static void writeScriptFile(SourceFile sourceFile) {
        
        final File file = new File(getExamplesDir(), sourceFile.getFilename());
        if(file.exists()) {
            CraftTweakerAPI.LOGGER.info("Skip writing example file '{}' since it already exists", file);
            return;
        }
        
        if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            CraftTweakerAPI.LOGGER.error("Could not create folder '{}'", file.getParentFile());
            return;
        }
        
        try(final FileWriter writer = new FileWriter(file, false); final Reader reader = sourceFile.open()) {
            IOUtils.copy(reader, writer);
        } catch(IOException e) {
            CraftTweakerAPI.LOGGER.warn("Could not write script example: ", e);
        }
    }
    
    private static File getExamplesDir() {
        
        return new File(CraftTweakerConstants.SCRIPT_DIR, "examples");
    }
    
}
