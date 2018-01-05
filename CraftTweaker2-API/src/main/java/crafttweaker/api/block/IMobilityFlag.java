package crafttweaker.api.block;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.block.IMobilityFlag")
@ZenRegister
public interface IMobilityFlag {
    
    @ZenMethod
    boolean matches(IMobilityFlag other);
    
    Object getInternal();
}
