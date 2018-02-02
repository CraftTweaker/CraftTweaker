package crafttweaker.mc1120.item;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.creativetabs.MCCreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.stream.Collectors;

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
        
        for(String key : OreDictionary.getOreNames()) {
            for(ItemStack is : OreDictionary.getOres(key)) {
                if(is.getItem() == item) {
                    result.add(CraftTweakerAPI.oreDict.get(key));
                    break;
                }
            }
        }
        
        return result;
    }
    
    @Override
    public IItemStack getDefaultInstance() {
        return new MCItemStack(item.getDefaultInstance());
    }
    
    @Override
    public void setHarvestLevel(String type, int level) {
        item.setHarvestLevel(type, level);
    }
    
    @Override
    public ICreativeTab getCreativeTab() {
        return MCCreativeTab.getICreativeTab(item.getCreativeTab());
    }
    
    @Override
    public void setCreativeTab(ICreativeTab tab) {
        item.setCreativeTab((CreativeTabs) tab.getInternal());
    }
    
    @Override
    public ICreativeTab[] getCreativeTabs() {
        CreativeTabs[] mcTabs = item.getCreativeTabs();
        ICreativeTab[] output = new ICreativeTab[mcTabs.length];
        for(int i = 0; i < output.length; i++) {
            output[i] = MCCreativeTab.getICreativeTab(mcTabs[i]);
        }
        return output;
    }
    
    @Override
    public void setNoRepair() {
        item.setNoRepair();
    }
    
    @Override
    public boolean canItemEditBlocks() {
        return item.canItemEditBlocks();
    }
    
    @Override
    public int getItemEnchantability() {
        return item.getItemEnchantability();
    }
    
    @Override
    public void setContainerItem(IItemDefinition itemDef) {
        item.setContainerItem((Item) itemDef.getInternal());
    }
    
    @Override
    public List<IItemStack> getSubItems () {
        return getSubItems(getCreativeTab());
    }
    
    @Override
    public List<IItemStack> getSubItems(ICreativeTab tab) {
        NonNullList<ItemStack> list = NonNullList.create();
        item.getSubItems((CreativeTabs) tab.getInternal(), list);
        return list.stream().map(MCItemStack::new).collect(Collectors.toList());
    }
    
    @Override
    public Object getInternal() {
        return item;
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
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final MCItemDefinition other = (MCItemDefinition) obj;
        return !(this.item != other.item && (this.item == null || !this.item.equals(other.item)));
    }
}
