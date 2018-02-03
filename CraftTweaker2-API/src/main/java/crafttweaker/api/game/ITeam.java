package crafttweaker.api.game;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenClass("crafttweaker.game.ITeam")
@ZenRegister
public interface ITeam {
    
    @ZenGetter("name")
    String getName();
    
    @ZenMethod
    String formatString(String input);
    
    @ZenGetter("allowFriendlyFire")
    boolean getAllowFriendlyFire();
    
    @ZenGetter("colorPrefix")
    String getColorPrefix();
    
    @ZenGetter("membershipCollection")
    List<String> getMembershipCollection();
    
    @ZenGetter("deathMessageVisibility")
    String getDeathMessageVisibility();
    
    @ZenGetter("collisionRule")
    String getCollisionRule();
}
