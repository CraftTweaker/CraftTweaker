package crafttweaker.util;

import stanhebben.zenscript.value.IntRange;

import java.util.Random;

public class IntegerRange {
    
    private final int min;
    private final int max;
    private final Random rand;
    
    public IntegerRange(int min, int max) {
        this.min = Math.min(min, max);
        this.max = Math.max(min, max);
        rand = new Random(2906);
    }
    
    public IntegerRange(IntRange range) {
        this.min = Math.min(range.getFrom(), range.getTo());
        this.max = Math.max(range.getFrom(), range.getTo());
        rand = new Random(2906);
    }
    
    public int getRandom() {
        return getRandom(rand);
    }

    public int getRandom(Random rand) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public int getMin() {
        return min;
    }
    
    public int getMax() {
        return max;
    }
}
