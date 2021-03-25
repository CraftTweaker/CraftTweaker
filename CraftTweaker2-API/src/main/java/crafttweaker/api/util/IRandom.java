package crafttweaker.api.util;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.util.IRandom")
@ZenRegister
public interface IRandom {
    @ZenMethod
    int nextInt();

    @ZenMethod
    int nextInt(int bound);

    @ZenMethod
    double nextDouble();

    @ZenMethod
    float nextFloat();

    @ZenMethod
    boolean nextBoolean();

    @ZenMethod
    int nextInt(int minimum, int maximum);

    @ZenMethod
    float nextFloat(float minimum, float maximum);

    @ZenMethod
    double nextDouble(double minimum, double maximum);

    @ZenMethod
    String getRandomUUID();
}
