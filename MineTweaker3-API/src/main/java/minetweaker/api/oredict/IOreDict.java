package minetweaker.api.oredict;

import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("minetweaker.oredict.IOreDict")
public interface IOreDict {
    
    @ZenMemberGetter
    IOreDictEntry get(String name);
    
    @ZenGetter("entries")
    List<IOreDictEntry> getEntries();
}
