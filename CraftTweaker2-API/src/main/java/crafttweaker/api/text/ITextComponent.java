package crafttweaker.api.text;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.text.ITextComponent")
public interface ITextComponent {

    @ZenOperator(OperatorType.ADD)
    ITextComponent append(ITextComponent text);

    @ZenOperator(OperatorType.CAT)
    default ITextComponent cat(ITextComponent text) {
        return append(text);
    }

    @ZenMethod
    @ZenSetter("style")
    void setStyle(IStyle style);

    @ZenMethod
    @ZenGetter("style")
    IStyle getStyle();

    @ZenMethod
    String getUnformattedComponentText();

    @ZenMethod
    String getUnformattedText();

    @ZenMethod
    String getFormattedText();

    @ZenMethod
    List<ITextComponent> getSiblings();

    @ZenMethod
    ITextComponent createCopy();


    Object getInternal();
}
