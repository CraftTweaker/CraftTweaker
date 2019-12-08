package minetweaker.mc1112.furnace;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.*;

public class FuelTweaker {

    public static final FuelTweaker INSTANCE = new FuelTweaker();
    private final HashMap<Item, List<SetFuelPattern>> quickList = new HashMap<>();
    private List<IFuelHandler> original;

    private FuelTweaker() {
    }

    public void register() {
        try {
            Field fuelHandlers = GameRegistry.class.getDeclaredField("fuelHandlers");
            fuelHandlers.setAccessible(true);
            original = (List<IFuelHandler>) fuelHandlers.get(null);
            List<IFuelHandler> modified = new ArrayList<>();
            modified.add(new OverridingFuelHandler());
            fuelHandlers.set(null, modified);
        } catch(NoSuchFieldException ex) {
            System.out.println("[MineTweaker] Error: could not get GameRegistry fuel handlers field. Cannot use custom fuel values.");
        } catch(SecurityException | IllegalAccessException ex) {
            System.out.println("[MineTweaker] Error: could not alter GameRegistry fuel handlers field. Cannot use custom fuel values.");
        }
    }

    public void addFuelPattern(SetFuelPattern pattern) {
        List<IItemStack> items = pattern.getPattern().getItems();
        if(items == null) {
            MineTweakerAPI.logError("Cannot set fuel for <*>");
            return;
        }

        for(IItemStack item : pattern.getPattern().getItems()) {
            ItemStack itemStack = MineTweakerMC.getItemStack(item);
            Item mcItem = itemStack.getItem();
            if(!quickList.containsKey(mcItem)) {
                quickList.put(mcItem, new ArrayList<>());
            }
            quickList.get(mcItem).add(pattern);
        }
        
        MineTweakerAPI.getIjeiRecipeRegistry().addFuel(Arrays.asList(MineTweakerMC.getItemStacks(pattern.getPattern().getItems())), pattern.getValue());
    }

    public void removeFuelPattern(SetFuelPattern pattern) {
        for(IItemStack item : pattern.getPattern().getItems()) {
            ItemStack itemStack = MineTweakerMC.getItemStack(item);
            Item mcItem = itemStack.getItem();
            if(quickList.containsKey(mcItem)) {
                quickList.get(mcItem).remove(pattern);
            }
            MineTweakerAPI.getIjeiRecipeRegistry().removeFuel(itemStack);
        }
       
    }

    private class OverridingFuelHandler implements IFuelHandler {

        @Override
        public int getBurnTime(ItemStack fuel) {
            if(quickList.containsKey(fuel.getItem())) {
                IItemStack stack = MineTweakerMC.getIItemStack(fuel);

                for(SetFuelPattern override : quickList.get(fuel.getItem())) {
                    if(override.getPattern().matches(stack)) {
                        return override.getValue();
                    }
                }
            }

            int max = 0;
            for(IFuelHandler handler : original) {
                max = Math.max(max, handler.getBurnTime(fuel));
            }
            return max;
        }
    }
}
