package stanhebben.zenscript.annotations;

import java.lang.annotation.*;

/**
 * Denotes a caster. Casters are capable of converting one type into another.
 * They implement the 'as' operator.
 * <p>
 * For a native class, no arguments are provided. For an expansion method, the
 * argument is the source value.
 *
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenCaster {
    
}
