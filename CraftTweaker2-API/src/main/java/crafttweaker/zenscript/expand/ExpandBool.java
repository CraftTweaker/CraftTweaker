package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataBool;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

/**
 * @author Stanneke
 */
@ZenExpansion("bool")
@ZenRegister
public class ExpandBool {

    @ZenCaster
    public static IData asData(boolean value) {
        return new DataBool(value);
    }
}
