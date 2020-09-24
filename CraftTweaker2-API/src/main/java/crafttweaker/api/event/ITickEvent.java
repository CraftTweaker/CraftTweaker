package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.ITickEvent")
@ZenRegister
public interface ITickEvent {

	@ZenMethod
    @ZenGetter("phase")
    String getPhase();
	
	@ZenMethod
    @ZenGetter("side")
	String getSide();
    
	@ZenMethod
    @ZenGetter("isClient")
	boolean isClient();
    
	@ZenMethod
    @ZenGetter("isServer")
    boolean isServer();
}
