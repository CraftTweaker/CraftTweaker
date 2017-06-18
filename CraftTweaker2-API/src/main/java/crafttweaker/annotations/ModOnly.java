package crafttweaker.annotations;

import java.lang.annotation.*;

/**
 * Indicates that a certain class is only available when a specific mod is
 * available.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ModOnly {
    
    String value();
}
