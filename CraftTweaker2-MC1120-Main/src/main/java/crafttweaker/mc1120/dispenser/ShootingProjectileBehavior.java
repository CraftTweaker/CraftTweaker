package crafttweaker.mc1120.dispenser;

import crafttweaker.api.dispenser.IBlockSource;
import crafttweaker.api.dispenser.IDispenserBehavior;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.entity.IProjectile;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.util.MCPosition3f;
import net.minecraft.util.EnumFacing;

/**
 * @author youyihj
 */
public class ShootingProjectileBehavior implements IDispenserBehavior {
    private final IEntityDefinition projectile;
    private final float inaccuracy;
    private final float velocity;

    public ShootingProjectileBehavior(IEntityDefinition projectile, float inaccuracy, float velocity) {
        this.projectile = projectile;
        this.inaccuracy = inaccuracy;
        this.velocity = velocity;
    }

    @Override
    public IItemStack apply(IBlockSource source, IItemStack stack) {
        stack.mutable().shrink(1);
        EnumFacing facing = CraftTweakerMC.getFacing(source.getFacing());
        float x = (float) (source.getX() + 0.7f * facing.getFrontOffsetX());
        float y = (float) (source.getY() + 0.7f * facing.getFrontOffsetY());
        float z = (float) (source.getZ() + 0.7f * facing.getFrontOffsetZ());
        IEntity entity = projectile.createEntity(source.getWorld());
        entity.setPosition3f(new MCPosition3f(x, y, z));
        if (entity instanceof IProjectile) {
            ((IProjectile) projectile).shoot(facing.getFrontOffsetX(), facing.getFrontOffsetY() + 0.1, facing.getFrontOffsetZ(), velocity, inaccuracy);
        }
        source.getWorld().spawnEntity(entity);
        return stack;
    }
}
