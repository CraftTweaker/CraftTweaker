package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.IExplosionEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IExplosion;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.world.MCExplosion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.world.ExplosionEvent;

public class MCExplosionEvent implements IExplosionEvent {
    private ExplosionEvent event;
    private Vec3d pos;
    private IBlockPos blockPos;

    public MCExplosionEvent(ExplosionEvent event) {
        this.event = event;
        this.pos = event.getExplosion().getPosition();
        this.blockPos = CraftTweakerMC.getIBlockPos(new BlockPos(pos));
    }

    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }

    @Override
    public IExplosion getExplosion() {
        return new MCExplosion(event.getExplosion());
    }

    @Override
    public IBlockPos getPosition() {
        return blockPos;
    }

    @Override
    public double getX() {
        return pos.x;
    }

    @Override
    public double getY() {
        return pos.y;
    }

    @Override
    public double getZ() {
        return pos.z;
    }
}
