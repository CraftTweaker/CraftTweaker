package crafttweaker.api.recipes;

import crafttweaker.api.item.IItemStack;

public class FurnaceRecipe implements IFurnaceRecipe {
    
    private final IItemStack input;
    private final IItemStack output;
    private final float xp;
    
    public FurnaceRecipe(IItemStack input, IItemStack output, float xp) {
        this.input = input;
        this.output = output;
        this.xp = xp;
    }
    
    @Override
    public String toCommandString() {
        return String.format("furnace.addRecipe(%s, %s, %f)", output.toCommandString(), input.toCommandString(), xp);
    }
    
    @Override
    public IItemStack getInput() {
        return input;
    }
    
    @Override
    public IItemStack getOutput() {
        return output;
    }
    
    @Override
    public float getXp() {
        return xp;
    }
}
