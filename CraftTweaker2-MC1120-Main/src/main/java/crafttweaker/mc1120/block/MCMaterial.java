package crafttweaker.mc1120.block;

import crafttweaker.api.block.IMaterial;
import net.minecraft.block.material.Material;

public class MCMaterial implements IMaterial {
    
    private final Material material;
    
    public MCMaterial(Material material) {
        this.material = material;
    }
    
    @Override
    public boolean blocksLight() {
        return material.blocksLight();
    }
    
    @Override
    public boolean blocksMovement() {
        return material.blocksMovement();
    }
    
    @Override
    public boolean getCanBurn() {
        return material.getCanBurn();
    }
    
    @Override
    public String getMobilityFlag() {
        return material.getMobilityFlag().name();
    }
    
    @Override
    public boolean isLiquid() {
        return material.isLiquid();
    }
    
    @Override
    public boolean isOpaque() {
        return material.isOpaque();
    }
    
    @Override
    public boolean isReplaceable() {
        return material.isReplaceable();
    }
    
    @Override
    public boolean isSolid() {
        return material.isSolid();
    }
    
    @Override
    public boolean isToolNotRequired() {
        return material.isToolNotRequired();
    }
    
    @Override
    public IMaterial setReplaceable() {
        return new MCMaterial(material.setReplaceable());
    }
    
    @Override
    public boolean matches(IMaterial other) {
        return getInternal().equals(other.getInternal());
    }
    
    @Override
    public Object getInternal() {
        return material;
    }
    
}
