package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Indicates a simple iterable. Annotated class must implement the iterable
 * interface.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IterableSimple {
    
    String value();
}
