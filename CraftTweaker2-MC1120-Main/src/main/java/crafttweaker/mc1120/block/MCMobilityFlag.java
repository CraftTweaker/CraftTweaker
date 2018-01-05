package crafttweaker.mc1120.block;

import crafttweaker.api.block.IMobilityFlag;
import net.minecraft.block.material.EnumPushReaction;

public class MCMobilityFlag implements IMobilityFlag {
    
    private final EnumPushReaction mobilityFlag;
    
    public MCMobilityFlag(EnumPushReaction mobilityFlag) {
        this.mobilityFlag = mobilityFlag;
    }
    
    @Override
    public boolean matches(IMobilityFlag other) {
        return mobilityFlag.equals(other.getInternal());
    }
    
    @Override
    public EnumPushReaction getInternal() {
        return mobilityFlag;
    }
}
