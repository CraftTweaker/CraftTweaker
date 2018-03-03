package crafttweaker.mc1120.entity;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.*;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.data.IData;
import crafttweaker.api.entity.*;
import crafttweaker.api.game.ITeam;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.server.IServer;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.command.MCCommandSender;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.game.MCTeam;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.server.MCServer;
import crafttweaker.mc1120.util.MCPosition3f;
import crafttweaker.mc1120.world.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;

public class MCEntity extends MCCommandSender implements IEntity {
    
    private Entity entity;
    
    public MCEntity(Entity entity) {
        super(entity);
        this.entity = entity;
    }
    
    @Override
    public int getDimension() {
        return entity.dimension;
    }
    
    @Override
    public void setDimension(int dimensionID) {
        entity = entity.changeDimension(dimensionID);
    }
    
    @Override
    public double getX() {
        return entity.posX;
    }
    
    @Override
    public double getY() {
        return entity.posY;
    }
    
    @Override
    public double getZ() {
        return entity.posZ;
    }
    
    @Override
    public Position3f getPosition3f() {
        return new MCPosition3f((float) entity.posX, (float) entity.posY, (float) entity.posZ);
    }
    
    @Override
    public void setPosition(IBlockPos position) {
        entity.setPosition(position.getX(), position.getY(), position.getZ());
    }
    
    @Override
    public void setDead() {
        entity.setDead();
    }
    
    @Override
    public void setFire(int seconds) {
        entity.setFire(seconds);
    }
    
    @Override
    public void extinguish() {
        entity.extinguish();
    }
    
    @Override
    public boolean isWet() {
        return entity.isWet();
    }
    
    @Override
    public List<IEntity> getPassengers() {
        return entity.getPassengers().stream().map(CraftTweakerMC::getIEntity).collect(Collectors.toList());
    }
    
    @Override
    public double getDistanceSqToEntity(IEntity otherEntity) {
        return entity.getDistance(CraftTweakerMC.getEntity(otherEntity));
    }
    
    @Override
    public boolean isAlive() {
        return entity.isEntityAlive();
    }
    
    @Override
    public IEntity getRidingEntity() {
        return CraftTweakerMC.getIEntity(entity.getRidingEntity());
    }
    
    @Override
    public IItemStack getPickedResult() {
        return CraftTweakerMC.getIItemStack(entity.getPickedResult(new RayTraceResult(entity)));
    }
    
    @Override
    public String getCustomName() {
        return entity.getCustomNameTag();
    }
    
    @Override
    public void setCustomName(String name) {
        entity.setCustomNameTag(name);
    }
    
    @Override
    public boolean isImmuneToFire() {
        return entity.isImmuneToFire();
    }
    
    @Override
    public int getAir() {
        return entity.getAir();
    }
    
    @Override
    public void setAir(int amount) {
        entity.setAir(amount);
    }
    
    @Override
    public Entity getInternal() {
        return entity;
    }
    
    @Override
    public boolean canTrample(IWorld world, IBlockDefinition block, IBlockPos pos, float fall) {
        return entity.canTrample(CraftTweakerMC.getWorld(world), CraftTweakerMC.getBlock(block), CraftTweakerMC.getBlockPos(pos), fall);
    }
    
    @Override
    public String toString() {
        return entity.toString();
    }
    
    @Override
    public boolean isInvulnerableTo(IDamageSource source) {
        return entity.isEntityInvulnerable(CraftTweakerMC.getDamageSource(source));
    }
    
    @Override
    public boolean isInvulnerable() {
        return entity.getIsInvulnerable();
    }
    
    @Override
    public void setInvulnerable(boolean invulnerable) {
        entity.setEntityInvulnerable(invulnerable);
    }
    
    @Override
    public void setToLocationFrom(IEntity other) {
        entity.copyLocationAndAnglesFrom(CraftTweakerMC.getEntity(other));
    }
    
    @Override
    public boolean isBoss() {
        return !entity.isNonBoss();
    }
    
    @Override
    public int getMaxFallHeight() {
        return entity.getMaxFallHeight();
    }
    
    @Override
    public boolean doesTriggerPressurePlate() {
        return !entity.doesEntityNotTriggerPressurePlate();
    }
    
