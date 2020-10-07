package crafttweaker.mc1120.entity.attribute.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.attribute.IEntityAttributeModifier;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

import java.util.UUID;

@ZenExpansion("crafttweaker.entity.AttributeModifier")
@ZenRegister
public class ExpandEntityAttributeModifier {

    @ZenMethodStatic
    public IEntityAttributeModifier createModifier(String name, double amount, int operation, String uuid) {
        AttributeModifier modifier = (uuid == null) ? new AttributeModifier(name, amount, operation) : new AttributeModifier(UUID.fromString(uuid), name, amount, operation);
        return CraftTweakerMC.getIEntityAttributeModifier(modifier);
    }
}
