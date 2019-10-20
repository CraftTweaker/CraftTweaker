package minetweaker.api.mods;

import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan Hebben
 */
public interface IMod {
    
    @ZenGetter("id")
    String getId();
    
    @ZenGetter("name")
    String getName();
    
    @ZenGetter("version")
    String getVersion();
    
    @ZenGetter("decription")
    String getDescription();
}
