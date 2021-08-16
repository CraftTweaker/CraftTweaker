package crafttweaker.mc1120.creativetabs;

import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.creativetab.CreativeTabs;

import java.util.Objects;

public class MCCreativeTab implements ICreativeTab {
    
    public final CreativeTabs tab;
    private final String label;
    
    public MCCreativeTab(CreativeTabs tab, String label) {
        this.tab = tab;
        this.label = label;
    }
    
    public static ICreativeTab getICreativeTab(CreativeTabs tab) {
        if(tab == null)
            return null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCCreativeTab that = (MCCreativeTab) o;
        return Objects.equals(tab, that.tab) && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tab, label);
    }
}
