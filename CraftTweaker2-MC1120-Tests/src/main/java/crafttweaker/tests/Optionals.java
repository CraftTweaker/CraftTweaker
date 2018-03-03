package crafttweaker.tests;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.tests.Optionals")
@ZenRegister
public class Optionals {
    @ZenMethod
    public static void print(@Optional("heyho") String value) {
        CraftTweakerAPI.logError(value);
    }
    
    @ZenMethod
    public static void print2(@Optional(value = "minecraft:iron_ingot", methodName = "getFromString", methodClass = Optionals.class) IItemStack value) {
        print(value.getDisplayName());
    }
    
    @ZenMethod
    public static void print3(@Optional(valueLong = 3) int value) {
        CraftTweakerAPI.logError(String.valueOf(value));
    }
    
    /*
    //This method is meant to break this class
    @ZenMethod
    public static void printFaulty(@Optional String valie, String value) {
        //not meant to be created at all
        return;
    }
    */
    
    public static IItemStack getFromString(String value) {
        return BracketHandlerItem.getItem(value, 0);
    }
    
}
