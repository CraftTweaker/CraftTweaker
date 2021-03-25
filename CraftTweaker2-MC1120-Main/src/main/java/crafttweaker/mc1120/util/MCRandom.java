package crafttweaker.mc1120.util;

import crafttweaker.api.util.IRandom;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class MCRandom implements IRandom {
    private final Random random;

    public MCRandom(Random random) {
        this.random = random;
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public int nextInt(int minimum, int maximum) {
        return MathHelper.getInt(random, minimum, maximum);
    }

    @Override
    public float nextFloat(float minimum, float maximum) {
        return MathHelper.nextFloat(random, minimum, maximum);
    }

    @Override
    public double nextDouble(double minimum, double maximum) {
        return MathHelper.nextDouble(random, minimum, maximum);
    }

    @Override
    public String getRandomUUID() {
        return MathHelper.getRandomUUID(random).toString();
    }
}
