package crafttweaker.mc1120.item;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.Optional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stan
 */
public class MCItemDefinition implements IItemDefinition {

    private final String id;
    private final Item item;

    public MCItemDefinition(String id, Item item) {
        this.id = id;
        this.item = item;

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return item.getUnlocalizedName();
    }

    @Override
    public String getOwner() {
        return getId().split(":")[0];
    }

    @Override
    public IItemStack makeStack(@Optional int meta) {
        return CraftTweakerMC.getIItemStackWildcardSize(new ItemStack(item, 1, meta));
    }

    @Override
    public List<IOreDictEntry> getOres() {
        List<IOreDictEntry> result = new ArrayList<>();

        for (String key : OreDictionary.getOreNames()) {
            for (ItemStack is : OreDictionary.getOres(key)) {
                if (is.getItem() == item) {
                    result.add(CraftTweakerAPI.oreDict.get(key));
                    break;
                }
            }
        }

        return result;
    }

    // #############################
    // ### Object implementation ###
    // #############################

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.item != null ? this.item.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MCItemDefinition other = (MCItemDefinition) obj;
        return !(this.item != other.item && (this.item == null || !this.item.equals(other.item)));
    }
}
