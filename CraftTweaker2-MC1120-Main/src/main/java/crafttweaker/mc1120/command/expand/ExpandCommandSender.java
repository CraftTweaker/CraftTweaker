package crafttweaker.mc1120.command.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.command.ICommandSender;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.text.ITextComponent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author youyihj
 */
@ZenExpansion("crafttweaker.command.ICommandSender")
@ZenRegister
public class ExpandCommandSender {
    @ZenMethod
    public static void sendRichTextMessage(ICommandSender sender, ITextComponent textComponent) {
        CraftTweakerMC.getICommandSender(sender).sendMessage(CraftTweakerMC.getITextComponent(textComponent));
    }
}
