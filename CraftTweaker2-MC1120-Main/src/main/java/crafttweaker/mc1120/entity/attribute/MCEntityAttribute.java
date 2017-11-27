package crafttweaker.mc1120.entity.attribute;

import crafttweaker.api.entity.attribute.IEntityAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;

public class MCEntityAttribute implements IEntityAttribute {
    private IAttribute attribute;

    public MCEntityAttribute(IAttribute attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getName() {
        return attribute.getName();
    }

    @Override
    public double clampValue(double value) {
        return attribute.clampValue(value);
    }

    @Override
    public double getDefaultValue() {
        return attribute.getDefaultValue();
    }

    @Override
    public boolean getShouldWatch() {
        return attribute.getShouldWatch();
    }

    @Override
    public IEntityAttribute getParent() {
        return new MCEntityAttribute(attribute.getParent());
    }

    @Override
    public Object getInternal() {
        return attribute;
    }
}
