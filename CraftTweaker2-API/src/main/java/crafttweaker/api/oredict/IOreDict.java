package crafttweaker.api.oredict;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.oredict.IOreDict")
@ZenRegister
public interface IOreDict {
    
	@ZenMethod
	@ZenMemberGetter
    IOreDictEntry get(String name);
    
    @ZenGetter("entries")
    List<IOreDictEntry> getEntries();
}
