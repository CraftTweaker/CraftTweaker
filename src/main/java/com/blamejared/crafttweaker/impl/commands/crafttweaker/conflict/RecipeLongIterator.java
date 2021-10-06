package com.blamejared.crafttweaker.impl.commands.crafttweaker.conflict;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;

final class RecipeLongIterator implements PrimitiveIterator.OfLong {
    
    private final int size;
    
    private int currentLeft;
    private int currentRight;
    private boolean kill;
    
    RecipeLongIterator(final int size) {
        
        this.size = size;
        this.currentLeft = 0;
        this.currentRight = this.currentLeft + 1;
        this.kill = false;
    }
    
    @Override
    public long nextLong() {
        
        if (this.kill) throw new NoSuchElementException();
        
        final long current = make(this.currentLeft, this.currentRight);
        
        ++this.currentRight;
        
        if (this.currentRight >= this.size) {
            
            ++this.currentLeft;
            
            if (this.currentLeft >= this.size - 1) this.kill = true;
            
            this.currentRight = this.currentLeft + 1;
        }
        
        return current;
    }
    
    @Override
    public boolean hasNext() {
        
        return !this.kill;
    }
    
    long estimateLength() {
        
        return (long) Math.ceil((double) (((long) this.size) * (((long) this.size) - 1L)) / 2.0D);
    }
    
    static long make(final int left, final int right) {
        
        return (((long) left) << 32L) | ((long) right);
    }
    
    static int first(final long val) {
        
        return (int) (val >> 32L);
    }
    
    static int second(final long val) {
        
        return (int) val;
    }
}
