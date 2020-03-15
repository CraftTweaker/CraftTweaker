package crafttweaker.mc1120.events.handling;

import crafttweaker.api.command.ICommand;
import crafttweaker.api.command.ICommandSender;
import crafttweaker.api.event.CommandEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCCommandEvent implements CommandEvent {

    private final net.minecraftforge.event.CommandEvent event;

    public MCCommandEvent(net.minecraftforge.event.CommandEvent event) {
        this.event = event;
    }

    @Override
    public ICommandSender getCommandSender() {
        return CraftTweakerMC.getICommandSender(event.getSender());
    }

    @Override
    public ICommand getCommand() {
        return CraftTweakerMC.getICommand(event.getCommand());
    }

    @Override
    public String[] getParameters() {
        return event.getParameters();
    }

    @Override
    public void setParameters(String[] parameters) {
        event.setParameters(parameters);
    }

    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
}
