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
    public Object getInternal() {
        return attributeModifier;
    }

	@Override
	public IEntityAttributeModifier createModifier(String name, double amount, int operation, String uuid) {
		AttributeModifier modifier = (uuid == null) ? new AttributeModifier(name, amount, operation) : new AttributeModifier(UUID.fromString(uuid), name, amount, operation);
		
		return CraftTweakerMC.getIEntityAttributeModifier(modifier);
	}

	@Override
	public boolean isSaved() {
		return attributeModifier.isSaved();
	}

	@Override
	public void setSaved(boolean saved) {
		attributeModifier.setSaved(saved);
	}
}