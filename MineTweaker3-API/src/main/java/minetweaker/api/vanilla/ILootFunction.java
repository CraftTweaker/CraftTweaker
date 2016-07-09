package minetweaker.api.vanilla;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

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
