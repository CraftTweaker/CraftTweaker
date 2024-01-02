package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/ProjectileImpactEvent")
@NativeTypeRegistration(value = ProjectileImpactEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.ProjectileImpactEvent")
public class ExpandProjectileImpactEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ProjectileImpactEvent> BUS = IEventBus.cancelable(
            ProjectileImpactEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
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
