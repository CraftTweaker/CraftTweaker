package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;

import java.util.Map;

/**
 * @author youyihj
 */
@ZenRegister
@ZenExpansion("crafttweaker.data.IData[string]")
public class ExpandDataStringDict {

    @ZenCaster
    public static IData asData(Map<String, IData> data) {
        return new DataMap(data, true);
    }
}
