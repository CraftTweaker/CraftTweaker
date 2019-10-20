package minetweaker.api.resource;

import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.resource.IResourceFile")
public interface IResourceFile {

    @ZenGetter("name")
    String getName();
}
