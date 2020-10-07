package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityArrow;
import crafttweaker.api.entity.IEntityArrowTipped;
import crafttweaker.api.entity.IProjectile;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;

import javax.annotation.Nullable;

public class MCEntityArrowTipped extends MCEntityArrow implements IEntityArrowTipped {
    private final EntityTippedArrow entityArrowTipped;

    public MCEntityArrowTipped(EntityTippedArrow entityArrowTipped) {
        super(entityArrowTipped);
        this.entityArrowTipped = entityArrowTipped;
    }

    @Override
    public void setPotionEffect(IItemStack stack) {
        entityArrowTipped.setPotionEffect(CraftTweakerMC.getItemStack(stack));
    }

    @Override
    public void addPotionEffect(IPotionEffect effect) {
        entityArrowTipped.addEffect(CraftTweakerMC.getPotionEffect(effect));
    }
}
