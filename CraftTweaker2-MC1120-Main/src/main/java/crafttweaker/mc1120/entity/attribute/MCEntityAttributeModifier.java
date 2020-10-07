package crafttweaker.mc1120.entity.attribute;

import crafttweaker.api.entity.attribute.IEntityAttributeModifier;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class MCEntityAttributeModifier implements IEntityAttributeModifier {
    private final AttributeModifier attributeModifier;

    public MCEntityAttributeModifier(AttributeModifier attributeModifier) {
        this.attributeModifier = attributeModifier;
    }

    @Override
    public String getUUID() {
        return attributeModifier.getID().toString();
    }

    @Override
    public String getName() {
        return attributeModifier.getName();
    }

    @Override
    public int getOperation() {
        return attributeModifier.getOperation();
    }

    @Override
    public double getAmount() {
        return attributeModifier.getAmount();
    }

	@Override
	public boolean isSaved() {
		return attributeModifier.isSaved();
	}

	@Override
	public void setSaved(boolean saved) {
		attributeModifier.setSaved(saved);
	}

    @Override
    public Object getInternal() {
        return attributeModifier;
    }
}