package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * Makes int arrays convertable to IData.
 *
 * @author Jared
 */
@ZenExpansion("int[]")
@ZenRegister
public class ExpandIntArray {

    @ZenCaster
    public static IData asData(int[] values) {
        return new DataIntArray(values, true);
    }
}
