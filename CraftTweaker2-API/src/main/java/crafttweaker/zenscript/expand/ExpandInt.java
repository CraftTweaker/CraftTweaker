package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataInt;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 * @author Stanneke
 */
@ZenExpansion("int")
@ZenRegister
public class ExpandInt {

    @ZenCaster
    public static IData toData(int value) {
        return new DataInt(value);
    }
}
