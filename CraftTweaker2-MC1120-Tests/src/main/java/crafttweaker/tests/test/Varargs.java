package crafttweaker.tests.test;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.potions.IPotion;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@ZenClass("crafttweaker.tests.varargs")
@ZenRegister
public class Varargs {

    @ZenMethod
    public static void printVarArg(String... args) {
        for (String arg : args) {
            CraftTweakerAPI.logWarning(arg);
        }
    }

    @ZenMethod
    public static List<IItemStack> applyOreDicts(IItemStack stack, @NotNull IOreDictEntry... ods) {
        List<IItemStack> returner = new LinkedList<>();
        for (IOreDictEntry od : ods) {
            od.add(stack);
        }

        return returner;
    }

    @ZenMethod
    public static int argLength(Object... args) {
        CraftTweakerAPI.logInfo(String.valueOf(args.length));
        return args.length;
    }

    @ZenMethod
    public static boolean checkLength(int length, @Optional Object... args) {
        return length == (args == null ? 0 : args.length);
    }

    @ZenMethod
    public static void printSomething(int a, int b, String c, double d, String... e) {
        CraftTweakerAPI.logWarning(String.join("; ", String.valueOf(a), String.valueOf(b), c, String.valueOf(d), Arrays.toString(e)));
    }

    @ZenMethod
    public static void printButThisTimeWithZenObjectTypesAsZSCanBeBad(IItemStack a, IOreDictEntry b, ILiquidStack c, IPotion d, IEntityDefinition... e) {
        CraftTweakerAPI.logInfo(String.join(";", a.getDisplayName(), b.getName(), c.getDisplayName(), d.name(), Arrays.toString(e)));
    }

    @ZenMethod
    public static void printIntArr(int... iA) {
        CraftTweakerAPI.logWarning(Arrays.toString(iA));
    }

    @ZenMethod
    public static void printIntArrs(int[]... iAs) {
        CraftTweakerAPI.logWarning(Arrays.deepToString(iAs));
    }
}
