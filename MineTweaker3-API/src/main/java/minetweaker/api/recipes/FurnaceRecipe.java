package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;

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
        return String.format("furnace.addRecipe(%s, %s, %s)", output, input, xp);
    }
    
}
