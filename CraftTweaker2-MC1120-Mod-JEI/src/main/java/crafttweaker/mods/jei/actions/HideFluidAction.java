package crafttweaker.mods.jei.actions;

import crafttweaker.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mods.jei.JEI;
import net.minecraftforge.fluids.FluidStack;

public class HideFluidAction implements IAction {
    
    private final ILiquidStack stack;
    
    public HideFluidAction(ILiquidStack stack) {
        this.stack = stack;
    }
    
    
    @Override
    public void apply() {
        if(stack == null) {
            CraftTweakerAPI.logError("Cannot hide null liquids!");
            return;
        }
        
        
        FluidStack fluid = CraftTweakerMC.getLiquidStack(stack);
        JEI.HIDDEN_LIQUIDS.add(fluid);
    }
    
    @Override
    public String describe() {
        return "Hiding item in JEI: " + stack;
    }
    
}
