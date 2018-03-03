package crafttweaker.mods.jei.actions;

import crafttweaker.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mods.jei.JEIAddonPlugin;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;

import static crafttweaker.api.minecraft.CraftTweakerMC.getItemStack;

public class HideFluidAction implements IAction {
    
    private final ILiquidStack stack;
    
    public HideFluidAction(ILiquidStack stack) {
        this.stack = stack;
    }
    

    @Override
    public void apply() {
        if(stack == null){
            CraftTweakerAPI.logError("Cannot hide null liquids!");
            return;
        }
        
        
        FluidStack fluid = CraftTweakerMC.getLiquidStack(stack);
        
        JEIAddonPlugin.itemRegistry.removeIngredientsAtRuntime(FluidStack.class, Collections.singleton(fluid));

    }
    
    @Override
    public String describe() {
        return "Hiding item in JEI: " + stack;
    }
    
}
