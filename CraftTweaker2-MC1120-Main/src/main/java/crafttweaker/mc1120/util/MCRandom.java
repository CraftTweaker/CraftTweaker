package crafttweaker.mc1120.util;

import crafttweaker.api.util.IRandom;

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
    public boolean nextBoolean() {
        return random.nextBoolean();
    }
}
