package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataLongArray;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 * @author youyihj
 */
@ZenExpansion("long[]")
@ZenRegister
public class ExpandLongArray {
    @ZenCaster
    public static IData asData(long[] value) {
        return new DataLongArray(value, true);
    }
}
