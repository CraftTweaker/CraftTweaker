package crafttweaker.mc1120.text;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.DataMap;
import crafttweaker.api.data.IData;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

/**
 * @author youyihj
 */
@ZenRegister
@ZenExpansion("crafttweaker.text.ITextComponent")
public class ExpandTextComponent {
    @ZenMethodStatic
    public static ITextComponent fromString(String s) {
        return CraftTweakerMC.getITextComponent(new TextComponentString(s));
    }

    @ZenMethodStatic
    public static ITextComponent fromTranslation(String translationKey) {
        return CraftTweakerMC.getITextComponent(new TextComponentTranslation(translationKey));
    }

    @ZenMethodStatic
    public static ITextComponent fromTranslation(String translationKey, Object... args) {
        return CraftTweakerMC.getITextComponent(new TextComponentTranslation(translationKey, args));
    }

    @ZenMethodStatic
    public static ITextComponent fromData(IData data) {
        if (data instanceof DataMap) {
            return CraftTweakerMC.getITextComponent(net.minecraft.util.text.ITextComponent.Serializer.jsonToComponent(data.asString()));
        } else {
            throw new IllegalArgumentException("data argument must be DataMap!");
        }
    }
}
