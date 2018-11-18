package crafttweaker.mc1120.commands.dumpzscommand;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.zenscript.GlobalRegistry;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.io.FileUtils;

import java.io.*;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class ExportZsCommand extends CraftTweakerCommand {
    
    public ExportZsCommand() {
        super("exportzs");
    }
    
    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        File file = new File("zs_export.json");
        
        
        ZsDumpCollector dump = new ZsDumpCollector();
        dump.collectTypeRegistry(GlobalRegistry.getTypes());
        dump.collectBracketHandlers(GlobalRegistry.getPrioritizedBracketHandlers());
        dump.collectRoot(GlobalRegistry.getRoot());
    
        try {
            FileUtils.writeStringToFile(file, dump.toJson(), "utf8");
        } catch(IOException e) {
            CraftTweakerAPI.logError("Couldn't export json", e);
            sender.sendMessage(new TextComponentString("\u00A74Couldn't export json file."));
        }
        
        sender.sendMessage(getFileOpenText("Dumped content of the GlobalRegistry to a json file \u00A7r[\u00A76Click here to open\u00A7r]", file.getAbsolutePath()));
    }
    
    
    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct exportzs", "/ct exportzs", true),
                getNormalMessage(" \u00A73Dumps the whole ZenScript Registry to a json file for IDE integration"));
    }
    
}
