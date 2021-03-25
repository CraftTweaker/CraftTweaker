package crafttweaker.api.util;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.util.IRandom")
@ZenRegister
public interface IRandom {
    int nextInt();

    int nextInt(int bound);

    double nextDouble();

    float nextFloat();

    boolean nextBoolean();
}
