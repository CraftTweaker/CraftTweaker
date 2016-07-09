/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18.entity;

import minetweaker.api.entity.IEntityDefinition;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * @author Stan
 */
public class MCEntityDefinition implements IEntityDefinition{
    private final EntityRegistry.EntityRegistration registration;

    public MCEntityDefinition(EntityRegistry.EntityRegistration registration){
        this.registration = registration;
    }

    @Override
    public String getId(){
        return registration.getEntityClass().getName();
    }

    @Override
    public String getName(){
        return registration.getEntityName();
    }
}
