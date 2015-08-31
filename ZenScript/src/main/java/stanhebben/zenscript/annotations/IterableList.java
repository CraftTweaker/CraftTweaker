package stanhebben.zenscript.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
