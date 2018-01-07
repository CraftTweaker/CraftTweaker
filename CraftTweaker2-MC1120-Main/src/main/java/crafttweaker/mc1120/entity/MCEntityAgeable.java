package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityAgeable;
import net.minecraft.entity.EntityAgeable;

public class MCEntityAgeable extends MCEntityCreature implements IEntityAgeable {
    
    private final EntityAgeable entityAgeable;
    
    public MCEntityAgeable(EntityAgeable entity) {
        super(entity);
        entityAgeable = entity;
    }
    
    @Override
    public int getGrowingAge() {
        return entityAgeable.getGrowingAge();
    }
    
    @Override
    public void setGrowingAge(int age) {
        entityAgeable.setGrowingAge(age);
    }
    
    @Override
    public void ageUp(int seconds, boolean forced) {
        entityAgeable.ageUp(seconds, forced);
    }
    
    @Override
    public void addGrowth(int seconds) {
        entityAgeable.addGrowth(seconds);
    }
    
    @Override
    public void setScaleForAge(boolean scaleForAge) {
        entityAgeable.setScaleForAge(scaleForAge);
    }
}
