package crafttweaker.mc1120.creativetabs;

import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MCCreativeTab implements ICreativeTab {
    
    public final CreativeTabs tab;
    private final String label;
    
    public MCCreativeTab(CreativeTabs tab, String label) {
        this.tab = tab;
        this.label = label;
    }
    
    public static ICreativeTab getICreativeTab(CreativeTabs tab) {
        return CraftTweakerMC.creativeTabs.values().stream().filter(ta -> ta.getInternal().equals(tab)).findFirst().orElse(null);
    }
    
    @Override
    public void setBackgroundImageName(String backgroundImage) {
        tab.setBackgroundImageName(backgroundImage);
    }
    
    
    @Override
    public int getSearchBarWidth() {
        return tab.getSearchbarWidth();
    }
    
    @Override
    public String getTabLabel() {
        return label;
    }
    
    @Override
    public void setNoScrollBar() {
        tab.setNoScrollbar();
    }
    
    @Override
    public void setNoTitle() {
        tab.setNoTitle();
    }
    
    @Override
    public CreativeTabs getInternal() {
        return tab;
    }
    
    
}
