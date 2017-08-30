package crafttweaker.api.vanilla;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.vanilla.ILootFunction")
@ZenRegister
public interface ILootFunction {
    
    @ZenMethod
    String getName();
    
    @ZenSetter("name")
    void setName(String name);
}
