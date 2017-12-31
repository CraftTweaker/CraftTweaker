package crafttweaker.mc1120.block;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.block.material.EnumPushReaction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.block.MobilityFlag")
@ZenRegister
public class MobilityFlag {
    @ZenMethod
    public static String normal() {
        return EnumPushReaction.NORMAL.name();
    }

    @ZenMethod
    public static String destroy() {
        return EnumPushReaction.DESTROY.name();
    }

    @ZenMethod
    public static String block() {
        return EnumPushReaction.BLOCK.name();
    }

    @ZenMethod
    public static String ignore() {
        return EnumPushReaction.IGNORE.name();
    }

    @ZenMethod
    public static String pushOnly() {
        return EnumPushReaction.PUSH_ONLY.name();
    }
}
