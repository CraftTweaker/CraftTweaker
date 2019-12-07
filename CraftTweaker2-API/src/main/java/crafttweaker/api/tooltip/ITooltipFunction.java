package crafttweaker.api.tooltip;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("crafttweaker.item.ITooltipFunction")
public interface ITooltipFunction {
    
    String process(IItemStack ingredient);
}
