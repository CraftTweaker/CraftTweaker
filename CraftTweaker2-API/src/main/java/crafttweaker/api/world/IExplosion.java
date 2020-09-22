package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.world.IExplosion")
@ZenRegister
public interface IExplosion {
	
	Object getInternal();
    
    @ZenMethod
    @ZenGetter("placedBy")
    IEntityLivingBase getExplosivePlacedBy();
    
    @ZenGetter("affectedBlockPositions")
	@ZenMethod
	IBlockPos[] getAffectedBlockPositions();
	
	@ZenGetter("position")
	@ZenMethod
	IVector3d getPosition();
	
	@ZenMethod
	void doExplosionA();
	
	@ZenMethod
	void doExplosionB(boolean spawnParticles) ;

	@ZenMethod
	void clearAffectedBlockPositions();}
