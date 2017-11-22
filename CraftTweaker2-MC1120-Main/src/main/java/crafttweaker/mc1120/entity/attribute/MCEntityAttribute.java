package crafttweaker.mc1120.entity.attribute;

import crafttweaker.api.entity.attribute.IEntityAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

public class MCEntityAttribute implements IEntityAttribute {
    private IAttribute attribute;

    public MCEntityAttribute(IAttribute attribute) {
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public double clampValue(double value) {
        return 0;
    }

    @Override
    public double getDefaultValue() {
        return 0;
    }

    @Override
    public boolean getShouldWatch() {
        return false;
    }

    @Override
    public IEntityAttribute getParent() {
        return null;
    }

    @Override
    public Object getInternal() {
        return attribute;
    }
}
