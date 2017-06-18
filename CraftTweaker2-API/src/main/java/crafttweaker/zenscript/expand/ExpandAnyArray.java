package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

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
