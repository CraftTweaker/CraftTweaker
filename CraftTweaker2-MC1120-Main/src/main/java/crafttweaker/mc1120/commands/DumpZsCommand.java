package crafttweaker.mc1120.commands;

import crafttweaker.mc1120.commands.dumpZScommand.DumpZsTarget;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class DumpZsCommand extends CraftTweakerCommand {
    
    private final Map<String, DumpZsTarget> targets;
    
    public DumpZsCommand(DumpZsTarget... targets) {
        super("dumpzs");
        this.targets = Arrays.stream(targets).collect(Collectors.toMap(t -> t.argumentName, Function.identity()));
    
        final ITextComponent[] components = new ITextComponent[this.targets.size() + 2];
        components[0] = getClickableCommandText("\u00A72/ct dumpzs " + this.targets.keySet()
                .stream()
                .collect(Collectors.joining(" | ", "[", "]")), "/ct dumpzs", true);
        components[1] = getNormalMessage(" \u00A73Dumps the whole ZenScript Registry");
    
        int i = 2;
        for(DumpZsTarget value : this.targets.values()) {
            components[i++] = getNormalMessage(String.format(" \u00A73[%s]: %s", value.argumentName, value.getDescription()));
        }
        setDescription(components);
    }
    
    @Override
    protected void init() {
        //No-Op since we need the map to have already been initialized
        //That means this instead goes into the constructor...
    }
    
    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(getNormalMessage("Please provide a target"));
            return;
        }
        for(String arg : args) {
            if(!targets.containsKey(arg))
                sender.sendMessage(getNormalMessage("No target found for argument " + arg));
            else
                targets.get(arg).execute(sender, server);
        }
    }
    
    @Override
    public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<>(targets.keySet());
    }
}
