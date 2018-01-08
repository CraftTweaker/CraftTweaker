package crafttweaker.mc1120.damage;

import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntity;
import crafttweaker.mc1120.entity.MCEntity;
import crafttweaker.mc1120.entity.MCEntityLivingBase;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class MCDamageSource implements IDamageSource {
	
	private final DamageSource source;
    public MCDamageSource(DamageSource source) {
        this.source = source;
    }
	@Override
	public boolean canHarmInCreative() {
		return source.canHarmInCreative();
	}
	@Override
	public String getDamageType() {
		return source.getDamageType();
	}
	@Override
	public String getDeathMessage(IEntity entity) {
		return entity.getInternal() instanceof EntityLivingBase ? source.getDeathMessage((EntityLivingBase)entity.getInternal()).getFormattedText() : "ERROR";
	}
	@Override
	public float getHungerDamage() {
		return source.getHungerDamage();
	}
	@Override
	public IEntity getImmediateSource() {
		Entity ent = source.getImmediateSource();
		if (ent instanceof EntityPlayer) return new MCPlayer((EntityPlayer) ent);
		if (ent instanceof EntityLivingBase) return new MCEntityLivingBase((EntityLivingBase) ent);
		return new MCEntity(ent);
	}
	@Override
	public IEntity getTrueSource() {
		Entity ent = source.getTrueSource();
		if (ent instanceof EntityPlayer) return new MCPlayer((EntityPlayer) ent);
		if (ent instanceof EntityLivingBase) return new MCEntityLivingBase((EntityLivingBase) ent);
		return new MCEntity(ent);
	}
	@Override
	public boolean isCreativePlayer() {
		return source.isCreativePlayer();
	}
	@Override
	public boolean isDamageAbsolute() {
		return source.isDamageAbsolute();
	}
	@Override
	public boolean isDifficultyScaled() {
		return source.isDamageAbsolute();
	}
	@Override
	public boolean isExplosion() {
		return source.isExplosion();
	}
	@Override
	public boolean isFireDamage() {
		return source.isFireDamage();
	}
	@Override
	public boolean isMagicDamage() {
		return source.isMagicDamage();
	}
	@Override
	public boolean isProjectile() {
		return source.isProjectile();
	}
	@Override
	public IDamageSource setDamageAllowedInCreativeMode() {
		return new MCDamageSource(source.setDamageAllowedInCreativeMode());
	}
	@Override
	public IDamageSource setDamageBypassesArmor() {
		return new MCDamageSource(source.setDamageBypassesArmor());
	}
	@Override
	public IDamageSource setDamageIsAbsolute() {
		return new MCDamageSource(source.setDamageIsAbsolute());
	}
	@Override
	public IDamageSource setDifficultyScaled() {
		return new MCDamageSource(source.setDifficultyScaled());
	}
	@Override
	public IDamageSource setExplosion() {
		return new MCDamageSource(source.setExplosion());
	}
	@Override
	public IDamageSource setFireDamage() {
		return new MCDamageSource(source.setFireDamage());
	}
	@Override
	public IDamageSource setMagicDamage() {
		return new MCDamageSource(source.setMagicDamage());
	}
	@Override
	public IDamageSource setProjectile() {
		return new MCDamageSource(source.setProjectile());
	}
    
    @Override
    public Object getInternal() {
        return source;
    }
}
