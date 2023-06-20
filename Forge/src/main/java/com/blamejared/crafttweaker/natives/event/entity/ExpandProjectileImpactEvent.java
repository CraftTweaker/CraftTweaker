package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/ProjectileImpactEvent")
@NativeTypeRegistration(value = ProjectileImpactEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.ProjectileImpactEvent")
public class ExpandProjectileImpactEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ProjectileImpactEvent> BUS = IEventBus.cancelable(
            ProjectileImpactEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    
    @ZenCodeType.Getter("hitResult")
    public static HitResult getHitResult(ProjectileImpactEvent internal) {
        
        return internal.getRayTraceResult();
    }
    
    @ZenCodeType.Getter("projectile")
    public static Projectile getProjectile(ProjectileImpactEvent internal) {
        
        return internal.getProjectile();
    }
    
}
