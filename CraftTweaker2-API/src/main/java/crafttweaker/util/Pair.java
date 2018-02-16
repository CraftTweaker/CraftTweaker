package crafttweaker.util;

import java.util.Objects;

public class Pair<K, V> {
    
    private final K key;
    private final V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other == null || this.getClass() != other.getClass())
            return false;
        if(this == other)
            return true;
        Pair<?, ?> otherPair = (Pair) other;
        return Objects.equals(this.key, otherPair.key) && Objects.equals(this.value, ((Pair) other).value);
    }
    
    public K getKey() {
        return key;
    }
    
    public V getValue() {
        return value;
    }
}
