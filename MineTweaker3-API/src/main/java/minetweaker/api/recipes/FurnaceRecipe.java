package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;

/**
 * Created by Jared on 4/18/2016.
 */
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
