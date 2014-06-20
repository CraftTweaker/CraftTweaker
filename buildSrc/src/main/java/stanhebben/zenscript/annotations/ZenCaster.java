package stanhebben.zenscript.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a caster. Casters are capable of converting one type into another.
 * They implement the 'as' operator.
 * 
 * For a native class, no arguments are provided. For an expansion method, the
 * argument is the source value.
 * 
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenCaster {
	
}
