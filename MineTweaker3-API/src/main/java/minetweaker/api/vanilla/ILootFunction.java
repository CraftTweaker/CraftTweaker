package minetweaker.api.vanilla;

import stanhebben.zenscript.annotations.*;

/**
 * Created by Jared on 6/3/2016.
 */
@ZenClass("minetweaker.vanilla.ILootFunction")
public interface ILootFunction {

    @ZenMethod
    String getName();

    @ZenSetter
    void setName();


}
