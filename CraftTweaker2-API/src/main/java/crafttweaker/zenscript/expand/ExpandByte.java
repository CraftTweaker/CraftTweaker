package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataByte;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 * @author Stanneke
 */
@ZenExpansion("byte")
@ZenRegister
public class ExpandByte {

    @ZenCaster
    public static IData asData(byte value) {
        return new DataByte(value);
    }
}
