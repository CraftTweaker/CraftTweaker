package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("long")
@ZenRegister
public class ExpandLong {

    @ZenCaster
    public static IData asData(long value) {
        return new DataLong(value);
    }
}
