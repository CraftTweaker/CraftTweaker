package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Indicates that the given method parameter cannot be null.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NotNull {
    
}
