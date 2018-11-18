package crafttweaker.mods.jei.actions;

import crafttweaker.*;
import crafttweaker.mods.jei.JEI;

public class HideCategoryAction implements IAction {
    
    private final String name;
    
    public HideCategoryAction(String name) {
        this.name = name;
    }
    
    
    @Override
    public void apply() {
        if(name == null || name.isEmpty()) {
            CraftTweakerAPI.logError("Cannot hide null category!");
            return;
        }
        JEI.HIDDEN_CATEGORIES.add(name);
        
    }
    
    @Override
    public String describe() {
        return "Hiding category in JEI: " + name;
    }
    
}
