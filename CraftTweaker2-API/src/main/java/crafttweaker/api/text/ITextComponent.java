package crafttweaker.api.text;

import crafttweaker.annotations.ZenRegister;
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

    @ZenSetter("style")
    void setStyle(IStyle style);

    @ZenGetter("style")
    IStyle getStyle();

    @ZenGetter("unformattedComponentText")
    String getUnformattedComponentText();

    @ZenGetter("unformattedText")
    String getUnformattedText();

    @ZenGetter("formattedText")
    String getFormattedText();

    @ZenGetter("siblings")
    List<ITextComponent> getSiblings();

    @ZenMethod
    ITextComponent createCopy();


    Object getInternal();
}
