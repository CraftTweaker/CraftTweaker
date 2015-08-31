package minetweaker.api.mods;

import stanhebben.zenscript.annotations.ZenGetter;

/**
 * 
 * 
 * @author Stan Hebben
 */
public interface IMod {
	@ZenGetter("id")
	public String getId();

	@ZenGetter("name")
	public String getName();

	@ZenGetter("version")
	public String getVersion();

	@ZenGetter("decription")
	public String getDescription();
}
