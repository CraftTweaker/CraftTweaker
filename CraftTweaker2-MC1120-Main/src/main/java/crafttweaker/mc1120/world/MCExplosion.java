package crafttweaker.mc1120.world;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.*;
import net.minecraft.world.Explosion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MCExplosion implements IExplosion  {
	
	private Explosion explosion;

	public MCExplosion(Explosion explosion)
	{
		this.explosion = explosion;
	}

	@Override
	public Object getInternal() {
		return explosion;
	}

	@Override
	public IEntityLivingBase getExplosivePlacedBy() {
		return CraftTweakerMC.getIEntityLivingBase(explosion.getExplosivePlacedBy());
	}

	@Override
	public IBlockPos[] getAffectedBlockPositions() {
		List<IBlockPos> list = new ArrayList<IBlockPos>();
		list.addAll(explosion.getAffectedBlockPositions().stream().map(x -> {
			return new MCBlockPos(x);
		}).collect(Collectors.toList()));

		return (IBlockPos[])list.toArray();
	}

	@Override
	public IVector3d getPosition() {
		return CraftTweakerMC.getIVector3d(explosion.getPosition());
	}

	@Override
	public void doExplosionA() {
		explosion.doExplosionA();
	}

	@Override
	public void doExplosionB(boolean spawnParticles) {
		explosion.doExplosionB(spawnParticles);
	}

	@Override
	public void clearAffectedBlockPositions() {
		explosion.clearAffectedBlockPositions();
	}
}