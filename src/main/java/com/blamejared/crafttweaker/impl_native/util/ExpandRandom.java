package com.blamejared.crafttweaker.impl_native.util;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Random;

@ZenRegister
@Document("vanilla/api/util/Random")
@NativeTypeRegistration(value = Random.class, zenCodeName = "crafttweaker.api.util.Random")
public class ExpandRandom {

    /**
     * Returns the next pseudorandom, uniformly distributed int value from this random number generator's sequence.
     */
    @ZenCodeType.Method
    public static int nextInt(Random internal) {
        return internal.nextInt();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed int value between zero (inclusive)
     * and bound (exclusive) from this random number generator's sequence
     *
     * @param bound the upper bound (exclusive). Must be positive.
     */
    @ZenCodeType.Method
    public static int nextInt(Random internal, int bound) {
        return internal.nextInt(bound);
    }

    @ZenCodeType.Method
    public static boolean nextBoolean(Random internal) {
        return internal.nextBoolean();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed double value
     * between 0.0 and 1.0 from this random number generator's sequence.
     */
    @ZenCodeType.Method
    public static double nextDouble(Random internal) {
        return internal.nextDouble();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed float value
     * between 0.0f and 1.0f from this random number generator's sequence.
     */

    @ZenCodeType.Method
    public static float nextFloat(Random internal) {
        return internal.nextFloat();
    }
}
