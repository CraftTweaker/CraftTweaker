package crafttweaker.mc1120.block;

import crafttweaker.api.block.IMobilityFlag;
import net.minecraft.block.material.EnumPushReaction;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCMobilityFlag that = (MCMobilityFlag) o;
        return mobilityFlag == that.mobilityFlag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobilityFlag);
    }
}
