package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataShort;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 * @author Stanneke
 */
@ZenExpansion("short")
@ZenRegister
public class ExpandShort {

    @ZenCaster
    public static IData asNBT(short value) {
        return new DataShort(value);
    }
}
