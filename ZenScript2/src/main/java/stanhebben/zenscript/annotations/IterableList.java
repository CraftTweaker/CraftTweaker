package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Makes a class implementing List expose itself as iterable over keys and
 * values.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IterableList {
    
    String value();
}
