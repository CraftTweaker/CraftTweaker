package com.blamejared.crafttweaker.impl.command.type.script.example;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.mixin.common.access.server.AccessMinecraftServer;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.input.ReaderInputStream;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExamplesCommand {
    
    private static final class NoLoadInjectingInputStream extends InputStream {
        private static final int EOF = -1;
        
        private static final byte INPUT_SOURCE_DELEGATE = 0;
        private static final byte INPUT_SOURCE_BYTE = 1;
        private static final byte INPUT_SOURCE_END = 2;
        
        private static final String INJECTED_STRING = """
                #noload
                // The line above will prevent the script from running, ensuring examples do not affect your current setup.
                // Feel free to comment or remove the line if you want to see a particular example in action.
                """;
        
        private final InputStream delegate;
        private final ByteBuffer next;
        
        private boolean open;
        private byte inputSource;
        
        NoLoadInjectingInputStream(final InputStream delegate, final Charset charset, final String lineEndings) {
            this.delegate = delegate;
            this.next = computeNext(charset, lineEndings);
            this.open = true;
            this.inputSource = INPUT_SOURCE_DELEGATE;
        }
        
        private static ByteBuffer computeNext(final Charset charset, final String lineEndings) {
            final String replaced = lineEndings + INJECTED_STRING.replace("\n", lineEndings);
            final ByteBuffer encoded = charset.encode(replaced);
            // Now we make a copy so that we can manipulate it at will, without any requirements from the underlying API
            final ByteBuffer result = ByteBuffer.allocate(encoded.remaining());
            result.put(encoded);
            result.flip();
            return result;
        }
        
        @Override
        public int read() throws IOException {
            this.ensureOpen();
            
            if (this.inputSource == INPUT_SOURCE_DELEGATE) {
                final int delegate = this.delegate.read();
                if (delegate != EOF) {
                    return delegate;
                }
                
                this.inputSource = INPUT_SOURCE_BYTE;
            }
            
            if (this.inputSource == INPUT_SOURCE_BYTE) {
                try {
                    return ((int) this.next.get()) & 0xFF;
                } catch (final BufferUnderflowException e) {
                    // Easier and likely faster to catch this once rather than checking every single time
                    this.inputSource = INPUT_SOURCE_END;
                }
            }
            
            return EOF;
        }
        
        @Override
        public void close() throws IOException {
            this.delegate.close();
            this.next.clear();
            this.open = false;
        }
        
        private void ensureOpen() throws IOException {
            if (!this.open) {
                throw new IOException("Stream closed");
            }
        }
        
    }
    
    private ExamplesCommand() {}
    
    public static void registerCommand(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "examples",
                Component.translatable("crafttweaker.command.description.examples"),
                builder -> builder.requires((source) -> source.hasPermission(2)) // TODO("Permission API")
                        .executes(ctx -> execute(ctx.getSource().getPlayerOrException()))
        );
    }
    
    private static int execute(final ServerPlayer player) {
        
        final MinecraftServer server = player.server;
        @SuppressWarnings("resource") final MinecraftServer.ReloadableResources reloadableResources = ((AccessMinecraftServer) server).crafttweaker$getResources();
        final ResourceManager resourceManager = reloadableResources.resourceManager();
        
        //Collect all scripts that are in the scripts data pack folder and write them to the example scripts folder
        final int examplesAmount = resourceManager.listResources("scripts", n -> n.getPath().endsWith(".zs"))
                .entrySet()
                .stream()
                .map(ResourceManagerSourceFile::new)
                .mapToInt(ExamplesCommand::writeScriptFile)
                .sum();
        
        player.sendSystemMessage(CommandUtilities.openingFile(Component.translatable("crafttweaker.command.example.generated")
                .withStyle(ChatFormatting.GREEN), getExamplesDir().toString()));
        return examplesAmount;
    }
    
    private static int writeScriptFile(final SourceFile sourceFile) {
        
        final Path file = getExamplesDir().resolve(sourceFile.getFilename());
        if(Files.exists(file)) {
            CommandUtilities.COMMAND_LOGGER.info("Skip writing example file '{}' since it already exists", file);
            return 0;
        }
        
        if(!Files.exists(file.getParent())) {
            try {
                Files.createDirectories(file.getParent());
            } catch(final IOException e) {
                CommandUtilities.COMMAND_LOGGER.error("Could not create folder '" + file.getParent() + "'", e);
                return 0;
            }
        }
        
        try(
                final Reader reader = sourceFile.open();
                final InputStream readerStream = new ReaderInputStream(reader, StandardCharsets.UTF_8);
                final InputStream stream = new NoLoadInjectingInputStream(readerStream, StandardCharsets.UTF_8, "\n") // TODO("System-based?")
        ) {
            Files.copy(stream, file);
        } catch(final IOException e) {
            CommandUtilities.COMMAND_LOGGER.warn("Could not write script example: ", e);
        }
        
        return Command.SINGLE_SUCCESS;
    }
    
    private static Path getExamplesDir() {
        
        return CraftTweakerAPI.getScriptsDirectory().resolve("./examples");
    }
    
}
