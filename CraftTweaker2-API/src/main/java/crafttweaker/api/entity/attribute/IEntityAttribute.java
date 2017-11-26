package crafttweaker.api.entity.attribute;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("crafttweaker.entity.Attribute")
public interface IEntityAttribute {
    @ZenMethod
    @ZenGetter("name")
    String getName();

    @ZenMethod
    double clampValue(double value);

    @ZenMethod
    @ZenGetter("defaultValue")
    double getDefaultValue();

    @ZenMethod
    @ZenGetter("shouldWatch")
    boolean getShouldWatch();

    @ZenMethod
    @ZenGetter("parent")
    IEntityAttribute getParent();

    Object getInternal();
}
