package com.blamejared.crafttweaker.impl.commands.script_examples;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CommandCallerPlayer;
import com.blamejared.crafttweaker.impl.commands.CommandImpl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.io.IOUtils;
import org.openzen.zencode.shared.SourceFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class ExamplesCommand extends CommandImpl {
    
    @Deprecated
    public static final String name = "examples";
    @Deprecated
    public static final String description = "Creates example scripts based on the mods installed, and opens the example folder";
    
    public ExamplesCommand() {
        
        super(name, description, (CommandCallerPlayer) ExamplesCommand::execute);
    }
    
    public static void register() {
        
        CTCommands.registerCommand(new ExamplesCommand());
    }
    
    private static int execute(PlayerEntity playerEntity, ItemStack stack) {
        //Cast okay, since context.asPlayer() would've failed if this wasn't a ServerPlayerEntity
        final MinecraftServer server = ((ServerPlayerEntity) playerEntity).server;
        final DataPackRegistries dataPackRegistries = server.getDataPackRegistries();
        final IResourceManager resourceManager = dataPackRegistries.getResourceManager();
        
        //final ExampleCollectionEvent event = new ExampleCollectionEvent(resourceManager);
        //MinecraftForge.EVENT_BUS.post(event);
        //Collect all scripts that are in the scripts data pack folder and write them to the example scripts folder
        for(ResourceLocation file : resourceManager.getAllResourceLocations("scripts", n -> n.endsWith(".zs"))) {
            writeScriptFile(new ResourceManagerSourceFile(file, resourceManager));
        }
        
        playerEntity.sendMessage(new StringTextComponent("Wrote examples to the 'examples' folder inside the scripts folder."), CraftTweaker.CRAFTTWEAKER_UUID);
        
        //TODO: Should we also open the folder?
        //final String path = getExamplesDir().toURI().toString();
        //PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity), new MessageOpen(path));
        return 0;
    }
    
    @Deprecated
    public static void writeScriptFile(SourceFile sourceFile) {
        
        final File file = new File(getExamplesDir(), sourceFile.getFilename());
        if(file.exists()) {
            CraftTweakerAPI.logInfo("Skip writing example file '%s' since it already exists", file);
            return;
        }
        
        if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            CraftTweakerAPI.logError("Could not create folder %s", file.getParentFile());
            return;
        }
        
        try(final FileWriter writer = new FileWriter(file, false); final Reader reader = sourceFile.open()) {
            IOUtils.copy(reader, writer);
        } catch(IOException e) {
            CraftTweakerAPI.logger.throwingWarn("Could not write script example: ", e);
        }
    }
    
    private static File getExamplesDir() {
        
        return new File(CraftTweakerAPI.SCRIPT_DIR, "examples");
    }
    
}
