package crafttweaker.mc1120.mods;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.mods.IMod;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.ModContainer;

import java.util.*;
import java.util.stream.Collectors;

import static crafttweaker.CrafttweakerImplementationAPI.ITEM_COMPARATOR;

/**
 * @author Stan
 */
public class MCMod implements IMod {
    
    private final ModContainer mod;
    
    public MCMod(ModContainer mod) {
        this.mod = mod;
    }
    
    @Override
    public String getId() {
        return mod.getModId();
    }
    
    @Override
    public String getName() {
        return mod.getName();
    }
    
    @Override
    public String getVersion() {
        return mod.getVersion();
    }
    
    @Override
    public String getDescription() {
        return mod.getMetadata().description;
    }
    
    @Override
    public IItemStack[] getItems() {
        List<IItemStack> stacks = new ArrayList<>();
        List<Item> items = BracketHandlerItem.getItemNames().values().stream().filter(item -> item.getRegistryName().getResourceDomain().equals(mod.getModId())).collect(Collectors.toList());
        for(Item item : items) {
            if(item == null)
                continue;
            NonNullList<ItemStack> list = NonNullList.create();
            item.getSubItems(CreativeTabs.SEARCH, list);
            for(ItemStack stack : list) {
                stacks.add(CraftTweakerMC.getIItemStack(stack));
            }
        }
        return stacks.toArray(new IItemStack[0]);
    }
}
