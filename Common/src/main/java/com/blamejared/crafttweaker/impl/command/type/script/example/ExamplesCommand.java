package com.blamejared.crafttweaker.impl.command.type.script.example;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.mixin.common.access.server.AccessMinecraftServer;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.input.ReaderInputStream;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExamplesCommand {
    
    private ExamplesCommand() {}
    
    public static void registerCommand(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "examples",
                new TranslatableComponent("crafttweaker.command.description.examples"),
                builder -> builder.executes(ctx -> execute(ctx.getSource().getPlayerOrException()))
        );
    }
    
    private static int execute(final ServerPlayer player) {
        
        final MinecraftServer server = player.server;
        final MinecraftServer.ReloadableResources reloadableResources = ((AccessMinecraftServer) server).crafttweaker$getResources();
        final ResourceManager resourceManager = reloadableResources.resourceManager();
        
        //Collect all scripts that are in the scripts data pack folder and write them to the example scripts folder
        for(ResourceLocation file : resourceManager.listResources("scripts", n -> n.endsWith(".zs"))) {
            writeScriptFile(new ResourceManagerSourceFile(file, resourceManager));
        }
        
        player.sendMessage(CommandUtilities.openingFile(new TranslatableComponent("crafttweaker.command.example.generated").withStyle(ChatFormatting.GREEN), getExamplesDir().toString()), CraftTweakerConstants.CRAFTTWEAKER_UUID);
        return Command.SINGLE_SUCCESS;
    }
    
    private static void writeScriptFile(final SourceFile sourceFile) {
        
        final Path file = getExamplesDir().resolve(sourceFile.getFilename());
        if(Files.exists(file)) {
            CraftTweakerAPI.LOGGER.info("Skip writing example file '{}' since it already exists", file);
            return;
        }
        
        if(!Files.exists(file.getParent())) {
            try {
                Files.createDirectories(file.getParent());
            } catch(final IOException e) {
                CraftTweakerAPI.LOGGER.error("Could not create folder '" + file.getParent() + "'", e);
                return;
            }
        }
        
        try(final Reader reader = sourceFile.open(); final InputStream stream = new ReaderInputStream(reader, StandardCharsets.UTF_8)) {
            Files.copy(stream, file);
        } catch(final IOException e) {
            CraftTweakerAPI.LOGGER.warn("Could not write script example: ", e);
        }
    }
    
    private static Path getExamplesDir() {
        
        return CraftTweakerAPI.getScriptsDirectory().resolve("./examples");
    }
    
}
