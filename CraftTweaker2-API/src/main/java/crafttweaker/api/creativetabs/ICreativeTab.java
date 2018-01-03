package crafttweaker.api.creativetabs;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.creative_tabs.ICreativeTab")
@ZenRegister
public interface ICreativeTab {
    
    @ZenGetter("drawInForegroundOfTab")
    boolean drawInForegroundOfTab();
    
    @ZenGetter("backgroundImageName")
    String getBackgroundImageName();
    
    @ZenSetter("backgroundImageName")
    void setBackgroundImageName(String backgroundImage);
    
    @ZenGetter("iconItemStack")
    IItemStack getIconItemStack();
    
    @ZenGetter("searchBarWidth")
    int getSearchBarWidth();
    
    @ZenGetter("tabColumn")
    int getTabColumn();
    
    @ZenGetter("tabIconItem")
    IItemStack getTabIconItem();
    
    @ZenGetter("tabIndex")
    int getTabIndex();
    
    @ZenGetter("tabLabel")
    String getTabLabel();
    
    @ZenGetter("tabPage")
    int getTabPage();
    
    @ZenGetter("translatedTabLabel")
    String getTranslatedTabLabel();
    
    @ZenGetter("hasSearchBar")
    boolean hasSearchBar();
    
    @ZenGetter("alignedRight")
    boolean isAlighnedRight();
    
    @ZenGetter("isTabInFirstRow")
    boolean isTabInFirstRow();
    
    @ZenMethod
    void setNoScrollBar();
    
    @ZenMethod
    void setNoTitle();
    
    @ZenGetter("shouldHidePlayerInventory")
    boolean shouldHidePlayerInventory();
    
    Object getInternal();
}
