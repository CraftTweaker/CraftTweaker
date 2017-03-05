package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Makes a class implementing Map expose itself as iterable over keys and
 * values.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IterableMap {
    
    String key();
    
    String value();
}
