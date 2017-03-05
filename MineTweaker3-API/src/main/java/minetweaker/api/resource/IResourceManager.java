package minetweaker.api.resource;

/**
 * @author Stan
 */
public interface IResourceManager {

    /**
     * Retrieves a resource file. Returns null if the resource dosen't exist.
     *
     * @param module
     * @param name
     *
     * @return
     */
    IResourceFile getResource(String module, String name);
}
