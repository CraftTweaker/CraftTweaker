package minetweaker.mods.jei;

import minetweaker.*;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.*;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

/**
 * MineTweaker JEI support.
 * <p>
 * Enables hiding JEI items as well as adding new item stacks. These item stacks
 * can then show a custom message or contain NBT data. Can be used to show a
 * custom message or lore with certain items, or to provide spawnable items with
 * specific NBT tags.
 *
 * @author Stan Hebben
 */
@ZenClass("mods.jei.JEI")
public class JEI {
    
    @ZenMethod
    public static void hide(IItemStack stack) {
        MineTweakerAPI.apply(new Hide(getItemStack(stack)));
    }
    
    private static class Hide implements IUndoableAction {
        
        private ItemStack stack;
        
        public Hide(ItemStack stack) {
            this.stack = stack;
        }
        
        @Override
        public void apply() {
            if(!JEIAddonPlugin.jeiHelpers.getIngredientBlacklist().isIngredientBlacklisted(stack))
                JEIAddonPlugin.jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(stack);
        }
        
        @Override
        public boolean canUndo() {
            return true;
        }
        
        @Override
        public void undo() {
            if(JEIAddonPlugin.jeiHelpers.getIngredientBlacklist().isIngredientBlacklisted(stack))
                JEIAddonPlugin.jeiHelpers.getIngredientBlacklist().removeIngredientFromBlacklist(stack);
        }
        
        @Override
        public String describe() {
            return "Hiding item in JEI: " + stack;
        }
        
        @Override
        public String describeUndo() {
            return "Displaying item in JEI: " + stack;
        }
        
        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
