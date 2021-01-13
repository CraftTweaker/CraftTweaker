package crafttweaker.mc1120.damage.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IVector3d;
import crafttweaker.mc1120.damage.MCDamageSource;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.damage.IDamageSource")
@ZenRegister
public class MCDamageSourceExpand {
    private static DamageSource getInternal(IDamageSource expanded) {
        return CraftTweakerMC.getDamageSource(expanded);
    }

    @ZenMethodStatic
    public static IDamageSource createEntityDamage(String damagetype, IEntity source) {
        EntityDamageSource damageSource = new EntityDamageSource(damagetype, CraftTweakerMC.getEntity(source));
        return new MCDamageSource(damageSource);
    }

    @ZenMethodStatic
    public static IDamageSource createIndirectDamage(String damagetype, IEntity source, IEntity indirectEntity) {
        EntityDamageSourceIndirect damageSource = new EntityDamageSourceIndirect(damagetype, CraftTweakerMC.getEntity(source), CraftTweakerMC.getEntity(indirectEntity));
        return new MCDamageSource(damageSource);
    }

    @ZenMethodStatic
    public static IDamageSource createMobDamage(IEntityLivingBase mob) {
        return new MCDamageSource(DamageSource.causeMobDamage(CraftTweakerMC.getEntityLivingBase(mob)));
    }
    
    @ZenMethodStatic
    public static IDamageSource createIndirectDamage(IEntity source, IEntityLivingBase indirectEntityIn) {
        return new MCDamageSource(DamageSource.causeIndirectDamage(CraftTweakerMC.getEntity(source), CraftTweakerMC.getEntityLivingBase(indirectEntityIn)));
    }
    
    @ZenMethodStatic
    public static IDamageSource createPlayerDamage(IPlayer player) {
        return new MCDamageSource(DamageSource.causePlayerDamage(CraftTweakerMC.getPlayer(player)));
    }
    
    @ZenMethodStatic
    public static IDamageSource createThrownDamage(IEntity source, @Optional IEntity indirectEntityIn) {
        return new MCDamageSource(DamageSource.causeThrownDamage(CraftTweakerMC.getEntity(source), CraftTweakerMC.getEntity(indirectEntityIn)));
    }
    
    @ZenMethodStatic
    public static IDamageSource createIndirectMagicDamage(IEntity source, @Optional IEntity indirectEntityIn) {
        return new MCDamageSource(DamageSource.causeIndirectMagicDamage(CraftTweakerMC.getEntity(source), CraftTweakerMC.getEntity(indirectEntityIn)));
    }
    
    @ZenMethodStatic
    public static IDamageSource createThornsDamage(IEntity source) {
        return new MCDamageSource(DamageSource.causeThornsDamage(CraftTweakerMC.getEntity(source)));
    }
    
    @ZenMethodStatic
    public static IDamageSource createExplosionDamage(@Optional IEntityLivingBase entityLivingBaseIn) {
        return new MCDamageSource(DamageSource.causeExplosionDamage(CraftTweakerMC.getEntityLivingBase(entityLivingBaseIn)));
    }
    
    @ZenMethodStatic
    public static IDamageSource createOfType(String type) {
        return new MCDamageSource(new DamageSource(type));
    }

    @ZenMethodStatic
    public static IDamageSource IN_FIRE() {
        return new MCDamageSource(DamageSource.IN_FIRE);
    }
    
    @ZenMethodStatic
    public static IDamageSource LIGHTNING_BOLT() {
        return new MCDamageSource(DamageSource.LIGHTNING_BOLT);
    }
    
    @ZenMethodStatic
    public static IDamageSource ON_FIRE() {
        return new MCDamageSource(DamageSource.ON_FIRE);
    }
    
    @ZenMethodStatic
    public static IDamageSource LAVA() {
        return new MCDamageSource(DamageSource.LAVA);
    }
    
    @ZenMethodStatic
    public static IDamageSource HOT_FLOOR() {
        return new MCDamageSource(DamageSource.HOT_FLOOR);
    }
    
    @ZenMethodStatic
    public static IDamageSource IN_WALL() {
        return new MCDamageSource(DamageSource.IN_WALL);
    }
    
    @ZenMethodStatic
    public static IDamageSource CRAMMING() {
        return new MCDamageSource(DamageSource.CRAMMING);
    }
    
    @ZenMethodStatic
    public static IDamageSource DROWN() {
        return new MCDamageSource(DamageSource.DROWN);
    }
    
    @ZenMethodStatic
    public static IDamageSource STARVE() {
        return new MCDamageSource(DamageSource.STARVE);
    }
    
    @ZenMethodStatic
    public static IDamageSource CACTUS() {
        return new MCDamageSource(DamageSource.CACTUS);
    }
    
    @ZenMethodStatic
    public static IDamageSource FALL() {
        return new MCDamageSource(DamageSource.FALL);
    }
    
    @ZenMethodStatic
    public static IDamageSource FLY_INTO_WALL() {
        return new MCDamageSource(DamageSource.FLY_INTO_WALL);
    }
    
    @ZenMethodStatic
    public static IDamageSource OUT_OF_WORLD() {
        return new MCDamageSource(DamageSource.OUT_OF_WORLD);
    }
    
    @ZenMethodStatic
    public static IDamageSource GENERIC() {
        return new MCDamageSource(DamageSource.GENERIC);
    }
    
    @ZenMethodStatic
    public static IDamageSource MAGIC() {
        return new MCDamageSource(DamageSource.MAGIC);
    }
    
    @ZenMethodStatic
    public static IDamageSource WITHER() {
        return new MCDamageSource(DamageSource.WITHER);
    }
    
    @ZenMethodStatic
    public static IDamageSource ANVIL() {
        return new MCDamageSource(DamageSource.ANVIL);
    }
    
    @ZenMethodStatic
    public static IDamageSource FALLING_BLOCK() {
        return new MCDamageSource(DamageSource.FALLING_BLOCK);
    }
    
    @ZenMethodStatic
    public static IDamageSource DRAGON_BREATH() {
        return new MCDamageSource(DamageSource.DRAGON_BREATH);
    }
    
    @ZenMethodStatic
    public static IDamageSource FIREWORKS() {
        return new MCDamageSource(DamageSource.FIREWORKS);
    }

    @ZenMethod
    @ZenGetter("damageUnblockable")
    public static boolean isDamageUnblockable(IDamageSource source) {
        return getInternal(source).isUnblockable();
    }

    @ZenMethod
    @ZenGetter("damageLocation")
    public static IVector3d getDamageLocation(IDamageSource source) {
        return CraftTweakerMC.getIVector3d(getInternal(source).getDamageLocation());
    }
}