    @Override
    public boolean isPushedByWater() {
        return entity.isPushedByWater();
    }
    
    @Override
    public String getDisplayName() {
        return entity.getDisplayName().getFormattedText();
    }
    
    @Override
    public boolean hasCustomName() {
        return entity.hasCustomName();
    }
    
    @Override
    public boolean alwaysRenderNameTag() {
        return entity.getAlwaysRenderNameTag();
    }
    
    @Override
    public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
        entity.setAlwaysRenderNameTag(alwaysRenderNameTag);
    }
    
    @Override
    public float getEyeHight() {
        return entity.getEyeHeight();
    }
    
    @Override
    public boolean isOutsideBorder() {
        return entity.isOutsideBorder();
    }
    
    @Override
    public void setOutsideBorder(boolean outsideBorder) {
        entity.setOutsideBorder(outsideBorder);
    }
    
    @Override
    public IServer getServer() {
        return new MCServer(entity.getServer());
    }
    
    @Override
    public boolean isImmuneToExplosions() {
        return entity.isImmuneToExplosions();
    }
    
    @Override
    public boolean shouldRiderSit() {
        return entity.shouldRiderSit();
    }
    
    @Override
    public boolean canRiderInteract() {
        return entity.canRiderInteract();
    }
    
    @Override
    public boolean shouldRiderDismountInWater(IEntity rider) {
        return entity.shouldDismountInWater(CraftTweakerMC.getEntity(rider));
    }
    
    @Override
    public IEntity getControllingPassenger() {
        return CraftTweakerMC.getIEntity(entity.getControllingPassenger());
    }
    
    @Override
    public boolean isPassenger(IEntity passenger) {
        return entity.isPassenger(CraftTweakerMC.getEntity(passenger));
    }
    
    @Override
    public List<IEntity> getPassengersRecursive() {
        return entity.getRecursivePassengers().stream().map(CraftTweakerMC::getIEntity).collect(Collectors.toList());
    }
    
    @Override
    public IEntity getLowestRidingEntity() {
        return CraftTweakerMC.getIEntity(entity.getLowestRidingEntity());
    }
    
    @Override
    public boolean isRidingSameEntity(IEntity other) {
        return entity.isRidingSameEntity(CraftTweakerMC.getEntity(other));
    }
    
    @Override
    public boolean canPassengerSteer() {
        return entity.canPassengerSteer();
    }
    
    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(entity.getEntityWorld());
    }
    
    @Override
    public void setWorld(IWorld world) {
        entity.setWorld(CraftTweakerMC.getWorld(world));
    }
    
    @Override
    public void onEntityUpdate() {
        entity.onEntityUpdate();
    }
    
    @Override
    public void onUpdate() {
        entity.onUpdate();
    }
    
    @Override
    public boolean isSprinting() {
        return entity.isSprinting();
    }
    
    @Override
    public void setSprinting(boolean sprinting) {
        entity.setSprinting(sprinting);
    }
    
    @Override
    public boolean isGlowing() {
        return entity.isGlowing();
    }
    
    @Override
    public void setGlowing(boolean glowing) {
        entity.setGlowing(glowing);
    }
    
    @Override
    public int getID() {
        return entity.getEntityId();
    }
    
    @Override
    public void setID(int id) {
        entity.setEntityId(id);
    }
    
    @Override
    public List<String> getTags() {
        return new ArrayList<>(entity.getTags());
    }
    
    @Override
    public void addTag(String tag) {
        entity.addTag(tag);
    }
    
    @Override
    public void removeTag(String tag) {
        entity.removeTag(tag);
    }
    
    @Override
    public void onKillCommand() {
        entity.onKillCommand();
    }
    
    @Override
    public int getMaxInPortalTime() {
        return entity.getMaxInPortalTime();
    }
    
    @Override
    public int getPortalCooldown() {
        return entity.getPortalCooldown();
    }
    
    @Override
    public boolean isSilent() {
        return entity.isSilent();
    }
    
    @Override
    public void setSilent(boolean silent) {
        entity.setSilent(silent);
    }
    
    @Override
    public boolean hasNoGravity() {
        return entity.hasNoGravity();
    }
    
    @Override
    public void setNoGravity(boolean noGravity) {
        entity.setNoGravity(noGravity);
    }
    
    @Override
    public boolean isInWater() {
        return entity.isInWater();
    }
    
    @Override
    public boolean isOverWater() {
        return entity.isOverWater();
    }
    
    @Override
    public void spawnRunningParticles() {
        entity.spawnRunningParticles();
    }
    
    @Override
    public boolean isInsideOfMaterial(IMaterial material) {
        return entity.isInsideOfMaterial(CraftTweakerMC.getMaterial(material));
    }
    
    @Override
    public boolean isInLava() {
        return entity.isInLava();
    }
    
    @Override
    public boolean attackEntityFrom(IDamageSource source, float amount) {
        return entity.attackEntityFrom(CraftTweakerMC.getDamageSource(source), amount);
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return entity.canBeCollidedWith();
    }
    
    @Override
    public boolean canBePushed() {
        return entity.canBePushed();
    }
    
    @Override
    public IData getNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        entity.writeToNBT(tag);
        return NBTConverter.from(tag, true);
    }
    
    @Override
    public IEntityItem dropItem(IItemStack itemStack, float offset) {
        return CraftTweakerMC.getIEntityItem(entity.entityDropItem(CraftTweakerMC.getItemStack(itemStack), offset));
    }
    
    @Override
    public boolean isInsideOpaqueBlock() {
        return entity.isEntityInsideOpaqueBlock();
    }
    
    @Override
    public void removePassengers() {
        entity.removePassengers();
    }
    
    @Override
    public void dismountRidingEntity() {
        entity.dismountRidingEntity();
    }
    
    @Override
    public List<IItemStack> getHeldEquipment() {
        List<IItemStack> output = new ArrayList<>();
        entity.getHeldEquipment().forEach(item -> output.add(CraftTweakerMC.getIItemStack(item)));
        return output;
    }
    
    @Override
    public List<IItemStack> getArmorInventoryList() {
        List<IItemStack> output = new ArrayList<>();
        entity.getArmorInventoryList().forEach(item -> output.add(CraftTweakerMC.getIItemStack(item)));
        return output;
    }
    
    @Override
    public List<IItemStack> getEquipmentAndArmor() {
        List<IItemStack> output = new ArrayList<>();
        entity.getEquipmentAndArmor().forEach(item -> output.add(CraftTweakerMC.getIItemStack(item)));
        return output;
    }
    
    @Override
    public boolean isBurning() {
        return entity.isBurning();
    }
    
    @Override
    public boolean isRiding() {
        return entity.isRiding();
    }
    
    @Override
    public boolean isBeingRidden() {
        return entity.isBeingRidden();
    }
    
    @Override
    public boolean isSneaking() {
        return entity.isSneaking();
    }
    
    @Override
    public void setSneaking(boolean sneaking) {
        entity.setSneaking(sneaking);
    }
    
    @Override
    public boolean isInvisible() {
        return entity.isInvisible();
    }
    
    @Override
    public ITeam getTeam() {
       return CraftTweakerMC.getITeam(entity.getTeam());
    }
    
    @Override
    public void setInvisible(boolean invisible) {
        entity.setInvisible(invisible);
    }
    
    @Override
    public boolean isOnSameTeam(IEntity other) {
        return entity.isOnSameTeam(CraftTweakerMC.getEntity(other));
    }
    
    @Override
    public void setInWeb() {
        entity.setInWeb();
    }
    
    @Override
    public IEntity[] getParts() {
        Entity[] parts = entity.getParts();
        if(parts == null)
            return new IEntity[0];
        IEntity[] output = new IEntity[parts.length];
        for(int i = 0; i < parts.length; i++) {
            output[i] = CraftTweakerMC.getIEntity(parts[i]);
        }
        return output;
    }
    
    @Override
    public boolean isEntityEqual(IEntity other) {
        return entity.isEntityEqual(CraftTweakerMC.getEntity(other));
    }
    
    @Override
    public boolean canBeAttackedWithItem() {
        return entity.canBeAttackedWithItem();
    }
    
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MCEntity && entity.isEntityEqual(((MCEntity) obj).entity)) || super.equals(obj);
    }
}
