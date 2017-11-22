package crafttweaker.api.entity.attribute;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

import java.util.List;

@ZenRegister
@ZenClass("crafttweaker.entity.AttributeInstance")
public interface IEntityAttributeInstance {
    @ZenMethod
    @ZenGetter("attribute")
    IEntityAttribute getAttribute();

    @ZenMethod
    @ZenGetter("baseValue")
    double getBaseValue();

    @ZenMethod
    @ZenSetter("baseValue")
    void setBaseValue(double baseValue);

    @ZenMethod
    List<IEntityAttributeModifier> getModifiersByOperation(int operation);

    @ZenMethod
    @ZenGetter("modifiers")
    List<IEntityAttributeModifier> getModifiers();

    @ZenMethod
    boolean hasModifier(IEntityAttributeModifier modifier);

    @ZenMethod
    IEntityAttributeModifier getModifier(String uuid);

    @ZenMethod
    void applyModifier(IEntityAttributeModifier modifier);

    @ZenMethod
    void removeModifier(IEntityAttributeModifier modifier);

    @ZenMethod
    void removeModifier(String uuid);

    @ZenMethod
    void removeAllModifiers();

    @ZenMethod
    @ZenGetter("attributeValue")
    double getAttributeValue();

    Object getInternal();
}
