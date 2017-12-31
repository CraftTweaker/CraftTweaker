package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataList;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

import java.util.Arrays;

/**
 * Makes arrays convertable to IData.
 *
 * @author Stan Hebben
 */
@ZenExpansion("any[]")
@ZenRegister
public class ExpandAnyArray {

    @ZenCaster
    public static IData asData(IData[] values) {
        return new DataList(Arrays.asList(values), true);
    }
}
