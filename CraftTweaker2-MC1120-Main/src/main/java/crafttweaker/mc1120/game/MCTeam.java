package crafttweaker.mc1120.game;

import crafttweaker.api.game.ITeam;
import net.minecraft.scoreboard.Team;

import java.util.*;

public class MCTeam implements ITeam {
    
    private final Team team;
    
    public MCTeam(Team team) {
        this.team = team;
    }
    
    @Override
    public String getName() {
        return team.getName();
    }
    
    @Override
    public String formatString(String input) {
        return team.formatString(input);
    }
    
    @Override
    public boolean getAllowFriendlyFire() {
        return team.getAllowFriendlyFire();
    }
    
    @Override
    public String getColorPrefix() {
        return team.getColor().toString();
    }
    
    @Override
    public List<String> getMembershipCollection() {
        return new LinkedList<>(team.getMembershipCollection());
    }
    
    @Override
    public String getDeathMessageVisibility() {
        return team.getDeathMessageVisibility().internalName;
    }
    
    @Override
    public String getCollisionRule() {
        return team.getCollisionRule().name;
    }
    
    @Override
    public Team getInternal() {
        return team;
    }
}
