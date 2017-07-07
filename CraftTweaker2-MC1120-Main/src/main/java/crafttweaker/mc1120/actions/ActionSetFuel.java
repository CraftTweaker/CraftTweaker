package crafttweaker.mc1120.actions;

import crafttweaker.IAction;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.furnace.*;

public class ActionSetFuel implements IAction {
    
    private final SetFuelPattern pattern;
    
    public ActionSetFuel(SetFuelPattern pattern) {
        this.pattern = pattern;
    }
    
    @Override
    public void apply() {
        for(IItemStack stack : pattern.getPattern().getItems()) {
            MCFurnaceManager.fuelMap.put(stack, pattern.getValue());
        }
    }
    
    @Override
    public String describe() {
        return "Setting fuel for " + pattern.getPattern();
    }
}