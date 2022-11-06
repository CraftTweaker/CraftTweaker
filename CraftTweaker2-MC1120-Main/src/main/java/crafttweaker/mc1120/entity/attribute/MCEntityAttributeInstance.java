package crafttweaker.mc1120.entity.attribute;

import crafttweaker.api.entity.attribute.IEntityAttribute;
import crafttweaker.api.entity.attribute.IEntityAttributeInstance;
import crafttweaker.api.entity.attribute.IEntityAttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class MCEntityAttributeInstance implements IEntityAttributeInstance {
    private final IAttributeInstance attributeInstance;

    public MCEntityAttributeInstance(IAttributeInstance attributeInstance) {
        this.attributeInstance = attributeInstance;
    }

    @Override
    public IEntityAttribute getAttribute() {
        return new MCEntityAttribute(attributeInstance.getAttribute());
    }

    @Override
    public double getBaseValue() {
        return attributeInstance.getBaseValue();
    }

    @Override
    public void setBaseValue(double baseValue) {
        attributeInstance.setBaseValue(baseValue);
    }

    @Override
    public List<IEntityAttributeModifier> getModifiersByOperation(int operation) {
        return attributeInstance.getModifiersByOperation(operation).stream()
                .filter(Objects::nonNull)
                .map(MCEntityAttributeModifier::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<IEntityAttributeModifier> getModifiers() {
        return attributeInstance.getModifiers().stream()
                .map(MCEntityAttributeModifier::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasModifier(IEntityAttributeModifier modifier) {
        return attributeInstance.hasModifier((AttributeModifier) modifier.getInternal());
    }

    @Override
    public IEntityAttributeModifier getModifier(String uuid) {
        return Optional.ofNullable(attributeInstance.getModifier(UUID.fromString(uuid)))
                .map(MCEntityAttributeModifier::new)
                .orElse(null);
    }

    @Override
    public void applyModifier(IEntityAttributeModifier modifier) {
        attributeInstance.applyModifier((AttributeModifier) modifier.getInternal());
    }

    @Override
    public void removeModifier(IEntityAttributeModifier modifier) {
        attributeInstance.removeModifier((UUID) modifier.getInternal());
    }

    @Override
    public void removeModifier(String uuid) {
        attributeInstance.removeModifier(UUID.fromString(uuid));
    }

    @Override
    public void removeAllModifiers() {
        attributeInstance.getModifiers().forEach(attributeInstance::removeModifier);
    }

    @Override
    public double getAttributeValue() {
        return attributeInstance.getAttributeValue();
    }

    @Override
    public Object getInternal() {
        return attributeInstance;
    }
}
