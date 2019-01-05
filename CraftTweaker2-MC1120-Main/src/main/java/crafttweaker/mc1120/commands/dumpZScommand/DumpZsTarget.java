package crafttweaker.mc1120.commands.dumpZScommand;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public abstract class DumpZsTarget {
    public final String argumentName;

    protected DumpZsTarget(String argumentName) {
        this.argumentName = argumentName;
    }

    public abstract String getDescription();

    public abstract void execute(ICommandSender sender, MinecraftServer Server);
}
