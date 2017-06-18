package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import stanhebben.zenscript.annotations.*;

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
