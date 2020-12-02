package crafttweaker.api.text;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.text.IStyle")
public interface IStyle {
    @ZenGetter("parent")
    IStyle getParent();

    @ZenSetter("parent")
    void setParent(IStyle style);

    @ZenGetter("bold")
    boolean getBold();

    @ZenSetter("bold")
    void setBold(boolean bold);

    @ZenGetter("italic")
    boolean getItalic();

    @ZenSetter("italic")
    void setItalic(boolean italic);

    @ZenGetter("underline")
    boolean getUnderline();

    @ZenSetter("underline")
    void setUnderline(boolean underline);

    @ZenGetter("strikethrough")
    boolean getStrikethrough();

    @ZenSetter("strikethrough")
    void setStrikethrough(boolean strikethrough);

    @ZenGetter("obfuscated")
    boolean getObfuscated();

    @ZenSetter("obfuscated")
    void setObfuscated(boolean obfuscated);

    @ZenGetter("insertion")
    String getInsertion();

    @ZenSetter("insertion")
    void setInsertion(String insertion);

    @ZenGetter("color")
    String getColor();

    @ZenSetter("color")
    void setColor(String color);

    @ZenMethod
    IStyle createShallowCopy();

    @ZenMethod
    IStyle createDeepCopy();

    Object getInternal();
}
