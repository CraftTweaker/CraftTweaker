package crafttweaker.mc1120.block.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IMobilityFlag;
import crafttweaker.mc1120.block.MCMobilityFlag;
import net.minecraft.block.material.EnumPushReaction;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.block.IMobilityFlag")
@ZenRegister
public class ExpandMobilityFlag {
    private static final IMobilityFlag NORMAL = new MCMobilityFlag(EnumPushReaction.NORMAL);
    private static final IMobilityFlag DESTROY = new MCMobilityFlag(EnumPushReaction.DESTROY);
    private static final IMobilityFlag BLOCK = new MCMobilityFlag(EnumPushReaction.BLOCK);
    private static final IMobilityFlag IGNORE = new MCMobilityFlag(EnumPushReaction.IGNORE);
    private static final IMobilityFlag PUSH_ONLY = new MCMobilityFlag(EnumPushReaction.PUSH_ONLY);
    
    
    
	@ZenMethodStatic
	public static IMobilityFlag normal() {
		return NORMAL;
	}
	
	@ZenMethodStatic
	public static IMobilityFlag destroy() {
		return DESTROY;
	}
	
	@ZenMethodStatic
	public static IMobilityFlag block() {
		return BLOCK;
	}
	
	@ZenMethodStatic
	public static IMobilityFlag ignore() {
		return IGNORE;
	}
	
	@ZenMethodStatic
	public static IMobilityFlag pushOnly() {
		return PUSH_ONLY;
	}
}
