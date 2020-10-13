package crafttweaker.mc1120.tileentity;

import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.tileentity.IMobSpawnerBaseLogic;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.entity.MCEntityDefinition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class MCMobSpawnerBaseLogic implements IMobSpawnerBaseLogic {
    
    private final MobSpawnerBaseLogic mobSpawnerBaseLogic;
    
    public MCMobSpawnerBaseLogic(MobSpawnerBaseLogic mobSpawnerBaseLogic) {
        this.mobSpawnerBaseLogic = mobSpawnerBaseLogic;
    }
    
    @Override
    public IEntityDefinition getEntityDefinition() {
        try {
            final Object entityId = ObfuscationReflectionHelper.findMethod(MobSpawnerBaseLogic.class, "func_190895_g", ResourceLocation.class).invoke(mobSpawnerBaseLogic);
            if (entityId != null && entityId instanceof ResourceLocation && ForgeRegistries.ENTITIES.containsKey((ResourceLocation) entityId)) {
                return new MCEntityDefinition(ForgeRegistries.ENTITIES.getValue((ResourceLocation) entityId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    @Override
    public void setEntityDefinition(IEntityDefinition entityDefinition) {
        final Object internal = entityDefinition.getInternal();
        if(internal instanceof EntityEntry) {
            mobSpawnerBaseLogic.setEntityId(((EntityEntry) internal).delegate.name());
        }
    }
    
    @Override
    public void updateSpawner() {
        mobSpawnerBaseLogic.updateSpawner();
    }
    
    @Override
    public IData getNbtData() {
        return NBTConverter.from(mobSpawnerBaseLogic.writeToNBT(new NBTTagCompound()), true);
    }
    
    @Override
    public void setNbtData(IData nbtData) {
        mobSpawnerBaseLogic.readFromNBT((NBTTagCompound) NBTConverter.from(nbtData));
    }
    
    @Override
    public void setDelayToMin() {
        mobSpawnerBaseLogic.setDelayToMin(1);
    }
    
    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(mobSpawnerBaseLogic.getSpawnerWorld());
    }
    
    @Override
    public IBlockPos getBlockPos() {
        return CraftTweakerMC.getIBlockPos(mobSpawnerBaseLogic.getSpawnerPosition());
    }
}
