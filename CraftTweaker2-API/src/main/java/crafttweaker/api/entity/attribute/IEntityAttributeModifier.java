package crafttweaker.api.entity.attribute;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("crafttweaker.entity.AttributeModifier")
public interface IEntityAttributeModifier {
    @ZenMethod
    @ZenGetter("uuid")
    String getUUID();

    @ZenMethod
    @ZenGetter("name")
    String getName();

    @ZenMethod
    @ZenGetter("operation")
    int getOperation();

    @ZenMethod
    @ZenGetter("amount")
    double getAmount();
    
    @ZenMethod
    @ZenGetter("saved")
    boolean isSaved();
    
    @ZenMethod
    @ZenSetter("saved")
    void setSaved(boolean saved);


    Object getInternal();
}