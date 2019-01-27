package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EntityLivingSpawnEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.tileentity.IMobSpawnerBaseLogic;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.tileentity.MCMobSpawnerBaseLogic;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MCEntityLivingSpawnEvent implements EntityLivingSpawnEvent {
    
    private final LivingSpawnEvent event;
    
    public MCEntityLivingSpawnEvent(LivingSpawnEvent event) {
        this.event = event;
    }
    
    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }
    
    @Override
    public float getX() {
        return event.getX();
    }
    
    @Override
    public float getY() {
        return event.getY();
    }
    
    @Override
    public float getZ() {
        return event.getZ();
    }
    
    @Override
    public IEntityLivingBase getEntityLivingBase() {
        return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
    }
    
    public static class MCEntityLivingExtendedSpawnEvent extends MCEntityLivingSpawnEvent implements EntityLivingSpawnEvent.EntityLivingExtendedSpawnEvent {
    
        private final IMobSpawnerBaseLogic spawnerBaseLogic;
        
        public MCEntityLivingExtendedSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
            super(event);
            spawnerBaseLogic = event.getSpawner() == null ? null : new MCMobSpawnerBaseLogic(event.getSpawner());
        }
    
        public MCEntityLivingExtendedSpawnEvent(LivingSpawnEvent.SpecialSpawn event) {
            super(event);
            spawnerBaseLogic = event.getSpawner() == null ? null : new MCMobSpawnerBaseLogic(event.getSpawner());
        }
    
        @Override
        public IMobSpawnerBaseLogic getSpawner() {
            return spawnerBaseLogic;
        }
    }
    
    public static class MCEntityLivingAllowDespawnEvent extends MCEntityLivingSpawnEvent implements EntityLivingSpawnEvent.EntityLivingAllowDespawnEvent {
    
        private final LivingSpawnEvent.AllowDespawn event;
    
        public MCEntityLivingAllowDespawnEvent(LivingSpawnEvent.AllowDespawn event) {
            super(event);
            this.event = event;
        }
    
        @Override
        public void forceDespawn() {
            event.setResult(Event.Result.ALLOW);
        }
    
        @Override
        public void forceRemain() {
            event.setResult(Event.Result.DENY);
        }
    
        @Override
        public void pass() {
            event.setResult(Event.Result.DEFAULT);
        }
    }
}
