package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.LivingExperienceDropEvent")
@ZenRegister
public interface LivingExperienceDropEvent extends ILivingEvent, IEventCancelable {
    @ZenGetter("droppedExperience")
    int getDroppedExperience();

    @ZenSetter("droppedExperience")
    void setDroppedExperience(int experience);

    @ZenGetter("player")
    IPlayer getPlayer();

    @ZenGetter("originalExperience")
    int getOriginalExperience();
}
