package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import crafttweaker.mc1120.furnace.*;

public class ActionSetFuel implements IAction {
    
    private final SetFuelPattern pattern;
    
    public ActionSetFuel(SetFuelPattern pattern) {
        this.pattern = pattern;
    }
    
    @Override
    public void apply() {
        FuelTweaker.INSTANCE.addFuelPattern(pattern);
    }
    
    @Override
    public String describe() {
        return "Setting fuel for " + pattern.getPattern();
    }
}