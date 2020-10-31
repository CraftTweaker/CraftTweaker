package crafttweaker.api.util;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IVector3d;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.util.IAxisAlignedBB")
@ZenRegister
public interface IAxisAlignedBB {

    Object getInternal();

    @ZenMethod
    IAxisAlignedBB create(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);

    @ZenMethod
    IAxisAlignedBB create(IBlockPos pos);

    @ZenMethod
    IAxisAlignedBB create(IBlockPos pos1, IBlockPos pos2);

    @ZenMethod
    IAxisAlignedBB create(IVector3d min, IVector3d max);

    @ZenGetter("minX")
    double getMinX();

    @ZenGetter("minY")
    double getMinY();

    @ZenGetter("minZ")
    double getMinZ();

    @ZenGetter("maxX")
    double getMaxX();

    @ZenGetter("maxY")
    double getMaxY();

    @ZenGetter("maxZ")
    double getMaxZ();

    @ZenGetter("center")
    IVector3d getCenter();

    @ZenMethod
    IAxisAlignedBB contract(double x, double y, double z);

    @ZenMethod
    IAxisAlignedBB expand(double x, double y, double z);

    @ZenMethod
    IAxisAlignedBB grow(double value);

    @ZenMethod
    IAxisAlignedBB grow(double x, double y, double z);

    @ZenMethod
    IAxisAlignedBB shrink(double value);

    @ZenMethod
    IAxisAlignedBB intersect(IAxisAlignedBB other);

    @ZenMethod
    boolean intersects(IAxisAlignedBB other);

    @ZenMethod
    boolean contains(IVector3d vec);

    @ZenMethod
    IAxisAlignedBB union(IAxisAlignedBB other);
}