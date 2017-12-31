package crafttweaker.mc1120.liquid;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * @author Stan
 */
public class MCLiquidDefinition implements ILiquidDefinition {

    private final Fluid fluid;

    public MCLiquidDefinition(Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public String getName() {
        return fluid.getName();
    }

    @Override
    public String getDisplayName() {
        return fluid.getLocalizedName(new FluidStack(fluid, 1000));
    }

    @Override
    public ILiquidStack asStack(int millibuckets) {
        return new MCLiquidStack(new FluidStack(fluid, millibuckets));
    }

    @Override
    public int getLuminosity() {
        return fluid.getLuminosity();
    }

    @Override
    public void setLuminosity(int value) {
        CraftTweakerAPI.apply(new ActionSetLuminosity(value));
    }

    @Override
    public int getDensity() {
        return fluid.getDensity();
    }

    @Override
    public void setDensity(int density) {
        CraftTweakerAPI.apply(new ActionSetDensity(density));
    }

    @Override
    public int getTemperature() {
        return fluid.getTemperature();
    }

    @Override
    public void setTemperature(int temperature) {
        CraftTweakerAPI.apply(new ActionSetTemperature(temperature));
    }

    @Override
    public int getViscosity() {
        return fluid.getViscosity();
    }

    @Override
    public void setViscosity(int viscosity) {
        CraftTweakerAPI.apply(new ActionSetViscosity(viscosity));
    }

    @Override
    public boolean isGaseous() {
        return fluid.isGaseous();
    }

    @Override
    public void setGaseous(boolean gaseous) {
        CraftTweakerAPI.apply(new ActionSetGaseous(gaseous));
    }

    private class ActionSetLuminosity implements IAction {

        private final int oldValue;
        private final int newValue;

        public ActionSetLuminosity(int newValue) {
            oldValue = getLuminosity();
            this.newValue = newValue;
        }

        @Override
        public void apply() {
            fluid.setLuminosity(newValue);
        }

        @Override
        public String describe() {
            return "Setting " + fluid.getName() + " luminosity to " + newValue;
        }

    }

    private class ActionSetDensity implements IAction {

        private final int oldValue;
        private final int newValue;

        public ActionSetDensity(int newValue) {
            oldValue = getDensity();
            this.newValue = newValue;
        }

        @Override
        public void apply() {
            fluid.setDensity(newValue);
        }

        @Override
        public String describe() {
            return "Setting " + fluid.getName() + " density to " + newValue;
        }
    }

    private class ActionSetTemperature implements IAction {

        private final int oldValue;
        private final int newValue;

        public ActionSetTemperature(int newValue) {
            oldValue = getTemperature();
            this.newValue = newValue;
        }

        @Override
        public void apply() {
            fluid.setTemperature(newValue);
        }


        @Override
        public String describe() {
            return "Setting " + fluid.getName() + " temperature to " + newValue;
        }
    }

    private class ActionSetViscosity implements IAction {

        private final int oldValue;
        private final int newValue;

        public ActionSetViscosity(int newValue) {
            oldValue = getViscosity();
            this.newValue = newValue;
        }

        @Override
        public void apply() {
            fluid.setViscosity(newValue);
        }

        @Override
        public String describe() {
            return "Setting " + fluid.getName() + " viscosity to " + newValue;
        }
    }

    private class ActionSetGaseous implements IAction {

        private final boolean oldValue;
        private final boolean newValue;

        public ActionSetGaseous(boolean newValue) {
            oldValue = isGaseous();
            this.newValue = newValue;
        }

        @Override
        public void apply() {
            fluid.setGaseous(newValue);
        }

        @Override
        public String describe() {
            return "Setting " + fluid.getName() + " gaseous to " + newValue;
        }

    }
}
