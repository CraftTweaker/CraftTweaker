package stanhebben.zenscript.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a getter. Will make the given method accessible as getter, that is,
 * it's called when value.name is being used.
 * 
 * For a native class, no arguments are provided. For an expansion, a single
 * argument with the source value is provided.
 * 
 * @author Stan Hebben
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ZenGetter {
	/**
	 * Getter name. If omitted, the method name will be used as value name.
	 * 
	 * @return getter name
	 */
	String value() default "";
}
