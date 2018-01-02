package crafttweaker.api.creativetabs;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.creative_tabs.ICreativeTab")
@ZenRegister
public interface ICreativeTab {
    
    @ZenMethod
    @ZenGetter("drawInForegroundOfTab")
    boolean drawInForegroundOfTab();
    
    @ZenMethod
    @ZenGetter("backgroundImageName")
    String getBackgroundImageName();
    
    @ZenMethod
    ICreativeTab setBackgroundImageName(String backgroundImage);
    
    @ZenMethod
    @ZenGetter("iconItemStack")
    IItemStack getIconItemStack();
    
    @ZenMethod
    @ZenGetter("searchBarWidth")
    int getSearchBarWidth();
    
    @ZenMethod
    @ZenGetter("tabColumn")
    int getTabColumn();
    
    @ZenMethod
    @ZenGetter("tabIconItem")
    IItemStack getTabIconItem();
    
    @ZenMethod
    @ZenGetter("tabIndex")
    int getTabIndex();
    
    @ZenMethod
    @ZenGetter("tabLabel")
    String getTabLabel();
    
    @ZenMethod
    @ZenGetter("tabPage")
    int getTabPage();
    
    @ZenMethod
    @ZenGetter("translatedTabLabel")
    String getTranslatedTabLabel();
    
    @ZenMethod
    @ZenGetter("searchBar")
    boolean hasSearchBar();
    
    @ZenMethod
    @ZenGetter("alignedRight")
    boolean isAlighnedRight();
    
    @ZenMethod
    @ZenGetter("tabInFirstRow")
    boolean isTabInFirstRow();
    
    @ZenMethod
    ICreativeTab setNoScrollBar();
    
    @ZenMethod
    ICreativeTab setNoTitle();
    
    @ZenMethod
    @ZenGetter("hidePlayerInventory")
    boolean shouldHidePlayerInventory();
}
