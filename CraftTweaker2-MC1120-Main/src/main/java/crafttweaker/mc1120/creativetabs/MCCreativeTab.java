package crafttweaker.mc1120.creativetabs;

import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.creativetab.CreativeTabs;

public class MCCreativeTab implements ICreativeTab {
    
    public final CreativeTabs tab;
    
    public MCCreativeTab(CreativeTabs tab) {
        this.tab = tab;
    }
    
    @Override
    public boolean drawInForegroundOfTab() {
        return tab.drawInForegroundOfTab();
    }
    
    @Override
    public String getBackgroundImageName() {
        return tab.getBackgroundImageName();
    }
    
    @Override
    public IItemStack getIconItemStack() {
        return CraftTweakerMC.getIItemStack(tab.getIconItemStack());
    }
    
    @Override
    public int getSearchBarWidth() {
        return tab.getSearchbarWidth();
    }
    
    @Override
    public int getTabColumn() {
        return tab.getTabColumn();
    }
    
    @Override
    public IItemStack getTabIconItem() {
        return CraftTweakerMC.getIItemStack(tab.getTabIconItem());
    }
    
    @Override
    public int getTabIndex() {
        return tab.getTabIndex();
    }
    
    @Override
    public String getTabLabel() {
        return tab.getTabLabel();
    }
    
    @Override
    public int getTabPage() {
        return tab.getTabPage();
    }
    
    @Override
    public String getTranslatedTabLabel() {
        return tab.getTranslatedTabLabel();
    }
    
    @Override
    public boolean hasSearchBar() {
        return tab.hasSearchBar();
    }
    
    @Override
    public boolean isAlighnedRight() {
        return tab.isAlignedRight();
    }
    
    @Override
    public boolean isTabInFirstRow() {
        return tab.isTabInFirstRow();
    }
    
    @Override
    public ICreativeTab setBackgroundImageName(String backgroundImage) {
        return new MCCreativeTab(tab.setBackgroundImageName(backgroundImage));
    }
    
    @Override
    public ICreativeTab setNoScrollBar() {
        return new MCCreativeTab(tab.setNoScrollbar());
    }
    
    @Override
    public ICreativeTab setNoTitle() {
        return new MCCreativeTab(tab.setNoTitle());
    }
    
    @Override
    public boolean shouldHidePlayerInventory() {
        return tab.shouldHidePlayerInventory();
    }
    
    
}
