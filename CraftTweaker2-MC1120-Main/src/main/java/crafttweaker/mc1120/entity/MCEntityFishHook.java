package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityFishHook;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;

public class MCEntityFishHook extends MCEntity implements IEntityFishHook{
    private final EntityFishHook entityFishHook;
    
    public MCEntityFishHook(EntityFishHook entityFishHook) {
        super(entityFishHook);
        this.entityFishHook = entityFishHook;
    }
    
	@Override
	public IEntity caughtEntity() {
		return CraftTweakerMC.getIEntity(entityFishHook.caughtEntity);
	}

	@Override
	public IPlayer getAngler() {
		return CraftTweakerMC.getIPlayer(entityFishHook.getAngler());
	}

	@Override
	public void setLureSpeed(int lureSpeed) {
		entityFishHook.setLureSpeed(lureSpeed);
	}

	@Override
	public void setLuck(int luck) {
		entityFishHook.setLuck(luck);
	}
}
