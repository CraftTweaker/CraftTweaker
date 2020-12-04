package crafttweaker.mc1120.util.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IVector3d;
import net.minecraft.util.math.AxisAlignedBB;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("crafttweaker.util.IAxisAlignedBB")
@ZenRegister
public class ExpandAxisAlignedBB {

    @ZenMethodStatic
    public IAxisAlignedBB create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        AxisAlignedBB aabb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        return CraftTweakerMC.getIAxisAlignedBB(aabb);
    }

    @ZenMethodStatic
    public IAxisAlignedBB create(IBlockPos pos) {
        AxisAlignedBB aabb = new AxisAlignedBB(CraftTweakerMC.getBlockPos(pos));
        return CraftTweakerMC.getIAxisAlignedBB(aabb);
    }

    @ZenMethodStatic
    public IAxisAlignedBB create(IBlockPos pos1, IBlockPos pos2) {
        AxisAlignedBB aabb = new AxisAlignedBB(CraftTweakerMC.getBlockPos(pos1), CraftTweakerMC.getBlockPos(pos2));
        return CraftTweakerMC.getIAxisAlignedBB(aabb);
    }

    @ZenMethodStatic
    public IAxisAlignedBB create(IVector3d min, IVector3d max) {
        AxisAlignedBB aabb = new AxisAlignedBB(CraftTweakerMC.getVec3d(min), CraftTweakerMC.getVec3d(max));
        return CraftTweakerMC.getIAxisAlignedBB(aabb);
    }
}
